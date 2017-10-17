/**
 *
 */
package de.bonprix.database.logging;

/**
 * @author cthiel
 * @date 12.10.2016
 *
 */
@FunctionalInterface
public interface SqlLogger {

	public static final String LOGGER_NAME = "sql";

	/**
	 * Performs the logging to log4j.
	 *
	 * @param theSql
	 *            the actual SQL string
	 * @param parameters
	 *            list of parameters
	 * @param time
	 *            spent execution time
	 */
	void logSql(final String theSql, final Object[] parameters, final long time);

}
