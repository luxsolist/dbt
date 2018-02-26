package luxk.jdbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import luxk.jdbt.config.Config;
import luxk.jdbt.config.Database;

public class ExecLineKillSession extends ExecLine {
	
	class SessID {
		public String sid;
		public String serial;
	};
	
	private String database;
	private String user;
	private String password;
	
	private int count = 0;
	private int percent = 0;
	
	private String driver = null;
	private String url = null;
	
	private String selectSql =
			"select sid, serial# from v$session where prog_name = ? order by serial#";
	private String killSql = "alter system kill session (%s, %s)";

	public ExecLineKillSession(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		throw new DBTException(
				this.getClass().getName() + " unsupport duplicate",
				this.line);
	}
	
	@Override
	public void prepare() throws DBTException {
		
		Config conf = this.mCtx.getConfig();
		Database defaultDB = conf.getDatabase();
		Database myDB = conf.getDatabase(this.database);
		
		if(defaultDB == null) {
			throw new DBTException(
					"Default Database not found from Config");
		}
		if(myDB == null) {
			throw new DBTException(
					"Database " + this.database + " not found from configuration");
		}

		if(this.count == 0 && this.percent == 0) {
			throw new DBTException(
					"specify count or percentage to kill session");
		}
		
		if(this.count != 0 && this.percent != 0) {
			throw new DBTException(
					"both count and percentage specified");
		}
		
		if(this.count == 0 && (this.percent <= 0 || this.percent > 100)) {
			throw new DBTException("percent(" + this.percent + 
					") must bt set between 0(exclusive) and 100(inclusive)");
		}
		
		if(this.percent == 0 && this.count <= 0) {
			throw new DBTException(
					"count must be greater than 0");
		}
		
		// driver를 찾아보고 없으면 에러
		this.driver = myDB.getDriver();
		if(driver == null || "".equals(driver)) {
			throw new DBTException(
					"JDBC driver not found from configuration " + this.database);
		}
		
		// url은 없으면 에러
		url = myDB.getUrl();
		if(url == null || url.isEmpty()) {
			throw new DBTException(
					"JDBC url not found from configuration" + this.database);
		}

		// user name을 찾아보고 없으면 default에서 찾는다
		if(this.user == null || this.user.isEmpty())
			this.user = myDB.getUser();
		if(this.user == null || this.user.isEmpty())
			this.user = defaultDB.getUser();
		if(this.user == null || this.user.isEmpty()) {
			throw new DBTException(
					"JDBC user not found from configuration" + this.database);
		}

		// password을 찾아보고 없으면 default에서 찾는다
		if(this.password == null || this.password.isEmpty())
			this.password = myDB.getPassword();
		if(this.password == null || this.password.isEmpty())
			this.password = defaultDB.getPassword();
		if(this.password == null || this.password.isEmpty()) {
			throw new DBTException(
					"JDBC password not found from configuration" + this.database);
		}
	}
	
	@Override
	public void runInternal() throws DBTException {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SessID> list = new ArrayList<SessID>();
		
		try {
			this.result.begin();
			
			if(this.mCtx.isDebug()) {
				this.mCtx.writeDebug(this.name + " exec session kill");
			}
			
			con = connect();
			pstmt = con.prepareStatement(selectSql);

			pstmt.setString(1, this.mCtx.getProgName());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SessID ss = new SessID();
				ss.sid = rs.getString(1);
				ss.serial = rs.getString(2);
				list.add(ss);
			}
			rs.close(); rs = null;
			pstmt.close(); pstmt = null;
			
			int cnt = 0;
			if(this.percent != 0) {
				cnt = list.size() * this.percent / 100;
			} else {
				cnt = this.count;
			}
			
			stmt = con.createStatement();
			for(int i = 0; i < cnt; i++) {
				SessID ss = list.get(i);
				String tmp = String.format(this.killSql, ss.sid, ss.serial);
				
				// session이 이미 사라져서 실패할 수 있기 때문에 throw 무시
				try {
					stmt.execute(tmp);
				} catch(SQLException ee) {}
			}
			stmt.close(); stmt = null;
			
			con.close(); con = null;
			
			this.result.setSuccess();
			
		} catch(Exception e) {
			int errCode = -1;
			if(e instanceof SQLException)
				errCode = ((SQLException)e).getErrorCode();
			this.result.setFail(errCode, e.getMessage());
			if(con != null) try { con.rollback(); } catch(Exception ee) {}
			throw new DBTException("fail to sql_exec", e, this.line);
		} finally {
			if(rs != null) try { rs.close(); } catch(Exception ee) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception ee) {}
			if(stmt != null) try { stmt.close(); } catch(Exception ee) {}
			if(con != null) try { con.close(); } catch(Exception ee) {}
		}
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String val) {
		this.database = val;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCount() {
		return count;
	}

	public void setCount(String val) {
		this.count = Integer.parseInt(val);
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(String val) {
		this.percent = Integer.parseInt(val);
	}

	private Connection connect() throws Exception {
		Connection con = null;
		
		Class.forName(this.driver);
		con = DriverManager.getConnection(this.url, this.user, this.password);
		con.setAutoCommit(false);

		return con;
	}
}
