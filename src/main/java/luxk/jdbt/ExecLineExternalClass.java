package luxk.jdbt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExecLineExternalClass extends ExecLine {
	
	private ExternalClass extClass = null;
	
	private String argsStr = null;
	private DataIF[] args = null;
	
	public ExecLineExternalClass(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		
		ExecLineExternalClass exec =
				new ExecLineExternalClass(this.name, this.parent, this.mCtx);
		exec.setClass(this.extClass.getClass().getName());
		exec.setArgs(this.argsStr);
		return exec;
	}
	
	@Override
	public void runInternal() throws DBTException {
		
		Connection con = null;
		
		try {
			con = this.sCtx.getConnection();
			this.extClass.exec(con, this.args);
		} catch(Exception e) {
			int errCode = -1;
			if(e instanceof SQLException)
				errCode = ((SQLException)e).getErrorCode();
			this.result.setFail(errCode, e.getMessage());
			if(con != null) try { con.rollback(); } catch(Exception ee) {}
			throw new DBTException("fail to exec external_class", e, this.line);
		}
	}
	
	public void setClass(String val) throws DBTException {
		
		Object obj = null;
		
		try {
			obj = Class.forName(val).newInstance();
		} catch(Exception e) {
			new DBTException(e);
		}
		
		if(!(obj instanceof ExternalClass))
			new DBTException("Class " + obj.getClass().getName() + 
					" must implements " + ExternalClass.class.getName());
		
		this.extClass = (ExternalClass)obj;
	}

	public void setArgs(String val) throws DBTException {
		this.argsStr = val;
		this.args = searchOrCreateData(val);
	}
	
	private DataIF[] searchOrCreateData(String nameList) throws DBTException {
		
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
			if(data == null)
				throw new DBTException("Cannot find data " + name);

			list.add(data);
		}
		
		DataIF[] ret = new DataIF[list.size()];
		return list.toArray(ret);
	}
}
