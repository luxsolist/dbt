package luxk.jdbt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

public class ExecBlockTransaction extends ExecBlock {
	
	private int weight = 1;
	
	private Connection con = null;
	
	public ExecBlockTransaction(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}

	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		
		ExecBlockTransaction exec =
				new ExecBlockTransaction(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setWeight(Integer.toString(this.weight));

		Iterator<DataIF> tmp = this.data.values().iterator();
		while(tmp.hasNext()) {
			DataIF data = tmp.next().duplicate();
			exec.addData(data);
		}
		
		for(ExecIF e: this.children) {
			ExecIF child = e.duplicate(exec);
			exec.addChild(child);
		}
		
		return exec;
	}

	@Override
	public void prepareInternal() throws DBTException {
		this.sCtx = getSessionContext();
		try {
			this.con = this.sCtx.getConnection();
		} catch(Exception e) {
			throw new DBTException(e);
		}
	}
	
	@Override
	public void runInternal() throws DBTException {

		try {
			this.result.begin();
			
			for(ExecIF e: this.children)
				e.run();
			
			this.result.setSuccess();
			
		} catch(DBTException e) {
			this.result.setFail(e);
			throw e;
		} finally {}
	}
	
	public void cleanupInternal() throws DBTException {

		try {
			// 아직 commit 안되었다면 commit 해준다
			this.con.commit();
		} catch(SQLException e) {
			// do nothing
		} finally {}
	}
	
	public void setWeight(String str) throws DBTException {
		try {
			this.weight = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			throw new DBTException(e);
		}
		
		if(this.weight < 1) {
			this.weight = 1;
		} else if(this.weight > 100) {
			this.weight = 100;
		}
	}
	
	public int getIntWeight() {
		return this.weight;
	}
	
	/* commit은 transaction 정의 block 내에서 명시적으로 부르도록 한다.
	public void setPercommit(String str) throws DBTException {
		try {
			this.percommit = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			throw new DBTException(e);
		}
		
		if(this.percommit < 0)
			this.percommit = 0;
	}
	*/

}
