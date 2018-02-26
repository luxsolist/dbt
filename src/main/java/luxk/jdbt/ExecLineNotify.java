package luxk.jdbt;

public class ExecLineNotify extends ExecLine {
	
	public ExecLineNotify(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {

		ExecLineNotify exec = new ExecLineNotify(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);

		return exec;
	}

	@Override
	public void runInternal() throws DBTException {
		// TODO Auto-generated method stub

	}
}
