package luxk.jdbt.mockjdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class MockDriver implements Driver {

	public boolean acceptsURL(String arg0) throws SQLException {
		//return "jdbc.luxk.mock.thin".equals(arg0);
		return true;
	}

	public Connection connect(String arg0, Properties arg1) throws SQLException {
		return new MockConnection();
	}

	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 1;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		return null;
	}

	public boolean jdbcCompliant() {
		return true;
	}

}
