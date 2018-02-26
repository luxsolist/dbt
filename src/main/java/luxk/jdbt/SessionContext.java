package luxk.jdbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SessionContext {
	
	private boolean stop = false;
	
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	
	private Connection con = null;
	private Properties prop = new Properties();
	
	public void setConnectionInfo(String driver, String url, String user,
			String password, String programName) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		
		this.prop.setProperty("user", this.user);
		this.prop.setProperty("password", this.password);
		this.prop.setProperty("program_name", programName);
	}
	
	// warning thread unsafe!!!
	public Connection getConnection() throws DBTException {
		if(this.con == null) {
			try {
				Class.forName(this.driver);
				this.con = DriverManager.getConnection(this.url, this.prop);
				this.con.setAutoCommit(false);
			} catch(Exception e) {
				throw new DBTException(e);
			}
		}
		return this.con;
	}
	
	public Connection reconnect() throws DBTException {
		if(this.con != null) {
			try {
				this.con.close();
			} catch(Exception e) {}
		}

		try {
			Class.forName(this.driver);
			this.con = DriverManager.getConnection(this.url, this.prop);
			this.con.setAutoCommit(false);
		} catch(Exception e) {
			throw new DBTException(e);
		}
		
		return this.con;
	}
	
	public void setCtxStop() {
		this.stop = true;
	}
	
	public boolean isCtxStop() {
		return this.stop;
	}

}
