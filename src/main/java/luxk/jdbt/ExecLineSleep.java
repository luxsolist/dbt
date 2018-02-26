package luxk.jdbt;

public class ExecLineSleep extends ExecLine {
	
	private long time = 0;
	
	public ExecLineSleep(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {

		ExecLineSleep exec = new ExecLineSleep(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setTime(Long.toString(this.time));

		return exec;
	}
	
	@Override
	public void prepare() throws DBTException {}

	@Override
	public void runInternal() throws DBTException {
		try {
			this.wait(this.time);
		} catch(InterruptedException e) {
			throw new DBTException("Fail to exec sleep(" + this.time + ")", e);
		}
	}
	
	public void setTime(String str) throws DBTException {
		this.time = UtilConverter.parseTimeMilli(str);
		
		if(this.time < 1)
			throw new DBTException("invalid time value " + this.time,
					this.line);
	}

}
