package luxk.jdbt;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class ExecBlock extends AbstractExec {
	
	protected ArrayList<ExecIF> children = new ArrayList<ExecIF>();
	protected HashMap<String, DataIF> data = new HashMap<String, DataIF>();
	
	public ExecBlock(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
	}
	
	
	public void addChild(ExecIF child) throws DBTException {
		this.children.add(child);
	}

	public ExecIF[] getChildren() throws DBTException {
		ExecIF[] res = new ExecIF[this.children.size()];
		return (ExecIF[])this.children.toArray(res);
	}

	public Result getResult() {
		return this.result;
	}

	public void addData(DataIF data) throws DBTException {
		this.data.put(data.getName().toUpperCase(), data);
	}

	public DataIF getData(String name) {
		
		DataIF res = this.data.get(name.toUpperCase());
		
		if (res != null)
			return res;
		else if(this.parent != null)
			return this.parent.getData(name.toUpperCase());
		else
			return null;
	}
	
	public DataIF[] searchOrCreateData(String nameList) throws DBTException {
		
		if (nameList == null || "".equals(nameList))
			return null;
		
		String names[] = nameList.split(",");
		
		ArrayList<DataIF> list = new ArrayList<DataIF>();
		for(String e: names) {
			String name = e.trim();
			
			if(!name.startsWith("{") || !name.endsWith("}"))
				continue;
			
			name = name.substring(1, name.length()-1);
			DataIF data = getData(name);
			if(data == null) {
				data = DataFactory.factoryVar("string");
				data.setName(name);
				addData(data);
			}

			list.add(data);
		}
		
		DataIF[] ret = new DataIF[list.size()];
		return list.toArray(ret);
	}
	
	public void prepare() throws DBTException, DataException {
		
		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("prepare " + this.getClass().getName() +
					"(" + this.name + ")");
		
		prepareInternal();
		this.result.setName(this.name);
		
		for(ExecIF e: this.children)
			e.prepare();
	}

	public void runInternal() throws DBTException {
		try {
			this.result.begin();
			
			for(ExecIF e: this.children)
				e.run();
			
			this.result.setSuccess();
		} catch(DBTException e) {
			this.result.setFail(-1, e.getMessage());
			throw e;
		} finally {}
	}
	
	public void cleanup() throws DBTException {

		if(this.mCtx.isDebug())
			this.mCtx.writeDebug("cleanup " + this.getClass().getName() +
					"(" + this.name + ")");

		for(ExecIF e: this.children)
			e.cleanup();

		cleanupInternal();
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(this.getName());
		buf.append("(").append(this.getClass().getName()).append(")");
		
		if(this.parent != null) {
			buf.append("\n  parent: ").append(this.parent.getName());
			buf.append("(").append(this.parent.getClass().getName()).append(")");
		}

		if(!this.children.isEmpty()) {
			buf.append("\n  children:");
			for(ExecIF e: this.children)
				buf.append(" ").append(e.getName());
		}
		
		if(!this.data.isEmpty()) {
			buf.append("\n  data:");
			Iterator<DataIF> iter = this.data.values().iterator();
			while(iter.hasNext()) {
				DataIF data = iter.next();
				buf.append(" ").append(data.getName());
			}
		}
		
		return buf.toString();
	}
	
	public void dump() {
		this.mCtx.writeDebug(this.toString());
		
		for(ExecIF e: this.children)
			e.dump();		
	}
	
	public void dump(PrintStream out) {
		out.println(this);
		
		for(ExecIF e: this.children)
			e.dump(out);
	}
	
	public abstract void prepareInternal() throws DBTException, DataException;
	public abstract void cleanupInternal() throws DBTException;
}
