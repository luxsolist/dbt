package luxk.jdbt;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Worker implements Runnable {

	
	protected byte lock[] = new byte[0];
	
	protected String name = null;
	protected MainContext mCtx = null;
	protected int id = 0;
	protected ExecIF exec = null;
	protected Thread thread = null;
	protected int duration = -1; // sec
	protected int repeat = -1;
	protected boolean isStarted = false;
	protected boolean stopOnException = false;
	protected DBTException exception = null;
	
	protected long startTime;
	protected int runCount;
	
	public Worker(String name, MainContext mCtx) {
		this.name = name;
		this.mCtx = mCtx;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setExec(ExecIF exec) throws DBTException {
		
		if(!(exec instanceof ExecBlockSession) && !(exec instanceof ExecBlockThread))
			throw new DBTException(exec.getClass().getName() + " is unsupported class to set exec of " +
					this.getClass().getName());

		this.exec = exec;
	}
	
	public void setDuration(int val) {
		this.duration = val;
	}
	
	public void setDuration(String val) {
		long tmp = UtilConverter.parseTimeMilli(val) / 1000;
		this.duration = tmp > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)tmp;
	}
	
	public void setRepeat(int val) {
		this.repeat = val;
	}
	
	public void setSchedule(String val) {
		
	}
	
	public void setStopOnException(boolean val) {
		this.stopOnException = val;
	}
	
	public void start() {
		this.isStarted = true;
		this.thread = new Thread(this);
		this.thread.start();
		
	}
	
	public void start(int duration) {
		this.duration = duration;
		start();
	}
	
	public void stop() {
		this.isStarted = false;
		this.thread = null;
	}
	
	public boolean isFinished() {
		return !this.isStarted;
	}
	
	public DBTException getException() {
		return this.exception;
	}
		
	public void waitForSync(long millisec) {

		// wait for 
		synchronized(this.lock) {
			try {
				if(millisec <= 0)
					this.lock.wait();
				else
					this.lock.wait(millisec);
			} catch(InterruptedException e) {
				// do nothing
			}
		}
	}

	public void awake() {
		this.lock.notifyAll();
	}
	
	public void run() {
		this.startTime = System.currentTimeMillis();
		this.runCount = 0;

		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("Worker(erformance test) " + this.name + 
					"(#" + this.id + ") started");

		do {
			if(!this.mCtx.isStarted())
				break;
			
			this.runCount++;
			
			try {
				this.exec.run();
			} catch(DBTException ee) {
				this.mCtx.writeError(ee);
				this.exception = ee;
			}
		} while(!checkFinished());
		
		this.isStarted = false;
		
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("Worker(performance test) " + this.name + 
					"(#" + this.id + ") finished");
	}
	
	protected boolean checkFinished() {
		boolean durFinished =
				((this.duration < 0) || (System.currentTimeMillis() - this.startTime >= this.duration * 1000)) ?
						true : false;
		boolean repFinished = ((this.repeat < 0) || this.runCount >= this.repeat) ? true : false;
		boolean errFinished = ((this.stopOnException && this.exception != null)) ? true : false;
		
		return (durFinished && repFinished) || errFinished;
	}
}

class CronSchedule {
	
	private boolean[] secs   = new boolean[61];  // 0~61
	private boolean[] mins   = new boolean[60];  // 0~59
	private boolean[] hours  = new boolean[24];  // 0~23
	private boolean[] days   = new boolean[31];  // 0~30 (-1)
	private boolean[] months = new boolean[12];  // 0~11
	private boolean[] weeks  = new boolean[7];   // 0~6 (일월화수목금토)
	
	Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.KOREAN);;
	
	public CronSchedule(String schdPattern) throws DBTException {
		parsePattern(schdPattern);
	}
	
	public boolean isScheduledTime() {
		
		this.cal.setTimeInMillis(System.currentTimeMillis());
		
		int sec = cal.get(Calendar.SECOND);
		int min = cal.getMaximum(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int day = cal.get(Calendar.DAY_OF_MONTH)-1;
		int month = cal.get(Calendar.MONTH);
		int week = cal.get(Calendar.DAY_OF_WEEK)-1;
		
		return (this.secs[sec] && this.mins[min] && this.hours[hour] && 
				this.months[month] && (this.days[day] || this.weeks[week]));
	}
	
	public boolean isScheduledTime(long milliTime) {
		this.cal.setTimeInMillis(milliTime);
		
		int sec = cal.get(Calendar.SECOND);
		int min = cal.getMaximum(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int day = cal.get(Calendar.DAY_OF_MONTH)-1;
		int month = cal.get(Calendar.MONTH);
		int week = cal.get(Calendar.DAY_OF_WEEK)-1;
		
		return (this.secs[sec] && this.mins[min] && this.hours[hour] && 
				this.months[month] && (this.days[day] || this.weeks[week]));
	}
	
	private void parsePattern(String val) throws DBTException {
		
		if(val == null || val.isEmpty())
			throw new DBTException("Schedule is null");
		
		String[] s = val.trim().split(" ");
			
		if(s.length != 6)
			throw new DBTException("Schedule is missing(" + val + ")");
		
		setArray(s[0], this.secs);
		setArray(s[1], this.mins);
		setArray(s[2], this.hours);

		setArray(s[4], this.months);

		// day는 *이고 week는 특정 값이 설정되어 있으면 week만 특정 값으로 on(day || week에 의해)
		if("*".equals(s[3]) && !"*".equals(s[5])) {
			onoff(this.days, false);
			setArray(s[5], this.weeks);
		// day는 특정 값이 설정되어 있고 week는 *이면 day만 특정 값으로 on(day || week에 의해)
		} else if(!"*".equals(s[3]) && "*".equals(s[5])){
			setArray(s[3], this.days);
			onoff(this.weeks, false);
		// 나머지, day *, week * 이거나 day 특정 값, week 특정 값이면 둘 다 on(day || week 이므로 무해함)
		} else {
			setArray(s[3], this.days);
			setArray(s[5], this.weeks);
		}
	}
	
	private void onoff(boolean[] arr, boolean val) {
		for(int i = 0; i < arr.length; i++)
			arr[i] = val;
	}
	
	private void setArray(String val, boolean[] arr) throws DBTException {
		try {
			if("*".equals(val)) {
				for(int i = 0; i < this.secs.length; i++)
					this.secs[i] = true;
				return;
			}
			
			String[] sec = val.trim().split(",");
			for(String e: sec) {
				if(e.indexOf("-") >= 0) {
					String[] tmp = e.trim().split("-");
					int minVal = Integer.parseInt(tmp[0]);
					int maxVal = Integer.parseInt(tmp[1]);
					for(int i = minVal;i < maxVal; i++)
						this.mins[i] = true;
				} else {
					arr[Integer.parseInt(e)] = true;
				}
			}
		} catch(Exception e) {
			throw new DBTException("Invalid schedule string " + val, e);
		}
	}
}
