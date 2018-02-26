package luxk.jdbt;

public class ExecLineExpect extends ExecLine {
	
	private String text = null;
	
	public ExecLineExpect(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {

		ExecLineExpect exec = new ExecLineExpect(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setText(this.text);

		return exec;
	}

	@Override
	public void runInternal() throws DBTException {
		// TODO Auto-generated method stub

	}
	
	public void setSqlcode(String str) throws DBTException {
		
	}
	
	public void setRows(String str) throws DBTException {
	}
	
	public void setText(String str) {
		
	}

}
