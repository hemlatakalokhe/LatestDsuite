/**
 *
 */
package de.bonprix.database.logging;

import java.sql.CallableStatement;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author cthiel
 * @date 12.10.2016
 *
 */
public class JdbcLoggingInterceptorTest {

	private static final String DELECT_FROM_DUAL = "SELECT * FROM DUAL WHERE 1=1";

	private Appender mockAppender;

	@BeforeMethod
	public void setup() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(SqlLogger.LOGGER_NAME);
		loggerConfig.setLevel(Level.DEBUG);
		ctx.updateLoggers();

		// reset logger to debug for activate it for testing
		this.mockAppender = Mockito.mock(Appender.class);

		Mockito.reset(this.mockAppender);
		Mockito.when(this.mockAppender.getName())
			.thenReturn("MockAppender");
		Mockito.when(this.mockAppender.isStarted())
			.thenReturn(true);
		Mockito.when(this.mockAppender.isStopped())
			.thenReturn(false);

		loggerConfig.addAppender(this.mockAppender, Level.DEBUG, null);
	}

	@AfterMethod
	public void tearDown() {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(SqlLogger.LOGGER_NAME);
		loggerConfig.removeAppender("MockAppender");
	}

	@Test
	public void testInterceptPreparedStatement() throws SQLException {

		// setup test data
		final PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		final ParameterMetaData parameterMetaData = Mockito.mock(ParameterMetaData.class);
		Mockito.when(statement.getParameterMetaData())
			.thenReturn(parameterMetaData);
		Mockito.when(parameterMetaData.getParameterCount())
			.thenReturn(1);

		final JdbcLoggingInterceptor interceptor = new JdbcLoggingInterceptor();
		final PreparedStatement wrapped = (PreparedStatement) interceptor
			.createStatement(null, null, new Object[] { JdbcLoggingInterceptorTest.DELECT_FROM_DUAL }, statement, 1L);

		MatcherAssert.assertThat(wrapped, Matchers.instanceOf(PreparedStatementHelper.class));

		wrapped.executeQuery();

		verifyErrorMessages("SELECT * FROM DUAL WHERE 1=1NULL");
	}

	@Test
	public void testInterceptPreparedStatementNoLogger() throws SQLException {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		final LoggerConfig loggerConfig = config.getLoggerConfig(SqlLogger.LOGGER_NAME);
		loggerConfig.setLevel(Level.INFO);
		ctx.updateLoggers();

		// setup test data
		final PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		final ParameterMetaData parameterMetaData = Mockito.mock(ParameterMetaData.class);
		Mockito.when(statement.getParameterMetaData())
			.thenReturn(parameterMetaData);
		Mockito.when(parameterMetaData.getParameterCount())
			.thenReturn(1);

		final JdbcLoggingInterceptor interceptor = new JdbcLoggingInterceptor();
		final PreparedStatement wrapped = (PreparedStatement) interceptor
			.createStatement(null, null, new Object[] { JdbcLoggingInterceptorTest.DELECT_FROM_DUAL }, statement, 1L);

		MatcherAssert.assertThat(wrapped, Matchers.equalTo(statement));

		wrapped.executeQuery();

		verifyErrorMessages();
	}

	@Test
	public void testInterceptPreparedStatementWithParameters() throws SQLException {

		// setup test data
		final PreparedStatement statement = Mockito.mock(PreparedStatement.class);
		final ParameterMetaData parameterMetaData = Mockito.mock(ParameterMetaData.class);
		Mockito.when(statement.getParameterMetaData())
			.thenReturn(parameterMetaData);
		Mockito.when(parameterMetaData.getParameterCount())
			.thenReturn(2);

		final JdbcLoggingInterceptor interceptor = new JdbcLoggingInterceptor();
		final PreparedStatement wrapped = (PreparedStatement) interceptor
			.createStatement(null, null, new Object[] { "SELECT * FROM DUAL WHERE ? = ?" }, statement, 1L);
		wrapped.setInt(1, 1);
		wrapped.setString(2, "test");

		MatcherAssert.assertThat(wrapped, Matchers.instanceOf(PreparedStatementHelper.class));

		wrapped.executeQuery();

		verifyErrorMessages("SELECT * FROM DUAL WHERE 1 = 'test'");
	}

	@Test
	public void testInterceptCallableStatement() throws SQLException {

		// setup test data
		final CallableStatement statement = Mockito.mock(CallableStatement.class);
		final ParameterMetaData parameterMetaData = Mockito.mock(ParameterMetaData.class);
		Mockito.when(statement.getParameterMetaData())
			.thenReturn(parameterMetaData);
		Mockito.when(parameterMetaData.getParameterCount())
			.thenReturn(1);

		final JdbcLoggingInterceptor interceptor = new JdbcLoggingInterceptor();
		final CallableStatement wrapped = (CallableStatement) interceptor
			.createStatement(null, null, new Object[] { JdbcLoggingInterceptorTest.DELECT_FROM_DUAL }, statement, 1L);

		MatcherAssert.assertThat(wrapped, Matchers.instanceOf(CallableStatementHelper.class));

		wrapped.executeQuery();

		verifyErrorMessages("SELECT * FROM DUAL WHERE 1=1NULL");
	}

	@Test
	public void testInterceptCallableStatementWithParameters() throws SQLException {

		// setup test data
		final CallableStatement statement = Mockito.mock(CallableStatement.class);
		final ParameterMetaData parameterMetaData = Mockito.mock(ParameterMetaData.class);
		Mockito.when(statement.getParameterMetaData())
			.thenReturn(parameterMetaData);
		Mockito.when(parameterMetaData.getParameterCount())
			.thenReturn(2);

		final JdbcLoggingInterceptor interceptor = new JdbcLoggingInterceptor();
		final CallableStatement wrapped = (CallableStatement) interceptor
			.createStatement(null, null, new Object[] { "SELECT * FROM DUAL WHERE ? = ?" }, statement, 1L);
		wrapped.setInt(1, 1);
		wrapped.setString(2, "test");

		MatcherAssert.assertThat(wrapped, Matchers.instanceOf(CallableStatementHelper.class));

		wrapped.executeQuery();

		verifyErrorMessages("SELECT * FROM DUAL WHERE 1 = 'test'");
	}

	// handy function to inspect the messages sent to the logger
	private void verifyErrorMessages(final String... messages) {
		final ArgumentCaptor<LogEvent> captorLoggingEvent = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(this.mockAppender, Mockito.times(messages.length))
			.append(captorLoggingEvent.capture());

		int i = 0;
		for (final LogEvent loggingEvent : captorLoggingEvent.getAllValues()) {

			MatcherAssert.assertThat(loggingEvent.getMessage()
				.getFormattedMessage(), Matchers.endsWith(messages[i++]));
		}
	}

}
