package luxk.jdbt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Iterator;

public class ExecBlockSQLSelect extends ExecBlock {
	
	private boolean ignorethrow = false;
	
	private String literalBind = null;
	private DataIF[] literalBinds = null;
	private MessageFormat form = null;

	private String bindsName = null;
	private DataIF[] binds = null;
	
	private String outsName = null;
	private DataIF[] outs = null;
	
	private String sql = null;
	private int fetchCount = 0;
	
	public ExecBlockSQLSelect(String name, ExecIF parent, MainContext mCtx) {
		super(name, parent, mCtx);
		this.name = name;
		this.parent = parent;
		this.mCtx = mCtx;
		
		this.result = new Result(this, name);
	}
	
	public ExecIF duplicate(ExecIF parent) throws DBTException, DataException {
		
		ExecBlockSQLSelect exec =
				new ExecBlockSQLSelect(this.name, parent, this.mCtx);
		
		exec.setLine(this.line);
		exec.setIgnorethrow(Boolean.toString(this.ignorethrow));
		exec.setText(this.sql);
		
		Iterator<DataIF> tmp = this.data.values().iterator();
		while(tmp.hasNext()) {
			DataIF data = tmp.next().duplicate();
			exec.addData(data);
		}

		// data에서 찾을 수도 있기 때문에 data 변수 먼저 복사하고 bind/out은 나중에 복사
		exec.setLiteralbind(this.literalBind);
		exec.setBind(this.bindsName);
		exec.setOut(this.outsName);

		for(ExecIF e: this.children) {
			ExecIF child = e.duplicate(exec);
			exec.addChild(child);
		}
		
		return exec;
	}

	@Override
	public void prepareInternal() throws DBTException {
		this.sCtx = getSessionContext();
		if(this.literalBinds != null)
			this.form = new MessageFormat(this.sql);
	}

	@Override
	public void cleanupInternal() throws DBTException {}

	@Override
	public void runInternal() throws DBTException {
		SessionContext ctx = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			ctx = getSessionContext();
			con = ctx.getConnection();
			
			this.result.begin();
			
			String sqlStr = null;
			if(this.form != null && this.literalBinds != null) {
				Object tmp[] = new Object[this.literalBinds.length];
				for(int i = 0; i < tmp.length; i++) {
					tmp[i] = this.literalBinds[i].getValue();
				}
				sqlStr = this.form.format(tmp);
			} else {
				sqlStr = this.sql;
			}
			
			if(this.mCtx.isDebug()) {
				this.mCtx.writeDebug(this.name + " exec sql: " + sqlStr);
			}
			
			pstmt = con.prepareStatement(sqlStr);

			for(int i = 0; binds != null && i < binds.length; i++)
				pstmt.setString(i+1, binds[i].getValue());
			rs = pstmt.executeQuery();
			
			this.fetchCount = 0;
			while(rs.next()) {
				for(int i = 0; outs != null && i < outs.length; i++)
					outs[i].setValue(rs.getString(i+1));
				
				this.fetchCount++;
				
				for(ExecIF e: this.children)
					e.run();
			}
			
			this.result.setSuccess();
			
		} catch(Exception e) {
			int errCode = -1;
			if(e instanceof SQLException)
				errCode = ((SQLException)e).getErrorCode();
			this.result.setFail(errCode, e.getMessage());
			if(con != null) try { con.rollback(); } catch(Exception ee) {}
			if(!this.ignorethrow)
				throw new DBTException("fail to sql_exec", e, this.line);
		} finally {
			if(rs != null) try { rs.close(); } catch(Exception ee) {}
			if(pstmt != null) try { pstmt.close(); } catch(Exception ee) {}
		}
	}

	public void setIgnorethrow(String val) {
		this.ignorethrow = UtilString.isPositiveOption(val);
	}
	
	public int getFetchCount() {
		return this.fetchCount;
	}
	
	public void setLiteralbind(String str) throws DBTException {
		this.literalBind = str;
		this.literalBinds = searchOrCreateData(str);
	}

	public void setBind(String str) throws DBTException {
		this.bindsName = str;
		this.binds = searchOrCreateData(str);
	}
	
	public void setOut(String str) throws DBTException {
		this.outsName = str;
		this.outs = searchOrCreateData(str);
	}

	public void setText(String str) {
		this.sql = str;
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
		
		if(this.binds != null) {
			buf.append("\n  binds:");
			for(DataIF e: this.binds)
				buf.append(" ").append(e.getName());
		}
		
		if(this.outs != null) {
			buf.append("\n  outs:");
			for(DataIF e: this.outs)
				buf.append(" ").append(e.getName());
		}
		
		return buf.toString();
	}
}
