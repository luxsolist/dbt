package luxk.jdbt;

import java.sql.Connection;

public interface ExternalClass {
	
	public void exec(Connection con, DataIF[] args) throws Exception;

}
