/**
 *
 */
package de.bonprix.database.logging;

import java.sql.Date;
import java.sql.Timestamp;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

/**
 * @author cthiel
 * @date 12.10.2016
 *
 */
public class Log4j2SqlLoggerTest {

	@Test
	public void testGenerateSqlId() {
		final String sql1 = "SELECT 1 FROM DUAL WHERE 1=1";
		final String sql2 = "select 1 FROM DUAL WHERE 1=1";

		final String hash1 = Log4j2SqlLogger.generateSqlHash(sql1);
		final String hash2 = Log4j2SqlLogger.generateSqlHash(sql2);

		MatcherAssert.assertThat("9a7845400d36cb77b5722ef33983d627", Matchers.equalTo(hash1));
		MatcherAssert.assertThat("f7139642ff44366d279d4aa034be07b6", Matchers.equalTo(hash2));
		MatcherAssert.assertThat(hash1, Matchers.not(Matchers.equalTo(hash2)));
	}

	@Test
	public void testGenerateActualSql() {
		Log4j2SqlLogger logger = new Log4j2SqlLogger();
		final String sql = "SELECT * FROM some_table t WHERE t.id = ? AND t.name LIKE ? AND t.date > ? and t.boolean = ? and t.boolean = ? and t.timestamp = ? and t.null = ?";
		final Object[] parameters = new Object[] { 1L, "some name", new Date(1234567890000L), false, true,
				new Timestamp(987654321000L), null };

		final String actualSql = logger.generateActualSql(sql, parameters);

		MatcherAssert.assertThat(actualSql, Matchers
			.equalTo("SELECT * FROM some_table t WHERE t.id = 1 AND t.name LIKE 'some name' AND t.date > to_date('02/13/2009 23:31:30.000', 'mm/dd/yyyy hh24:mi:ss') and t.boolean = 0 and t.boolean = 1 and t.timestamp = to_timestamp('04/19/2001 04:25:21.000', 'mm/dd/yyyy hh24:mi:ss.ff3') and t.null = NULL"));
	}

	@Test
	public void testGenerateActualSqlNoParameter() {
		Log4j2SqlLogger logger = new Log4j2SqlLogger();
		final String sql = "SELECT * FROM some_table t WHERE 1 = 1";
		final Object[] parameters = new Object[] {};

		final String actualSql = logger.generateActualSql(sql, parameters);

		MatcherAssert.assertThat(actualSql, Matchers.equalTo("SELECT * FROM some_table t WHERE 1 = 1"));
	}

}
