package luxk.jdbt;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

public class Result {
	
	private ExecIF exec = null;
	private String name = null;
	
	private long beginTime = -1; // 처음 begin이 호출된 시간
	private long endTime = -1;   // 마지막 end가 호출된 시간
	
	private long runTime = 0; // begin ~ end 사이 수행 누적 시간
	private long count = 0;    // end가 호출된 횟수. 즉 해당 구간 호출 횟수

	private long checkpoint = 0;

	private boolean isSuccess = false;
	private int errorCode = -1;
	private String errorMsg = null;
	
	private long successCount = 0;
	private long failCount = 0;
	
	private long prevSuccessCount = 0;
	private long prevFailCount = 0;
	
	public Result(ExecIF exec, String name) {
		this.exec = exec;
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ExecIF getExec() {
		return this.exec;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void begin() {
		// 여러번 호출되더라도 처음 한번 호출시만 설정
		if(this.beginTime < 0)
			this.beginTime = System.currentTimeMillis();
		
		this.checkpoint = System.nanoTime();
	}
	
	// millisec
	public long getBeginTime() {
		return this.beginTime;
	}
	
	public Date getBeginDate() {
		return new Date(this.beginTime);
	}
	
	private void end() {
		this.runTime += (System.nanoTime() - this.checkpoint);
		this.count++;

		// 여러번 호출되면 가장 마지막에 호출된 시간을 저장하도록 항상 갱신
		this.endTime = System.currentTimeMillis();
	}
	
	// millisec
	public long getEndTime() {
		return this.endTime;
	}
	
	public Date getEndDate() {
		return new Date(this.endTime);
	}
	
	// millisec
	public long getElapsedTime() {
		return (this.endTime - this.beginTime);
	}
	
	// millisec
	public long getRunTime() {
		return this.runTime / 1000000;
	}
	
	// nanosec
	public long getRunTimeNano() {
		return this.runTime;
	}
	
	public long getCount() {
		return this.count;
	}
	
	public void setSuccess() {
		end();
		this.isSuccess = true;
		this.errorCode = 0;
		this.errorMsg = "success";
		this.successCount++;
	}
	
	public void setFail(int code, String msg) {
		end();
		this.isSuccess = false;
		this.errorCode = code;
		this.errorMsg = msg;
		this.failCount++;
		
		//System.out.println("error(" + code + "): " + msg);
	}

	public void setFail(Exception e) {
		end();
		this.isSuccess = false;
		
		if(e instanceof SQLException)
			this.errorCode = ((SQLException)e).getErrorCode();
		else
			this.errorCode = -1;
		
		this.errorMsg = e.getMessage();
		this.failCount++;
		
		//e.printStackTrace();
	}

	public boolean isSuccess() {
		return this.isSuccess;
	}
	
	public void setErrorCode(int code) {
		this.errorCode = code;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorMsg(String msg) {
		this.errorMsg = msg;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	public long getSuccessCount() {
		return this.successCount;
	}
	
	public long getFailCount() {
		return this.failCount;
	}

	public long getRecentSuccessCount() {
		long res = this.successCount - this.prevSuccessCount;
		this.prevSuccessCount = this.successCount;
		
		return res;
	}
	
	public long getRecentFailCount() {
		long res = this.failCount - this.prevFailCount;
		this.prevFailCount = this.failCount;
		
		return res;
	}
	
	public float getTPS() {
		return ((float)successCount / ((float)runTime / (1000*1000*1000)));
	}
	
	public void printPerformance(PrintWriter pw, int indent) {
		
		float time = (float)this.runTime / (1000 * 1000 * 1000);
		String str = String.format(
				"[%s] time(sec):%.3f succ:%d fail:%d TPS:%.3f",
				this.name, time, this.successCount, this.failCount, getTPS());
		
		for(int i = 0; i < indent; i++)
			pw.append(" ");
		
		pw.println(str);
	}
	
	public void printFunctional(PrintWriter pw, int indent) {
		
	}
}
