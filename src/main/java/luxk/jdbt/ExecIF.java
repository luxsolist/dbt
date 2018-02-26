package luxk.jdbt;

import java.io.PrintStream;

public interface ExecIF {
	
	public void setName(String name);
	public String getName();
	public void setLine(int line);
	
	public void setParent(ExecIF parent);
	public void addChild(ExecIF child) throws DBTException;
	public ExecIF[] getChildren() throws DBTException;
	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException;
	
	public void addData(DataIF data) throws DBTException;
	public DataIF getData(String name);
	
	public void prepare() throws DBTException, DataException;
	public void run() throws DBTException;
	public void cleanup() throws DBTException;

	public Result getResult();
	
	public void dump();
	public void dump(PrintStream out);
}
