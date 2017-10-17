package de.bonprix.exception;

@SuppressWarnings("serial")
public class GenerateSqlHashException extends RuntimeException {
	public GenerateSqlHashException() {
		// empty
	}

	public GenerateSqlHashException(String message) {
		super(message);
	}

	public GenerateSqlHashException(Throwable cause) {
		super(cause);
	}

	public GenerateSqlHashException(String message, Throwable cause) {
		super(message, cause);
	}
}
