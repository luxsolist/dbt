package luxk.jdbt;

import java.util.Iterator;

public class ExecBlockLoop extends ExecBlock {
	
	private int repeat = -1;
	private long until = -1; // nanosec
	
	private int count = 0;
	private long startTime = 0; // nanosec
	
	public ExecBlockLoop(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {

		ExecBlockLoop exec =
				new ExecBlockLoop(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setRepeat(Integer.toString(this.repeat));
		exec.setUntil(Long.toString(this.until));
		
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
		this.sCtx = getSessionContext();
	}
	
	@Override
	public void runInternal() throws DBTException {
		this.count = 0;
		this.startTime = System.nanoTime(); // nanosec
		
		try {
			this.result.begin();
			while((repeat > 0 && count++ < repeat) ||
					(until > 0 && System.nanoTime() - startTime < until)) {

				for(ExecIF e: this.children)
					e.run();
			}
			this.result.setSuccess();
		
		} catch(DBTException e) {
			this.result.setFail(-1, e.getMessage());
			throw e;
		} finally {
			
		}
	}

	@Override
	public void cleanupInternal() throws DBTException {}

	public void setRepeat(String str) throws DBTException {
		try {
			this.repeat = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			throw new DBTException(e);
		}
	}
	
	public void setUntil(String str) throws DBTException {
		try {
			this.until = UtilConverter.parseTimeMilli(str) * 1000 * 1000; // ns
		} catch(NumberFormatException e) {
			throw new DBTException(e);
		}
	}
}
