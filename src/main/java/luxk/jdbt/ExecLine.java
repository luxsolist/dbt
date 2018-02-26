package luxk.jdbt;

import java.io.PrintStream;

public abstract class ExecLine extends AbstractExec {
	
	public ExecLine(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
	}

	public void addChild(ExecIF child) throws DBTException {
		throw new DBTException("ProcLine not support addChild");
	}

	public ExecIF[] getChildren() throws DBTException {
		throw new DBTException("ProcLine not support getChildren");
	}
	
	@Override
	public void prepare() throws DBTException {
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("prepare " + this.getClass().getName());
		
		this.sCtx = getSessionContext();
	}
	
	public void cleanup() throws DBTException {
		// do nothing
	}

	public Result getResult() {
		return this.result;
	}
	
	public void addData(DataIF data) throws DBTException {
		throw new DBTException("ExecLine not support addData");
	}

	public DataIF getData(String name) {
		return this.parent.getData(name);
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(this.getName());
		buf.append("(").append(this.getClass().getName()).append(")");
		
		if(this.parent != null) {
			buf.append("\n  parent: ").append(this.parent.getName());
			buf.append("(").append(this.parent.getClass().getName()).append(")");
		}
		
		return buf.toString();
	}

	public void dump() {
		this.mCtx.writeDebug(this.toString());
	}

	public void dump(PrintStream out) {
		out.println(this);
	}
}
