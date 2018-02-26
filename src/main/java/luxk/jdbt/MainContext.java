package luxk.jdbt;

import java.io.PrintStream;
import java.io.PrintWriter;

import luxk.jdbt.config.Config;
import luxk.jdbt.config.ConfigXMLParser;

public class MainContext {
	
	/*
	public enum TestType {
		PERFORMANCE_TEST,
		FUNCTIONAL_TEST,
		AVAILABILITY_TEST
	}
	private TestType testType = TestType.PERFORMANCE_TEST;
	*/
	
	private Config conf = null;

	private boolean writeDebug = false;
	private boolean writeError = false;
	
	private int threadCount = 1;
	private int duration = 0; // second
	private int checkInterval = 10; // second
	
	private boolean runPrework = true;
	private boolean runMain = true;
	private boolean runPostwork = true;
	
	private boolean started = false;
	
	private Display rep = null;
	private String progName = "DBT_client";

	public void loadConfig(String path) throws DBTException {
		
		ConfigXMLParser p = new ConfigXMLParser();
		
		try {
			this.conf = p.parseConfig(path);
			this.writeDebug = this.conf.getParamBoolean("log.debug");
			this.writeError = this.conf.getParamBoolean("log.testError");
		} catch(Exception e) {
			throw new DBTException("fail to load config " + path, e);
		} finally {}
		
	}
	
	public Config getConfig() {
		return this.conf;
	}

	/*
	public TestType getTestType() {
		return this.testType;
	}
	
	public void setTestType(TestType type) {
		this.testType = type;
	}
	*/
	
	public boolean isDebug() {
		return this.writeDebug;
	}
	
	public void writeDebug(String str) {
		System.out.println(str);
	}
	
	public void writeError(Exception e) {
		if(this.writeError) {
			PrintWriter pw = new PrintWriter(System.out);
			e.printStackTrace(pw);
		}
	}
	
	public PrintStream getWriteStream() {
		return System.out;
	}
	public void write(String str) {
		System.out.println(str);
	}
	
	public int getThreadCount() {
		return this.threadCount;
	}
	
	public void setThreadCount(int cnt) {
		this.threadCount = cnt;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(int val) {
		this.duration = val;
		if(this.duration < 0)
			this.duration = 0;
	}
	
	public void setCheckInternal(int val) {
		this.checkInterval = val;
		if(this.checkInterval < 0)
			this.checkInterval = 0;
	}
	
	public int getCheckInterval() {
		return this.checkInterval;
	}
	
	public boolean isRunPrework() {
		return runPrework;
	}

	public void setRunPrework(boolean runPrework) {
		this.runPrework = runPrework;
	}

	public boolean isRunMain() {
		return runMain;
	}

	public void setRunMain(boolean runMain) {
		this.runMain = runMain;
	}

	public boolean isRunPostwork() {
		return runPostwork;
	}

	public void setRunPostwork(boolean runPostwork) {
		this.runPostwork = runPostwork;
	}
	
	public void setStarted(boolean val) {
		this.started = val;
	}
	
	public boolean isStarted() {
		return this.started;
	}
	
	public void setReporter(Display rep) {
		this.rep = rep;
	}
	
	public Display getReporter() {
		return this.rep;
	}
	
	public void setProgName(String val) {
		if(val.length() > 15)
			this.progName = val.substring(0, 15);
		else
			this.progName = val;
	}
	
	public String getProgName() {
		return this.progName;
	}
	
}
