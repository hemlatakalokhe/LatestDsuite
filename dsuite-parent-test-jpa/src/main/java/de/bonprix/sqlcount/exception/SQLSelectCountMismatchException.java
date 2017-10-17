package de.bonprix.sqlcount.exception;

/**
 * SQLSelectCountMismatchException - Thrown whenever there is a mismatch between
 * expected select statements count and the ones being executed.
 *
 * @author Vlad Mihalcea
 */
@SuppressWarnings("serial")
public class SQLSelectCountMismatchException extends SQLStatementCountMismatchException {

	public SQLSelectCountMismatchException(int expected, int recorded) {
		super(expected, recorded);
	}
}
