package luxk.jdbt;

public abstract class AbstractExec implements ExecIF {
	
	protected String name = null;
	protected ExecIF parent = null;
	protected int line = -1;
	
	protected MainContext mCtx = null;

	// @warning sCtx is thread unsafe!!!
	// @warning sCtx available after prepare
	protected SessionContext sCtx = null;
	protected Result result = null;
	
	public AbstractExec(String name, ExecIF parent, MainContext mCtx) {
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
	}

	public void setName(String name) {
		this.name = name;
		this.result.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setParent(ExecIF parent) {
		this.parent = parent;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public void setMainContext(MainContext mCtx) {
		this.mCtx = mCtx;
	}
	
	public MainContext getMainContext() {
		return this.mCtx;
	}

	public SessionContext getSessionContext() throws DBTException {
		if(this.sCtx == null)
			this.sCtx = ((AbstractExec)this.parent).getSessionContext();
		
		if(this.sCtx == null)
			throw new DBTException("SessionContext is null", this.line);
		
		return this.sCtx;
	}
	
	public void run() throws DBTException {
		
		if(!this.mCtx.isStarted())
			return;
		
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("run " + this.getClass().getName() +
					"(" + this.name + ")");

		runInternal();
	}
	
	public abstract DataIF getData(String name);
	public abstract void prepare() throws DBTException, DataException;
	public abstract void runInternal() throws DBTException;
	public abstract void cleanup() throws DBTException;
	
}
