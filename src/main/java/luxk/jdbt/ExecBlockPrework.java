package luxk.jdbt;

import java.util.ArrayList;

import luxk.jdbt.WorkerFactory.WorkerType;

public class ExecBlockPrework extends ExecBlock {
	
	protected ArrayList<Worker> workers = new ArrayList<Worker>();
	
	public ExecBlockPrework(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport duplicate",
				this.line);
	}

	@Override
	public void prepareInternal() throws DBTException, DataException {
		
		if(!this.mCtx.isRunPrework())
			return;
		
		ArrayList<ExecIF> orgList = this.children;
		this.children = new ArrayList<ExecIF>();

		WorkerFactory wFact = new WorkerFactory(WorkerType.FUNCTIONAL, this.mCtx);
		while(orgList.size() > 0) {
			
			ExecIF exec = orgList.remove(0);
			
			int cnt;
			String duration;
			int repeat;
			if(exec instanceof ExecBlockSession) {
				cnt = ((ExecBlockSession)exec).getCountInt();
				duration = ((ExecBlockSession)exec).getDuration();
				repeat = ((ExecBlockSession)exec).getRepeat();
			} else if(exec instanceof ExecBlockThread) {
				cnt = ((ExecBlockThread)exec).getCount();
				duration = ((ExecBlockThread)exec).getDuration();
				repeat = ((ExecBlockThread)exec).getRepeat();
			} else {
				throw new DBTException(exec.getClass().getName() +	" is unsupported child class of " +
						this.getClass().getName(), this.line);
			}
			
			Worker w = wFact.getWorker("Worker For Performance Test");
			w.setDuration(duration);
			w.setRepeat(repeat);
			w.setExec(exec);
			w.setStopOnException(true);
			
			this.children.add(exec);
			this.workers.add(w);
			
			for(int i = 1; i < cnt; i++) {
				ExecIF newExec = exec.duplicate(this);
				Worker newWork = wFact.getWorker("Worker For Performance Test");
				newWork.setDuration(duration);;
				newWork.setRepeat(repeat);
				newWork.setExec(newExec);
				newWork.setStopOnException(true);
				
				this.children.add(newExec);
				this.workers.add(newWork);
			}
		}
	}

	@Override
	public void runInternal() throws DBTException {
		
		if(!this.mCtx.isRunPrework())
			return;
		
		this.result.begin();

		for(Worker w: this.workers)
			w.start();
	
		do {
			// wait for 1 sec
			synchronized(this) {
				try {
					wait(1 * 1000);
				} catch(InterruptedException e) {}
			}
		} while(!isFinished());
		
		DBTException e = null;
		for(Worker w: this.workers) {
			e = w.getException(); 
			if(e != null)
				throw e;
		}

		this.result.setSuccess();
	}
	
	public void cleanupInternal() throws DBTException {}
	
	private boolean isFinished() {
		boolean finished = true;
		
		for(Worker w: this.workers) {
			if(!w.isFinished())
				finished = false;
		}
		
		return finished;
	}
}
