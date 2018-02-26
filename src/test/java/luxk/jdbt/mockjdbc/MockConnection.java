package luxk.jdbt.mockjdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection {

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void abort(Executor arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Statement createStatement() throws SQLException {
		return new MockStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return createStatement();
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isClosed() throws SQLException {
		return false;
	}

	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValid(int timeout) throws SQLException {
		return true;
	}

	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return new MockCallableStatement();
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return prepareCall("");
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return prepareCall("");
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new MockPreparedStatement();
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return prepareStatement("");
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return prepareStatement("");
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return prepareStatement("");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return prepareStatement("");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return prepareStatement("");
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {}
	public void rollback() throws SQLException {}
	public void rollback(Savepoint savepoint) throws SQLException {}
	public void setAutoCommit(boolean autoCommit) throws SQLException {}
	public void setCatalog(String catalog) throws SQLException {}
	public void setClientInfo(Properties properties) throws SQLClientInfoException {}
	public void setClientInfo(String name, String value) throws SQLClientInfoException {}
	public void setHoldability(int holdability) throws SQLException {}
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {}
	public void setReadOnly(boolean readOnly) throws SQLException {}

	public Savepoint setSavepoint() throws SQLException {
		return null;
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return null;
	}

	public void setSchema(String schema) throws SQLException {}
	public void setTransactionIsolation(int level) throws SQLException {}
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {}

}
