package luxk.jdbt;

import java.util.Iterator;

// XXX
public class ExecBlockThread extends ExecBlock {
	
	private int count = 1;
	private int repeat = -1;
	private String duration = null;
	
	private Worker wthrs[] = null;
	
	public ExecBlockThread(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		ExecBlockThread exec = new ExecBlockThread(this.name, parent, this.mCtx);
		exec.setCount(this.count);
		exec.setDuration(this.duration);
		exec.setRepeat(this.repeat);
		
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
	public void prepareInternal() throws DBTException, DataException {

		if(this.count < 0 || this.count > 10000000)
			throw new DBTException("invalid thread count " + this.count);
	}
	
	@Override
	public void runInternal() throws DBTException {
		
		this.result.begin();
		
		// start work thread
		for(int i = 0; i < this.wthrs.length; i++) {
			this.wthrs[i].start();
		}

		this.result.setSuccess();
	}

	@Override
	public void cleanupInternal() throws DBTException {}
	
	public void setCount(int val) {
		this.count = val;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setRepeat(int val) {
		this.repeat = val;
	}
	
	public int getRepeat() {
		return this.repeat;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String val) {
		this.duration = val;
	}
	
	public boolean isFinished() {
		boolean finished = true;
		
		for(int i = 0; i < this.wthrs.length; i++) {
			if(this.wthrs[i].isFinished()) {
				finished = false;
			}
		}
		
		return finished;
	}

}
