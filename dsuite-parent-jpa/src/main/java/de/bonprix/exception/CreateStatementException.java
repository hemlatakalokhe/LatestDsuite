package de.bonprix.exception;

@SuppressWarnings("serial")
public class CreateStatementException extends RuntimeException {

	public CreateStatementException() {
		// empty
	}

	public CreateStatementException(String message) {
		super(message);
	}

	public CreateStatementException(Throwable cause) {
		super(cause);
	}

	public CreateStatementException(String message, Throwable cause) {
		super(message, cause);
	}
}
