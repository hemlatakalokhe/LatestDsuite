package de.bonprix.database.logging;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.tomcat.jdbc.pool.interceptor.AbstractCreateStatementInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.exception.CreateStatementException;

/**
 * The JDBC-logging interceptor intercepts each and every JDBC statement and
 * tries to log the statement with parameters to the logger. It also measures
 * the time, the execution took.
 *
 * @author cthiel
 * @date 11.10.2016
 *
 */
public class JdbcLoggingInterceptor extends AbstractCreateStatementInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlLogger.LOGGER_NAME);

	private final SqlLogger sqlLogger = new Log4j2SqlLogger();

	@Override
	public void reset(final ConnectionPool parent, final PooledConnection con) {
		// empty
	}

	@Override
	public Object createStatement(final Object proxy, final Method method, final Object[] args, final Object statement,
			final long time) {
		if (!JdbcLoggingInterceptor.LOGGER.isDebugEnabled()) {
			return statement;
		}

		String sql = null;
		if (args != null && args.length > 0 && args[0] instanceof String) {
			sql = (String) args[0];
		}

		try {
			if (statement instanceof CallableStatement) {
				return new CallableStatementHelper((CallableStatement) statement, sql, this.sqlLogger);
			} else if (statement instanceof PreparedStatement) {
				return new PreparedStatementHelper((PreparedStatement) statement, sql, this.sqlLogger);
			} else {
				return statement;
			}
		} catch (final SQLException e) {
			throw new CreateStatementException(e);
		}
	}

	@Override
	public void closeInvoked() {
		// empty
	}

}
