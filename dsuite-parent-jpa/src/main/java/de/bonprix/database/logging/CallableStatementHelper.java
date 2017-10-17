package de.bonprix.database.logging;

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

/**
 * Wrapper class for a {@link CallableStatement}. Remembers the statement
 * string, parameters and triggers the logging itself during execution.
 *
 * @author cthiel
 * @date 11.10.2016
 *
 */
public class CallableStatementHelper implements CallableStatement {

	private final CallableStatement prepStmt;
	private final String sql;
	private final Object[] values;

	private final SqlLogger sqlLogger;

	/**
	 * Create the wrapper.
	 *
	 * @param prepStmt
	 *            the wrapped statement
	 * @param sql
	 *            the sql
	 * @throws SQLException
	 */
	public CallableStatementHelper(final CallableStatement prepStmt, final String sql, final SqlLogger sqlLogger)
			throws SQLException {
		this.prepStmt = prepStmt;
		this.sql = sql;
		this.sqlLogger = sqlLogger;
		this.values = new Object[this.prepStmt.getParameterMetaData()
			.getParameterCount()];
	}

	/**
	 * Returns a parameter.
	 *
	 * @param index
	 *            the index of the parameter
	 * @return the parameter
	 */
	public Object getParameter(final int index) {
		return this.values[index - 1];
	}

	/**
	 * Sets the parameter internally and formats it directly.
	 *
	 * @param index
	 *            the index
	 * @param value
	 *            the parameter value
	 */
	private void setParameter(final int index, final Object value) {
		this.values[index - 1] = value;
	}

	@Override
	public boolean execute() throws SQLException {
		final long start = System.currentTimeMillis();
		final boolean result = this.prepStmt.execute();
		final long end = System.currentTimeMillis() - start;

		this.sqlLogger.logSql(this.sql, this.values, end);

		return result;
	}

	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {
		return this.prepStmt.executeQuery(sql);
	}

	@Override
	public int executeUpdate(final String sql) throws SQLException {
		return this.prepStmt.executeUpdate(sql);
	}

	@Override
	public void close() throws SQLException {
		this.prepStmt.close();

	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return this.prepStmt.getMaxFieldSize();
	}

	@Override
	public void setMaxFieldSize(final int max) throws SQLException {
		this.prepStmt.setMaxFieldSize(max);
	}

	@Override
	public int getMaxRows() throws SQLException {
		return this.prepStmt.getMaxRows();
	}

	@Override
	public void setMaxRows(final int max) throws SQLException {
		this.prepStmt.setMaxRows(max);
	}

	@Override
	public void setEscapeProcessing(final boolean enable) throws SQLException {
		this.prepStmt.setEscapeProcessing(enable);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return this.prepStmt.getQueryTimeout();
	}

	@Override
	public void setQueryTimeout(final int seconds) throws SQLException {
		this.prepStmt.setQueryTimeout(seconds);
	}

	@Override
	public void cancel() throws SQLException {
		this.prepStmt.cancel();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.prepStmt.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		this.prepStmt.clearWarnings();
	}

	@Override
	public void setCursorName(final String name) throws SQLException {
		this.prepStmt.setCursorName(name);
	}

	@Override
	public boolean execute(final String sql) throws SQLException {
		return this.prepStmt.execute(sql);
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return this.prepStmt.getResultSet();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return this.prepStmt.getUpdateCount();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return this.prepStmt.getMoreResults();
	}

	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		this.prepStmt.setFetchDirection(direction);
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return this.prepStmt.getFetchDirection();
	}

	@Override
	public void setFetchSize(final int rows) throws SQLException {
		this.prepStmt.setFetchSize(rows);
	}

	@Override
	public int getFetchSize() throws SQLException {
		return this.prepStmt.getFetchSize();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return this.prepStmt.getResultSetConcurrency();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return this.prepStmt.getResultSetType();
	}

	@Override
	public void addBatch(final String sql) throws SQLException {
		this.prepStmt.addBatch(sql);
	}

	@Override
	public void clearBatch() throws SQLException {
		this.prepStmt.clearBatch();
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return this.prepStmt.executeBatch();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.prepStmt.getConnection();
	}

	@Override
	public boolean getMoreResults(final int current) throws SQLException {
		return this.prepStmt.getMoreResults(current);
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return this.prepStmt.getGeneratedKeys();
	}

	@Override
	public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
		return this.prepStmt.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
		return this.prepStmt.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
		return this.prepStmt.executeUpdate(sql, columnNames);
	}

	@Override
	public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
		return this.prepStmt.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
		return this.prepStmt.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(final String sql, final String[] columnNames) throws SQLException {
		return this.prepStmt.execute(sql, columnNames);
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return this.prepStmt.getResultSetHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.prepStmt.isClosed();
	}

	@Override
	public void setPoolable(final boolean poolable) throws SQLException {
		this.prepStmt.setPoolable(poolable);
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return this.prepStmt.isPoolable();
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return this.prepStmt.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return this.prepStmt.isWrapperFor(iface);
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		final long start = System.currentTimeMillis();
		final ResultSet result = this.prepStmt.executeQuery();
		final long end = System.currentTimeMillis() - start;

		this.sqlLogger.logSql(this.sql, this.values, end);

		return result;
	}

	@Override
	public int executeUpdate() throws SQLException {
		return this.prepStmt.executeUpdate();
	}

	@Override
	public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
		this.prepStmt.setNull(parameterIndex, sqlType);
		setParameter(parameterIndex, null);
	}

	@Override
	public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
		this.prepStmt.setBoolean(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setByte(final int parameterIndex, final byte x) throws SQLException {
		this.prepStmt.setByte(parameterIndex, x);
	}

	@Override
	public void setShort(final int parameterIndex, final short x) throws SQLException {
		this.prepStmt.setShort(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setInt(final int parameterIndex, final int x) throws SQLException {
		this.prepStmt.setInt(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setLong(final int parameterIndex, final long x) throws SQLException {
		this.prepStmt.setLong(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setFloat(final int parameterIndex, final float x) throws SQLException {
		this.prepStmt.setFloat(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setDouble(final int parameterIndex, final double x) throws SQLException {
		this.prepStmt.setDouble(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
		this.prepStmt.setBigDecimal(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setString(final int parameterIndex, final String x) throws SQLException {
		this.prepStmt.setString(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
		this.prepStmt.setBytes(parameterIndex, x);
	}

	@Override
	public void setDate(final int parameterIndex, final Date x) throws SQLException {
		this.prepStmt.setDate(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setTime(final int parameterIndex, final Time x) throws SQLException {
		this.prepStmt.setTime(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
		this.prepStmt.setTimestamp(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.prepStmt.setAsciiStream(parameterIndex, x, length);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.prepStmt.setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		this.prepStmt.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void clearParameters() throws SQLException {
		this.prepStmt.clearParameters();
	}

	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
		this.prepStmt.setObject(parameterIndex, x, targetSqlType);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setObject(final int parameterIndex, final Object x) throws SQLException {
		this.prepStmt.setObject(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void addBatch() throws SQLException {
		this.prepStmt.addBatch();
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final int length)
			throws SQLException {
		this.prepStmt.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setRef(final int parameterIndex, final Ref x) throws SQLException {
		this.prepStmt.setRef(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
		this.prepStmt.setBlob(parameterIndex, x);
	}

	@Override
	public void setClob(final int parameterIndex, final Clob x) throws SQLException {
		this.prepStmt.setClob(parameterIndex, x);
	}

	@Override
	public void setArray(final int parameterIndex, final Array x) throws SQLException {
		this.prepStmt.setArray(parameterIndex, x);
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.prepStmt.getMetaData();
	}

	@Override
	public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
		this.prepStmt.setDate(parameterIndex, x, cal);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
		this.prepStmt.setTime(parameterIndex, x, cal);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
		this.prepStmt.setTimestamp(parameterIndex, x, cal);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
		this.prepStmt.setNull(parameterIndex, sqlType, typeName);
		setParameter(parameterIndex, null);
	}

	@Override
	public void setURL(final int parameterIndex, final URL x) throws SQLException {
		this.prepStmt.setURL(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.prepStmt.getParameterMetaData();
	}

	@Override
	public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
		this.prepStmt.setRowId(parameterIndex, x);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setNString(final int parameterIndex, final String value) throws SQLException {
		this.prepStmt.setNString(parameterIndex, value);
		setParameter(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value, final long length)
			throws SQLException {
		this.prepStmt.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
		this.prepStmt.setNClob(parameterIndex, value);
	}

	@Override
	public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.prepStmt.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream, final long length)
			throws SQLException {
		this.prepStmt.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		this.prepStmt.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
		this.prepStmt.setSQLXML(parameterIndex, xmlObject);
		setParameter(parameterIndex, xmlObject);
	}

	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength)
			throws SQLException {
		this.prepStmt.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		setParameter(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.prepStmt.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		this.prepStmt.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final long length)
			throws SQLException {
		this.prepStmt.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.prepStmt.setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
		this.prepStmt.setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
		this.prepStmt.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
		this.prepStmt.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.prepStmt.setClob(parameterIndex, reader);
	}

	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
		this.prepStmt.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
		this.prepStmt.setNClob(parameterIndex, reader);
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		this.prepStmt.closeOnCompletion();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return this.prepStmt.isCloseOnCompletion();
	}

	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
		this.prepStmt.registerOutParameter(parameterIndex, sqlType);
	}

	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
		this.prepStmt.registerOutParameter(parameterIndex, sqlType, scale);
	}

	@Override
	public boolean wasNull() throws SQLException {
		return this.prepStmt.wasNull();
	}

	@Override
	public String getString(final int parameterIndex) throws SQLException {
		return this.prepStmt.getString(parameterIndex);
	}

	@Override
	public boolean getBoolean(final int parameterIndex) throws SQLException {
		return this.prepStmt.getBoolean(parameterIndex);
	}

	@Override
	public byte getByte(final int parameterIndex) throws SQLException {
		return this.prepStmt.getByte(parameterIndex);
	}

	@Override
	public short getShort(final int parameterIndex) throws SQLException {
		return this.prepStmt.getShort(parameterIndex);
	}

	@Override
	public int getInt(final int parameterIndex) throws SQLException {
		return this.prepStmt.getInt(parameterIndex);
	}

	@Override
	public long getLong(final int parameterIndex) throws SQLException {
		return this.prepStmt.getLong(parameterIndex);
	}

	@Override
	public float getFloat(final int parameterIndex) throws SQLException {
		return this.prepStmt.getFloat(parameterIndex);
	}

	@Override
	public double getDouble(final int parameterIndex) throws SQLException {
		return this.prepStmt.getDouble(parameterIndex);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
		return this.prepStmt.getBigDecimal(parameterIndex, scale);
	}

	@Override
	public byte[] getBytes(final int parameterIndex) throws SQLException {
		return this.prepStmt.getBytes(parameterIndex);
	}

	@Override
	public Date getDate(final int parameterIndex) throws SQLException {
		return this.prepStmt.getDate(parameterIndex);
	}

	@Override
	public Time getTime(final int parameterIndex) throws SQLException {
		return this.prepStmt.getTime(parameterIndex);
	}

	@Override
	public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
		return this.prepStmt.getTimestamp(parameterIndex);
	}

	@Override
	public Object getObject(final int parameterIndex) throws SQLException {
		return this.prepStmt.getObject(parameterIndex);
	}

	@Override
	public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
		return this.prepStmt.getBigDecimal(parameterIndex);
	}

	@Override
	public Object getObject(final int parameterIndex, final Map<String, Class<?>> map) throws SQLException {
		return this.prepStmt.getObject(parameterIndex, map);
	}

	@Override
	public Ref getRef(final int parameterIndex) throws SQLException {
		return this.prepStmt.getRef(parameterIndex);
	}

	@Override
	public Blob getBlob(final int parameterIndex) throws SQLException {
		return this.prepStmt.getBlob(parameterIndex);
	}

	@Override
	public Clob getClob(final int parameterIndex) throws SQLException {
		return this.prepStmt.getClob(parameterIndex);
	}

	@Override
	public Array getArray(final int parameterIndex) throws SQLException {
		return this.prepStmt.getArray(parameterIndex);
	}

	@Override
	public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
		return this.prepStmt.getDate(parameterIndex, cal);
	}

	@Override
	public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
		return this.prepStmt.getTime(parameterIndex, cal);
	}

	@Override
	public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
		return this.prepStmt.getTimestamp(parameterIndex, cal);
	}

	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName)
			throws SQLException {
		this.prepStmt.registerOutParameter(parameterIndex, sqlType, typeName);
	}

	@Override
	public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
		this.prepStmt.registerOutParameter(parameterName, sqlType);
	}

	@Override
	public void registerOutParameter(final String parameterName, final int sqlType, final int scale)
			throws SQLException {
		this.prepStmt.registerOutParameter(parameterName, sqlType, scale);
	}

	@Override
	public void registerOutParameter(final String parameterName, final int sqlType, final String typeName)
			throws SQLException {
		this.prepStmt.registerOutParameter(parameterName, sqlType, typeName);
	}

	@Override
	public URL getURL(final int parameterIndex) throws SQLException {
		return this.prepStmt.getURL(parameterIndex);
	}

	@Override
	public void setURL(final String parameterName, final URL val) throws SQLException {
		this.prepStmt.setURL(parameterName, val);
	}

	@Override
	public void setNull(final String parameterName, final int sqlType) throws SQLException {
		this.prepStmt.setNull(parameterName, sqlType);
	}

	@Override
	public void setBoolean(final String parameterName, final boolean x) throws SQLException {
		this.prepStmt.setBoolean(parameterName, x);
	}

	@Override
	public void setByte(final String parameterName, final byte x) throws SQLException {
		this.prepStmt.setByte(parameterName, x);
	}

	@Override
	public void setShort(final String parameterName, final short x) throws SQLException {
		this.prepStmt.setShort(parameterName, x);
	}

	@Override
	public void setInt(final String parameterName, final int x) throws SQLException {
		this.prepStmt.setInt(parameterName, x);
	}

	@Override
	public void setLong(final String parameterName, final long x) throws SQLException {
		this.prepStmt.setLong(parameterName, x);
	}

	@Override
	public void setFloat(final String parameterName, final float x) throws SQLException {
		this.prepStmt.setFloat(parameterName, x);
	}

	@Override
	public void setDouble(final String parameterName, final double x) throws SQLException {
		this.prepStmt.setDouble(parameterName, x);
	}

	@Override
	public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
		this.prepStmt.setBigDecimal(parameterName, x);
	}

	@Override
	public void setString(final String parameterName, final String x) throws SQLException {
		this.prepStmt.setString(parameterName, x);
	}

	@Override
	public void setBytes(final String parameterName, final byte[] x) throws SQLException {
		this.prepStmt.setBytes(parameterName, x);
	}

	@Override
	public void setDate(final String parameterName, final Date x) throws SQLException {
		this.prepStmt.setDate(parameterName, x);
	}

	@Override
	public void setTime(final String parameterName, final Time x) throws SQLException {
		this.prepStmt.setTime(parameterName, x);
	}

	@Override
	public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
		this.prepStmt.setTimestamp(parameterName, x);
	}

	@Override
	public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		this.prepStmt.setAsciiStream(parameterName, x, length);
	}

	@Override
	public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		this.prepStmt.setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale)
			throws SQLException {
		this.prepStmt.setObject(parameterName, x, targetSqlType, scale);
	}

	@Override
	public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
		this.prepStmt.setObject(parameterName, x, targetSqlType);
	}

	@Override
	public void setObject(final String parameterName, final Object x) throws SQLException {
		this.prepStmt.setObject(parameterName, x);
	}

	@Override
	public void setCharacterStream(final String parameterName, final Reader reader, final int length)
			throws SQLException {
		this.prepStmt.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
		this.prepStmt.setDate(parameterName, x, cal);
	}

	@Override
	public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
		this.prepStmt.setTime(parameterName, x, cal);
	}

	@Override
	public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
		this.prepStmt.setTimestamp(parameterName, x, cal);
	}

	@Override
	public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
		this.prepStmt.setNull(parameterName, sqlType, typeName);
	}

	@Override
	public String getString(final String parameterName) throws SQLException {
		return this.prepStmt.getString(parameterName);
	}

	@Override
	public boolean getBoolean(final String parameterName) throws SQLException {
		return this.prepStmt.getBoolean(parameterName);
	}

	@Override
	public byte getByte(final String parameterName) throws SQLException {
		return this.prepStmt.getByte(parameterName);
	}

	@Override
	public short getShort(final String parameterName) throws SQLException {
		return this.prepStmt.getShort(parameterName);
	}

	@Override
	public int getInt(final String parameterName) throws SQLException {
		return this.prepStmt.getInt(parameterName);
	}

	@Override
	public long getLong(final String parameterName) throws SQLException {
		return this.prepStmt.getLong(parameterName);
	}

	@Override
	public float getFloat(final String parameterName) throws SQLException {
		return this.prepStmt.getFloat(parameterName);
	}

	@Override
	public double getDouble(final String parameterName) throws SQLException {
		return this.prepStmt.getDouble(parameterName);
	}

	@Override
	public byte[] getBytes(final String parameterName) throws SQLException {
		return this.prepStmt.getBytes(parameterName);
	}

	@Override
	public Date getDate(final String parameterName) throws SQLException {
		return this.prepStmt.getDate(parameterName);
	}

	@Override
	public Time getTime(final String parameterName) throws SQLException {
		return this.prepStmt.getTime(parameterName);
	}

	@Override
	public Timestamp getTimestamp(final String parameterName) throws SQLException {
		return this.prepStmt.getTimestamp(parameterName);
	}

	@Override
	public Object getObject(final String parameterName) throws SQLException {
		return this.prepStmt.getObject(parameterName);
	}

	@Override
	public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
		return this.prepStmt.getBigDecimal(parameterName);
	}

	@Override
	public Object getObject(final String parameterName, final Map<String, Class<?>> map) throws SQLException {
		return this.prepStmt.getObject(parameterName, map);
	}

	@Override
	public Ref getRef(final String parameterName) throws SQLException {
		return this.prepStmt.getRef(parameterName);
	}

	@Override
	public Blob getBlob(final String parameterName) throws SQLException {
		return this.prepStmt.getBlob(parameterName);
	}

	@Override
	public Clob getClob(final String parameterName) throws SQLException {
		return this.prepStmt.getClob(parameterName);
	}

	@Override
	public Array getArray(final String parameterName) throws SQLException {
		return this.prepStmt.getArray(parameterName);
	}

	@Override
	public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
		return this.prepStmt.getDate(parameterName, cal);
	}

	@Override
	public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
		return this.prepStmt.getTime(parameterName, cal);
	}

	@Override
	public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
		return this.prepStmt.getTimestamp(parameterName, cal);
	}

	@Override
	public URL getURL(final String parameterName) throws SQLException {
		return this.prepStmt.getURL(parameterName);
	}

	@Override
	public RowId getRowId(final int parameterIndex) throws SQLException {
		return this.prepStmt.getRowId(parameterIndex);
	}

	@Override
	public RowId getRowId(final String parameterName) throws SQLException {
		return this.prepStmt.getRowId(parameterName);
	}

	@Override
	public void setRowId(final String parameterName, final RowId x) throws SQLException {
		this.prepStmt.setRowId(parameterName, x);
	}

	@Override
	public void setNString(final String parameterName, final String value) throws SQLException {
		this.prepStmt.setNString(parameterName, value);
	}

	@Override
	public void setNCharacterStream(final String parameterName, final Reader value, final long length)
			throws SQLException {
		this.prepStmt.setNCharacterStream(parameterName, value, length);
	}

	@Override
	public void setNClob(final String parameterName, final NClob value) throws SQLException {
		this.prepStmt.setNClob(parameterName, value);
	}

	@Override
	public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		this.prepStmt.setClob(parameterName, reader, length);
	}

	@Override
	public void setBlob(final String parameterName, final InputStream inputStream, final long length)
			throws SQLException {
		this.prepStmt.setBlob(parameterName, inputStream, length);
	}

	@Override
	public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		this.prepStmt.setNClob(parameterName, reader, length);
	}

	@Override
	public NClob getNClob(final int parameterIndex) throws SQLException {
		return this.prepStmt.getNClob(parameterIndex);
	}

	@Override
	public NClob getNClob(final String parameterName) throws SQLException {
		return this.prepStmt.getNClob(parameterName);
	}

	@Override
	public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
		this.prepStmt.setSQLXML(parameterName, xmlObject);
	}

	@Override
	public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
		return this.prepStmt.getSQLXML(parameterIndex);
	}

	@Override
	public SQLXML getSQLXML(final String parameterName) throws SQLException {
		return this.prepStmt.getSQLXML(parameterName);
	}

	@Override
	public String getNString(final int parameterIndex) throws SQLException {
		return this.prepStmt.getNString(parameterIndex);
	}

	@Override
	public String getNString(final String parameterName) throws SQLException {
		return this.prepStmt.getNString(parameterName);
	}

	@Override
	public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
		return this.prepStmt.getNCharacterStream(parameterIndex);
	}

	@Override
	public Reader getNCharacterStream(final String parameterName) throws SQLException {
		return this.prepStmt.getNCharacterStream(parameterName);
	}

	@Override
	public Reader getCharacterStream(final int parameterIndex) throws SQLException {
		return this.prepStmt.getCharacterStream(parameterIndex);
	}

	@Override
	public Reader getCharacterStream(final String parameterName) throws SQLException {
		return this.prepStmt.getCharacterStream(parameterName);
	}

	@Override
	public void setBlob(final String parameterName, final Blob x) throws SQLException {
		this.prepStmt.setBlob(parameterName, x);
	}

	@Override
	public void setClob(final String parameterName, final Clob x) throws SQLException {
		this.prepStmt.setClob(parameterName, x);
	}

	@Override
	public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
		this.prepStmt.setAsciiStream(parameterName, x, length);
	}

	@Override
	public void setBinaryStream(final String parameterName, final InputStream x, final long length)
			throws SQLException {
		this.prepStmt.setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setCharacterStream(final String parameterName, final Reader reader, final long length)
			throws SQLException {
		this.prepStmt.setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
		this.prepStmt.setAsciiStream(parameterName, x);
	}

	@Override
	public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
		this.prepStmt.setBinaryStream(parameterName, x);
	}

	@Override
	public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
		this.prepStmt.setCharacterStream(parameterName, reader);
	}

	@Override
	public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
		this.prepStmt.setNCharacterStream(parameterName, value);
	}

	@Override
	public void setClob(final String parameterName, final Reader reader) throws SQLException {
		this.prepStmt.setClob(parameterName, reader);
	}

	@Override
	public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
		this.prepStmt.setBlob(parameterName, inputStream);
	}

	@Override
	public void setNClob(final String parameterName, final Reader reader) throws SQLException {
		this.prepStmt.setNClob(parameterName, reader);
	}

	@Override
	public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
		return this.prepStmt.getObject(parameterIndex, type);
	}

	@Override
	public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
		return this.prepStmt.getObject(parameterName, type);
	}
}
