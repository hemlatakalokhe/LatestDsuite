package de.bonprix.sqlcount.exception;

/**
 * SQLUpdateCountMismatchException - Thrown whenever there is a mismatch between
 * expected update statements count and the ones being executed.
 *
 * @author Vlad Mihalcea
 */
@SuppressWarnings("serial")
public class SQLUpdateCountMismatchException extends SQLStatementCountMismatchException {

	public SQLUpdateCountMismatchException(int expected, int recorded) {
		super(expected, recorded);
	}
}
