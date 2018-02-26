package luxk.jdbt;

import java.sql.Connection;
import java.sql.SQLException;

public class ExecLineSQLCommit extends ExecLine {
	
	private boolean ignorethrow = false;
	
	public ExecLineSQLCommit(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}
	
	public ExecIF duplicate(ExecIF parent) throws DBTException {

		ExecLineSQLCommit exec =
				new ExecLineSQLCommit(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setIgnorethrow(Boolean.toString(this.ignorethrow));

		return exec;
	}

	@Override
	public void runInternal() throws DBTException {
		
		try {
			Connection con = this.sCtx.getConnection();
			this.result.begin();
			con.commit();
			this.result.setSuccess();
		} catch(SQLException e) {
			this.result.setFail(e.getErrorCode(), e.getMessage());
			if(!this.ignorethrow)
				throw new DBTException("fail to commit", e, this.line);
		}
	}
	
	public void setIgnorethrow(String val) {
		this.ignorethrow = UtilString.isPositiveOption(val);
	}
}
