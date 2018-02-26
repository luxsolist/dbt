package luxk.jdbt;

import java.util.ArrayList;

public class Sync {
	
	// max timeout 1year
	private static final long MAX_TIMEOUT = 365 * 24 * 60 * 60 * 1000;
	
	private String name = "";
	
	int count = 1;
	long timeout = 0; // ms

	ArrayList<Worker> waiters = new ArrayList<Worker>();
	
	public void setName(String str) {
		this.name = str;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCount(int cnt) throws DBTException {
		
		if (cnt < 1)
			throw new DBTException("count must be >= 1");
		if (cnt > 10000)
			throw new DBTException("count must be <= 10000");
		
		this.count = cnt;
	}
	
	public void setTimeout(long millisec) throws DBTException {
		
		if (millisec < 0)
			throw new DBTException("timeout must be >= 1");
		if (millisec > MAX_TIMEOUT)
			throw new DBTException("timeout must be <= " + MAX_TIMEOUT);
		
		this.timeout = millisec;		
	}

	public void addWaiter(Worker waitThread) {
		synchronized(this) {
			waitThread.waitForSync(this.timeout);
			waiters.add(waitThread);
		}
	}
	
	public void notifyTo() {
		synchronized(this) {
			if (--this.count <= 0) {
				for(Worker e: this.waiters) {
					e.awake();
				}
				this.waiters.clear();
			}
		}
	}

}
