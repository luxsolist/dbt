package luxk.jdbt;

public class ExecLineDescription extends ExecLine {
	
	private String description = null;
	
	public ExecLineDescription(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		throw new DBTException(
				this.getClass().getName() + " unsupport duplicate",
				this.line);
	}
	
	@Override
	public SessionContext getSessionContext() throws DBTException {
		return null;
	}

	@Override
	public void runInternal() throws DBTException {
		// TODO Auto-generated method stub

	}

	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String str) {
		this.description = str;
	}
}
