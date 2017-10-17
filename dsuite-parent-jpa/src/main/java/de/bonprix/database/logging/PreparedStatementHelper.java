package de.bonprix.database.logging;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
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

/**
 * Wrapper class for a {@link PreparedStatement}. Remembers the statement
 * string, parameters and triggers the logging itself during execution.
 *
 * @author cthiel
 * @date 11.10.2016
 *
 */
public class PreparedStatementHelper implements PreparedStatement {

	private final PreparedStatement prepStmt;
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
	public PreparedStatementHelper(final PreparedStatement prepStmt, final String sql, final SqlLogger sqlLogger)
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
	public ResultSet executeQuery() throws SQLException {
		final long start = System.currentTimeMillis();
		final ResultSet result = this.prepStmt.executeQuery();
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
}
