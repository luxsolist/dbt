package luxk.jdbt;

public class ExecBlockFunctionalTest extends ExecBlock {
	
	public ExecBlockFunctionalTest(String name, ExecIF parent,
			MainContext mCtx) {
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
	public void prepareInternal() throws DBTException, DataException {

		if(!this.mCtx.isRunMain())
			return;
		
		if(this.children.size() == 0)
			throw new DBTException(
					this.getClass().getName() + " has no child " +
					ExecBlockSession.class.getName(), this.line);
			
		if(this.children.size() != 1) {
			ExecIF child0 = this.children.get(0);
			throw new DBTException(
					this.getClass().getName() +
					" permit only one child " +
							child0.getClass().getName(), this.line);
		}
	}

	@Override
	public void cleanupInternal() throws DBTException {}
}
