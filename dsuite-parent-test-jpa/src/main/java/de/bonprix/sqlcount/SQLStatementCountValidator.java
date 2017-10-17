package de.bonprix.sqlcount;

import de.bonprix.sqlcount.exception.SQLDeleteCountMismatchException;
import de.bonprix.sqlcount.exception.SQLInsertCountMismatchException;
import de.bonprix.sqlcount.exception.SQLSelectCountMismatchException;
import de.bonprix.sqlcount.exception.SQLUpdateCountMismatchException;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

/**
 * SQLStatementCountValidator - Validates recorded statements count.
 *
 * First you execute some operations against your database and then you check
 * how many statements were executed. This is a useful tool against the "N+1"
 * problem or suboptimal DML statements.
 *
 * @author Vlad Mihalcea
 */
public class SQLStatementCountValidator {

	private SQLStatementCountValidator() {
	}

	/**
	 * Reset the statement recorder
	 */
	public static void reset() {
		QueryCountHolder.clear();
	}

	/**
	 * Assert select, insert, update and delete statement count
	 * 
	 * @param expectedSelectCount
	 *            expected select statement count
	 * @param expectedInsertCount
	 *            expected insert statement count
	 * @param expectedUpdateCount
	 *            expected update statement count
	 * @param expectedDeleteCount
	 *            expected delete statement count
	 */
	public static void assertSelectInsertUpdateDeleteCount(int expectedSelectCount, int expectedInsertCount, int expectedUpdateCount,
			int expectedDeleteCount) {
		assertSelectCount(expectedSelectCount);
		assertInsertCount(expectedInsertCount);
		assertUpdateCount(expectedUpdateCount);
		assertDeleteCount(expectedDeleteCount);
	}

	/**
	 * Assert select statement count
	 *
	 * @param expectedSelectCount
	 *            expected select statement count
	 */
	public static void assertSelectCount(int expectedSelectCount) {
		QueryCount queryCount = QueryCountHolder.getGrandTotal();
		int recordedSelectCount = queryCount.getSelect();
		if (expectedSelectCount != recordedSelectCount) {
			throw new SQLSelectCountMismatchException(expectedSelectCount, recordedSelectCount);
		}
	}

	/**
	 * Assert insert statement count
	 *
	 * @param expectedInsertCount
	 *            expected insert statement count
	 */
	public static void assertInsertCount(int expectedInsertCount) {
		QueryCount queryCount = QueryCountHolder.getGrandTotal();
		int recordedInsertCount = queryCount.getInsert();
		if (expectedInsertCount != recordedInsertCount) {
			throw new SQLInsertCountMismatchException(expectedInsertCount, recordedInsertCount);
		}
	}

	/**
	 * Assert update statement count
	 *
	 * @param expectedUpdateCount
	 *            expected update statement count
	 */
	public static void assertUpdateCount(int expectedUpdateCount) {
		QueryCount queryCount = QueryCountHolder.getGrandTotal();
		int recordedUpdateCount = queryCount.getUpdate();
		if (expectedUpdateCount != recordedUpdateCount) {
			throw new SQLUpdateCountMismatchException(expectedUpdateCount, recordedUpdateCount);
		}
	}

	/**
	 * Assert delete statement count
	 *
	 * @param expectedDeleteCount
	 *            expected delete statement count
	 */
	public static void assertDeleteCount(int expectedDeleteCount) {
		QueryCount queryCount = QueryCountHolder.getGrandTotal();
		int recordedDeleteCount = queryCount.getDelete();
		if (expectedDeleteCount != recordedDeleteCount) {
			throw new SQLDeleteCountMismatchException(expectedDeleteCount, recordedDeleteCount);
		}
	}
}