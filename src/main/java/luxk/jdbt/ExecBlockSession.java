package luxk.jdbt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Random;

import luxk.jdbt.config.Config;
import luxk.jdbt.config.Database;

public class ExecBlockSession extends ExecBlock {
	
	protected String count = "1";
	protected int countIntVal = 1;
	protected String duration = null;
	protected int repeat = -1;
	protected String database = null;
	protected String user = null;
	protected String password = null;
	protected String progName = null;
	protected String schedule = null;
	protected boolean reconnect = false;
	protected boolean ignorethrow = false;

	private String myDatabase = null;
	protected int[] conErrCodes = null;
	
	/* TODO ExecBlockTransactions로 이동
	protected boolean random = false;
	protected int[] txWeightMap = null;
	protected ExecBlockTransaction[] execTxs = null;
	protected Random rand = null;
	*/
	
	public ExecBlockSession(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}
	
	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {

		ExecBlockSession exec =
				new ExecBlockSession(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setCount(this.count);
		exec.setDuration(this.duration);
		exec.setRepeat(this.repeat);
		exec.setDatabase(this.database);
		exec.setUser(this.user);
		exec.setPassword(this.password);
		exec.setProgName(this.progName);
		exec.setReconnect(Boolean.toString(this.reconnect));
		exec.setIgnorethrow(Boolean.toString(this.ignorethrow));
		
		Iterator<DataIF> tmp = this.data.values().iterator();
		while(tmp.hasNext()) {
			DataIF data = tmp.next().duplicate();
			exec.addData(data);
		}
		
		for(ExecIF e: this.children) {
			ExecIF child = e.duplicate(exec);
			exec.addChild(child);
		}
		
		return exec;
	}

	@Override
	public void prepareInternal() throws DBTException {
		
		this.myDatabase = selectDatabase();
		String driver = null;
		String url = null;
		
		Config conf = this.mCtx.getConfig();
		Database defaultDB = conf.getDatabase();
		Database myDB = conf.getDatabase(this.myDatabase);
		
		if(defaultDB == null) {
			throw new DBTException(
					"Default Database not found from Config");
		}
		if(myDB == null) {
			throw new DBTException(
					"Database " + this.myDatabase + " not found from configuration");
		}
		
		// driver를 찾아보고 없으면 default를 가져온다
		driver = myDB.getDriver();
		if(driver == null || driver.isEmpty())
			driver = defaultDB.getDriver();
		if(driver == null || "".equals(driver)) {
			throw new DBTException(
					"JDBC driver not found from configuration " + this.myDatabase);
		}
		
		// url은 없으면 바로 에러
		url = myDB.getUrl();
		if(url == null || url.isEmpty()) {
			throw new DBTException(
					"JDBC url not found from configuration" + this.myDatabase);
		}

		// user name을 찾아보고 없으면 default에서 찾는다
		if(this.user == null || this.user.isEmpty())
			this.user = myDB.getUser();
		if(this.user == null || this.user.isEmpty())
			this.user = defaultDB.getUser();
		if(this.user == null || this.user.isEmpty()) {
			throw new DBTException(
					"JDBC user not found from configuration" + this.myDatabase);
		}

		// password을 찾아보고 없으면 default에서 찾는다
		if(this.password == null || this.password.isEmpty())
			this.password = myDB.getPassword();
		if(this.password == null || this.password.isEmpty())
			this.password = defaultDB.getPassword();
		if(this.password == null || this.password.isEmpty()) {
			throw new DBTException(
					"JDBC password not found from configuration" + this.myDatabase);
		}
		
		// progName을 찾아보고 없으면 null
		if(this.progName == null || this.progName.isEmpty())
			this.progName = this.mCtx.getProgName();
		
		this.sCtx = new SessionContext();
		this.sCtx.setConnectionInfo(driver, url, this.user, this.password, this.progName);
		
		// connect to db, init runtime, etc...
		try {
			this.sCtx.getConnection();
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug(
					"Session " + this.name + " connected to " + url);
		} catch(Exception e) {
			throw new DBTException(e);
		}
		
		// connection error를 감지할 errorcode 설정
		this.conErrCodes = myDB.getReconnectOnErrors();
		if(this.conErrCodes == null || this.conErrCodes.length == 0)
			this.conErrCodes = defaultDB.getReconnectOnErrors();
		
		/* TODO ExecBlock Transactions로 이동
		if(this.random) {
			// 하위 tx별 weight 설정
			int weightIdx = 0;
			int txIdx = 0;
			for(ExecIF e: this.children) {
				if(!(e instanceof ExecBlockTransaction)) {
					throw new DBTException("session(" + this.getName() + 
							")'s child must be transaction");
				}
				
				ExecBlockTransaction execTx = (ExecBlockTransaction)e;
				txIdx++;
				weightIdx += execTx.getIntWeight();
			}
			
			this.execTxs = new ExecBlockTransaction[txIdx];
			this.txWeightMap = new int[weightIdx];
			
			weightIdx = 0;
			txIdx = 0;
			for(ExecIF e: this.children) {
				ExecBlockTransaction execTx = (ExecBlockTransaction)e;
				execTxs[txIdx] = execTx;
				for(int i = 0; i < execTx.getIntWeight(); i++)
					this.txWeightMap[weightIdx++] = txIdx;
				txIdx++;
			}
			
			this.rand = new Random();
		}
		*/
	}
	
	public void runInternal() throws DBTException {
		try {
			this.result.begin();
			
			if(this.reconnect)
				this.sCtx.reconnect();
			
			/* TODO ExecBlock Transactions로 이동
			if(this.random) {
				int wIdx = this.rand.nextInt(this.txWeightMap.length);
				execTxs[this.txWeightMap[wIdx]].run();
			} else {
				for(ExecIF e: this.children)
					e.run();
			}
			*/
			for(ExecIF e: this.children)
				e.run();
			
			this.result.setSuccess();
		} catch(DBTException e) {
			if(this.mCtx.isDebug())
				this.mCtx.writeDebug(this.name + " catches exception " + 
						e.getMessage());
			this.result.setFail(-1, e.getMessage());
			this.mCtx.writeError(e);
			
			// connection에러의 경우 1초 sleep을 둬서 CPU 100% 소모하며 돌지 않도록 처리
			Throwable t = e.getCause();
			if(t != null && t instanceof SQLException) {
				if(isConnectionError(((SQLException)t).getErrorCode())) {
					synchronized (this) {
						try {
							wait(1000);
						} catch(Exception ee) {
							// do nothing
						}
					}
				}
			}
			
			if(!this.ignorethrow)
				throw e;
			
		} finally {}
	}
	
	public void cleanupInternal() throws DBTException {
		Connection con = null;
		
		try {
			con = this.sCtx.getConnection();
			if(con != null)
				con.close();
		} catch(SQLException e) {
			// do nothing
		}
	}
	
	public void setCount(String val) {
		if(val == null || val.isEmpty())
			return;
		
		String cnt = null;
		if(val.equalsIgnoreCase("${@session.count}")) {
			cnt = this.mCtx.getConfig().getParam("session.count");
			cnt = (cnt == null || cnt.isEmpty()) ? "1" : cnt;
		} else {
			cnt = val;
		}
		
		this.countIntVal = Integer.parseInt(cnt);
	}
	
	public String getCount() {
		return Integer.toString(this.countIntVal);
	}
	
	public int getCountInt() {
		return this.countIntVal;
	}
	
	public void setDuration(String val) {
		this.duration = val;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public void setRepeat(int val) {
		this.repeat = val;
	}
	
	public int getRepeat() {
		return this.repeat;
	}
	
	public void setDatabase(String val) {
		this.database = val;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	public void setSchedule(String val) {
		this.schedule = val;
	}
	
	public String getSchedule() {
		return this.schedule;
	}
	
	public String getSelectedDatabase() {
		return this.myDatabase;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String val) {
		this.password = val;
	}
	
	public void setProgName(String val) {
		this.progName = val;
	}
	
	public String getProgName(String val) {
		return this.progName;
	}
	
	public void setReconnect(String val) {
		this.reconnect = UtilString.isPositiveOption(val);
	}
	
	public void setIgnorethrow(String val) {
		this.ignorethrow = UtilString.isPositiveOption(val);
	}
	
	protected String selectDatabase() {
		if(this.database == null || "".equals(this.database)) {
			return "default";
		}
		
		String tmp[] = this.database.split(",");
		String conns[] = new String[tmp.length];
		int weights[] = new int[tmp.length];
		int weightTotal = 0;
		
		for(int i = 0; i < tmp.length; i++) {
			
			String con = tmp[i].trim();
			
			if(con == null || "".equals(con)) {
				conns[i] = "";
				weights[i] = 0;
			} else if(con.indexOf(":") < 0) {
				conns[i] = con;
				weights[i] = 1;
			} else {
				String xxx[] = con.split(":");
				conns[i] = xxx[0].trim();
				weights[i] = Integer.parseInt(xxx[1].trim());
			}
			weightTotal += weights[i];
		}
		
		int[] weightMap = new int[weightTotal];
		int idx = 0;
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[i]; j++)
				weightMap[idx++] = i;
		}
		Random rand = new Random();
		return conns[rand.nextInt(weightMap.length)];
	}

	protected boolean isConnectionError(int ec) {
		
		if(this.conErrCodes == null)
			return false;
		
		for(int e: this.conErrCodes) {
			if(e == ec)
				return true;
		}
		
		return false;
	}
}
