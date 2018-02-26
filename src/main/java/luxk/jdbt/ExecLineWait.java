package luxk.jdbt;

public class ExecLineWait extends ExecLine {
	
	public ExecLineWait(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {

		ExecLineWait exec = new ExecLineWait(this.name, parent, this.mCtx);
		
		exec.setParent(parent);
		exec.setMainContext(this.mCtx);
		exec.setName(this.name);
		exec.setLine(this.line);

		return exec;
	}

	@Override
	public void runInternal() throws DBTException {
		// TODO Auto-generated method stub

	}
}
