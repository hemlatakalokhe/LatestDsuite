/**
 *
 */
package de.bonprix.database.logging;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import de.bonprix.exception.GenerateSqlHashException;

/**
 * @author cthiel
 * @date 12.10.2016
 *
 */
public class Log4j2SqlLogger implements SqlLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlLogger.LOGGER_NAME);

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");

	public Log4j2SqlLogger() {
		this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public void logSql(final String theSql, final Object[] parameters, final long time) {

		final String sql = theSql.replaceAll("\\s+", " ");
		final String sqlHash = Log4j2SqlLogger.generateSqlHash(sql);
		final String sqlParameters = Joiner.on(", ")
			.useForNull("NULL")
			.join(parameters);
		final String sqlRuntime = Long.toString(time);
		final String sqlWithParameters = this.generateActualSql(sql, parameters);

		ThreadContext.put("sql", sql);
		ThreadContext.put("sqlHash", sqlHash);
		ThreadContext.put("sqlRuntime", sqlRuntime);
		ThreadContext.put("sqlParameters", sqlParameters);
		ThreadContext.put("sqlWithParameters", sqlWithParameters);

		final Object jpaId = ThreadContext.get("jpaRepository");

		Logger logger = Log4j2SqlLogger.LOGGER;

		if (jpaId != null) {
			logger = LoggerFactory.getLogger("sql." + jpaId.toString());
		}
		logger.debug("SQL (" + sqlRuntime + "ms): " + sqlWithParameters);

		ThreadContext.remove("sql");
		ThreadContext.remove("sqlHash");
		ThreadContext.remove("sqlRuntime");
		ThreadContext.remove("sqlParameters");
		ThreadContext.remove("sqlWithParameters");
	}

	/**
	 * Generate an MD5 hash of the SQL.
	 *
	 * @param sql
	 *            the sql
	 * @return the hash
	 */
	static String generateSqlHash(final String sql) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			throw new GenerateSqlHashException(e);
		}

		return Hex.encodeHexString(digest.digest(sql.getBytes()));

	}

	/**
	 * Replaces all placeholders of the SQL with actual parameters. The match of
	 * replacing is the order of placeholders and parameters.
	 *
	 * @param sqlQuery
	 *            the sql
	 * @param parameters
	 *            the array of parameters
	 * @return the sql string with parameters
	 */
	String generateActualSql(final String sqlQuery, final Object... parameters) {
		final String[] parts = sqlQuery.split("\\?");
		final StringBuilder sb = new StringBuilder();

		// This might be wrong if some '?' are used as litteral '?'
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			sb.append(part);
			if (i < parameters.length) {
				sb.append(this.formatParameter(parameters[i]));
			}
		}

		return sb.toString();
	}

	/**
	 * Format the parameter
	 *
	 * @param parameter
	 * @return
	 */
	String formatParameter(final Object parameter) {
		if (parameter == null) {
			return "NULL";
		}

		if (parameter instanceof String) {
			return "'" + ((String) parameter).replace("'", "''") + "'";
		}
		if (parameter instanceof Timestamp) {
			return "to_timestamp('" + this.dateFormat.format(parameter) + "', 'mm/dd/yyyy hh24:mi:ss.ff3')";
		}
		if (parameter instanceof Date) {
			return "to_date('" + this.dateFormat.format(parameter) + "', 'mm/dd/yyyy hh24:mi:ss')";
		}
		if (parameter instanceof Boolean) {
			return ((Boolean) parameter).booleanValue() ? "1" : "0";
		}

		return parameter.toString();
	}

}
