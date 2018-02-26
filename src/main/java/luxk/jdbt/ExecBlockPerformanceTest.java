package luxk.jdbt;

import java.io.PrintStream;
import java.util.ArrayList;

import luxk.jdbt.WorkerFactory.WorkerType;

public class ExecBlockPerformanceTest extends ExecBlock {
	
	protected ArrayList<Worker> workers = new ArrayList<Worker>();
	
	public ExecBlockPerformanceTest(String name, ExecIF parent,
			MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	@Override
	public void addChild(ExecIF child) throws DBTException {
		
		if(!(child instanceof ExecBlockSession) &&
				!(child instanceof ExecBlockThread))
			throw new DBTException(child.getClass().getName() + " is unsupported class to add child " +
						this.getClass().getName(), this.line);
		
		this.children.add(child);
	}

	@Override
	public void prepareInternal() throws DBTException, DataException {
		
		if(!this.mCtx.isRunMain())
			return;
		
		ArrayList<ExecIF> orgList = this.children;
		this.children = new ArrayList<ExecIF>();

		WorkerFactory wFact = new WorkerFactory(WorkerType.PERFORMANCE, this.mCtx);
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
			
			this.children.add(exec);
			this.workers.add(w);
			
			for(int i = 1; i < cnt; i++) {
				ExecIF newExec = exec.duplicate(this);
				Worker newWork = wFact.getWorker("Worker For Performance Test");
				newWork.setDuration(duration);;
				newWork.setRepeat(repeat);
				newWork.setExec(newExec);
				
				this.children.add(newExec);
				this.workers.add(newWork);
			}
		}
	}
	
	public ExecIF duplicate(ExecIF parent) throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport duplicate",
				this.line);
	}

	@Override
	public void runInternal() throws DBTException {
		
		if(!this.mCtx.isRunMain())
			return;

		long interval = this.mCtx.getCheckInterval() * 1000; // ms
		long startTime = System.currentTimeMillis();
		long checkTime = startTime;
		Display rep = this.mCtx.getReporter();
		PrintStream out = this.mCtx.getWriteStream();
		int printCnt = 0;
		
		this.result.begin();
		rep.setStartTime();
		
		for(Worker w: this.workers)
			w.start();
		
		if(interval > 0)
			rep.printPartialHeader(out);
		
		do {
			// wait for 1 sec
			synchronized(this) {
				try {
					wait(1 * 1000);
				} catch(InterruptedException e) {}
			}
			
			// check and gather statistics periodically
			if(interval > 0 &&
					System.currentTimeMillis() - checkTime > interval) {
				checkTime = System.currentTimeMillis();
				if(++printCnt % 40 == 0)
					rep.printPartialPage(out);
				rep.printPartialResult(this.result, out);
			}

		} while(!isFinished());

		this.result.setSuccess();
		
		if(interval > 0)
			rep.printPartialTail(out);
		
		// gather result
		rep.printOverallResult(this.result, out);
	}
	
	@Override
	public void cleanupInternal() throws DBTException {}
	
	private boolean isFinished() {
		boolean finished = true;
		
		for(Worker w: this.workers) {
			if(!w.isFinished())
				finished = false;
		}
		
		return finished;
	}

	@Override
	public SessionContext getSessionContext() throws DBTException {
		throw new DBTException(
				this.getClass().getName() + " unsupport getSessionContext",
				this.line);
	}

}
