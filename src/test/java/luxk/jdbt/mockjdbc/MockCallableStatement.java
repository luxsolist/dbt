package luxk.jdbt.mockjdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class MockCallableStatement implements CallableStatement {

	public void addBatch() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void clearParameters() throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean execute() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet executeQuery() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int executeUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setArray(int parameterIndex, Array x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(int parameterIndex, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNString(int parameterIndex, String value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setRef(int parameterIndex, Ref x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setString(int parameterIndex, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void cancel() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int executeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCursorName(String name) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setMaxFieldSize(int max) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setMaxRows(int max) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setQueryTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getBoolean(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getBoolean(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public double getDouble(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDouble(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getInt(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLong(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLong(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Ref getRef(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML getSQLXML(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public short getShort(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getShort(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getString(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(int parameterIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL(String parameterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(String parameterName, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBoolean(String parameterName, boolean x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setByte(String parameterName, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setBytes(String parameterName, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(String parameterName, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(String parameterName, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setClob(String parameterName, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDate(String parameterName, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setDouble(String parameterName, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setFloat(String parameterName, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setInt(String parameterName, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLong(String parameterName, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(String parameterName, NClob value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(String parameterName, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNString(String parameterName, String value) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNull(String parameterName, int sqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(String parameterName, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setRowId(String parameterName, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setShort(String parameterName, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setString(String parameterName, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTime(String parameterName, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setURL(String parameterName, URL val) throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
