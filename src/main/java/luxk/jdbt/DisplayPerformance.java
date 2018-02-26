package luxk.jdbt;

import java.io.PrintStream;
import java.util.ArrayList;

public class DisplayPerformance implements Display {
	
	private long startTime = 0;
	private long checkTime = 0;
	private long interval = 0;
	
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
		this.checkTime = this.startTime;
	}
	
	public void printPartialHeader(PrintStream out) {
		out.println(UtilDate.getDateString("yyyy-MM-dd HH:mm:ss"));
		
		long curTime = System.currentTimeMillis();
		this.interval = curTime - this.checkTime;
		this.checkTime = curTime;
		
		printPartialPage(out);
	}
	
	public void printPartialPage(PrintStream out) {
		out.println(
" time    success       fail    cur TPS      success       fail  total TPS");
		out.println(
"==========================================================================");
	}
	
	private void gatherPartialResult(Result res, DataPartial data) {
		// ExecBlockSession의 최근 TPS만 합산
		if(ExecBlockSession.class.equals(res.getExec().getClass())) {
			data.recentSuccessCnt += res.getRecentSuccessCount();
			data.recentFailCnt += res.getRecentFailCount();
			data.totalSuccessCnt += res.getSuccessCount();
			data.totalFailCnt += res.getFailCount();
		}
	}
	
	public void printPartialResult(Result res, PrintStream out) {
		long curTime = System.currentTimeMillis();
		this.interval = curTime - this.checkTime;
		this.checkTime = curTime;
		long diffTime = this.checkTime - this.startTime;
		DataPartial data = new DataPartial();
		
		gatherPartialResult(res, data);
		
		String str = String.format(
				"%5d %10d %10d %10d   %10d %10d %10d",
				diffTime/1000,
				data.recentSuccessCnt, data.recentFailCnt,
				data.recentSuccessCnt / (this.interval / 1000),
				data.totalSuccessCnt, data.totalFailCnt,
				data.totalSuccessCnt / (diffTime / 1000));
		out.println(str);
	}
	
	public void printPartialTail(PrintStream out) {
		out.println(
"==========================================================================");
	}
	
	private DataOverall findDataOverall(ArrayList<DataOverall> list,
			String name) {
		for(DataOverall e: list)
			if(name.equals(e.name))
				return e;
		
		DataOverall res = new DataOverall();
		res.name = name;
		list.add(res);
		
		return res;
	}
	
	private void gatherOverallResult(Result res, DataOverall sess,
			ArrayList<DataOverall> conn, ArrayList<DataOverall> tx) {
		
		if(ExecBlockSession.class.equals(res.getExec().getClass())) {
			sess.time += res.getRunTime();
			sess.succ += res.getSuccessCount();
			sess.fail += res.getFailCount();
			sess.tps += res.getTPS();
			
			ExecBlockSession execSess = (ExecBlockSession)res.getExec();
			
			DataOverall data = findDataOverall(conn, execSess.getSelectedDatabase());
			data.cnt++;
			data.time += res.getRunTime();
			data.succ += res.getSuccessCount();
			data.fail += res.getFailCount();
			data.tps += res.getTPS();
		}

		if(ExecBlockTransaction.class.equals(res.getExec().getClass())) {
			DataOverall data = findDataOverall(tx, res.getName());
			data.cnt++;
			data.time += res.getRunTime();
			data.succ += res.getSuccessCount();
			data.fail += res.getFailCount();
			data.tps += res.getTPS();
		}
	}
	
	public void printOverallResult(Result res, PrintStream out) {
		
		DataOverall sess = new DataOverall();
		ArrayList<DataOverall> conn = new ArrayList<DataOverall>();
		ArrayList<DataOverall> tx = new ArrayList<DataOverall>();

		gatherOverallResult(res, sess, conn, tx);
		
		String str = String.format(
				"Total success:%d,  fail:%d,  TPS:%.3f",
				sess.succ, sess.fail, sess.tps);
		out.println(str);

		if(conn.size() > 1) {
			out.println("\nPer Connection");
			for(DataOverall e: conn) {
				float time = (float)e.time / (1000 * e.cnt);
				str = String.format(
					"  [%s] avg_time(sec):%.3f, success:%d, fail:%d, TPS:%.3f",
					e.name, time, e.succ, e.fail, e.tps);
				out.println(str);
			}
		}

		out.println("\nPer TX");
		for(DataOverall e: tx) {
			float time = (float)e.time / (1000 * e.cnt);
			str = String.format(
					"  [%s] avg_time(sec):%.3f, success:%d, fail:%d, TPS:%.3f",
					e.name, time, e.succ, e.fail, e.tps);
			out.println(str);
		}
	}
	
	private void printDetailedResultInternal(Result res, PrintStream out,
			int indent) {
		float time = (float)res.getRunTime() / 1000;
		String str = String.format(
				"[%s] time(sec):%.3f, success:%d, fail:%d, TPS:%.3f",
				res.getName(), time, res.getSuccessCount(), res.getFailCount(),
				res.getTPS());
		
		for(int i = 0; i < indent; i++)
			out.print(" ");
		
		out.println(str);
	}

	public void printDetailResult(Result res, PrintStream out) {
		out.println("\nDetail Result:");
		printDetailedResultInternal(res, out, 0);
	}
}

class DataPartial {
	public long recentSuccessCnt = 0;
	public long recentFailCnt = 0;
	public long totalSuccessCnt = 0;
	public long totalFailCnt = 0;
}

class DataOverall {
	public String name = null;
	public int cnt = 0;
	public long time = 0;
	public long succ = 0;
	public long fail = 0;
	public float tps = 0;
}
