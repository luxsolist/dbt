package luxk.jdbt;

public class ExecBlockCase extends ExecBlock {
	
	private String category1 = null;
	private String category2 = null;
	private int caseVersion = 1;
	private Version dbVerFrom = null;
	private Version dbVerTo = null;
	
	private ExecBlockPrework prework = null;
	private ExecBlock main = null;
	private ExecBlockPostwork postwork = null;
	
	public ExecBlockCase(String name, ExecIF parent,
			MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}
	
	@Override
	public void addChild(ExecIF child) throws DBTException {
		if(child instanceof ExecBlockPrework) {
			this.prework = (ExecBlockPrework)child;
		} else if(child instanceof ExecBlockPostwork) {
			this.postwork = (ExecBlockPostwork)child;
		} else if(child instanceof ExecBlockPerformanceTest || child instanceof ExecBlockFunctionalTest) {
			this.main = (ExecBlock)child;
		} else if (child instanceof ExecLineDescription) {
			// do nothing
		} else {
			throw new DBTException(
				child.getClass().getName() + 
				" is unsupported class to add Case's child", this.line);
		}
		this.children.add(child);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport duplicate",
				this.line);
	}

	@Override
	public void prepare() throws DBTException {
		if(this.main == null)
			throw new DBTException("test main is null", this.line);
	}
	
	@Override
	public void prepareInternal() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport prepareInternal",
				this.line);
	}
	
	@Override
	public void runInternal() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport runInternal",
				this.line);
	}
	
	@Override
	public void run() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport run",
				this.line);
	}
	
	@Override
	public void cleanup() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport cleanup",
				this.line);
	}
	
	@Override
	public void cleanupInternal() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport cleanupInternal",
				this.line);
	}

	public void runPrework() throws DBTException, DataException {
		if(this.prework != null && this.mCtx.isRunPrework()) {
			this.prework.prepare();
			this.prework.run();
			this.prework.cleanup();
		}
	}
	
	public void runMain() throws DBTException, DataException {
		if(this.main != null && this.mCtx.isRunMain()) {
			this.main.prepare();
			this.main.run();
			this.main.cleanup();
		}
	}
	
	public void runPostwork() throws DBTException, DataException {
		if(this.postwork != null && this.mCtx.isRunPostwork()) {
			this.postwork.prepare();
			this.postwork.run();
			this.postwork.cleanup();
		}
	}
	
	@Override
	public SessionContext getSessionContext() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport session context",
				this.line);
	}
	
	public String getCategory1() {
		return this.category1;
	}
	
	public void setCategory1(String val) {
		this.category1 = val;
	}

	public String getCategory2() {
		return this.category2;
	}
	
	public void setCategory2(String val) {
		this.category2 = val;
	}

	public int getCaseVersion() {
		return this.caseVersion;
	}
	
	public void setCaseVersion(int val) {
		this.caseVersion = val;
	}
	
	public String getDatabaseVersion() {
		return String.format("%s-%s",
				(this.dbVerFrom == null ? "" : this.dbVerFrom), (this.dbVerTo == null ? "" : this.dbVerTo));
	}
	
	public String getDatabaseVersionFrom() {
		return this.dbVerFrom.toString();
	}
	
	public String getDatabaseVersionTo() {
		return this.dbVerTo.toString();
	}
	
	public void setDatabaseVersion(String str) throws DBTException {
		
		this.dbVerFrom = null;
		this.dbVerTo = null;
		
		if (str == null || str.isEmpty())
			return;
		
		str = str.trim();
		
		int posDash = str.indexOf("-");
		
		if(posDash < 0) {
			Version v = Version.parseVersion(str);
			this.dbVerFrom = v;
			this.dbVerTo = v;
		} else {
			if(str.length() < 2)
				throw new DBTException("Invalid version format(too short): " + str);
			
			int lastDash = str.lastIndexOf("-");
			
			if(posDash != lastDash)
				throw new DBTException("Invalid version format(wrong version range): " + str);
			
			String s = str.substring(0, posDash);
			if(s != null && !s.isEmpty())
				this.dbVerFrom = Version.parseVersion(s);
			
			s = str.substring(lastDash+1);
			if(s != null && !s.isEmpty())
				this.dbVerTo = Version.parseVersion(s);
		}
		
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug(String.format("set database version %s-%s", this.dbVerFrom, this.dbVerTo));
	}
	
	public boolean checkDatabaseVersion(String ver) throws DBTException {
		Version v = Version.parseVersion(ver);
		if(this.dbVerFrom != null && v.compareTo(this.dbVerFrom) < 0) return false;
		if(this.dbVerTo != null && v.compareTo(this.dbVerTo) > 0) return false;
		return true;
	}
}
