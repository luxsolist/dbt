package luxk.jdbt;

import java.io.PrintStream;

public interface Display {
	
	public void setStartTime();
	
	public void printPartialHeader(PrintStream out);
	public void printPartialPage(PrintStream out);
	public void printPartialResult(Result res, PrintStream out);
	public void printPartialTail(PrintStream out);
	
	public void printOverallResult(Result res, PrintStream out);
	public void printDetailResult(Result res, PrintStream out);

}
