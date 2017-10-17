package de.bonprix.exception;

@SuppressWarnings("serial")
public class FilterException extends RuntimeException {

	public FilterException() {
		// empty
	}

	public FilterException(String message) {
		super(message);
	}

	public FilterException(Throwable cause) {
		super(cause);
	}

	public FilterException(String message, Throwable cause) {
		super(message, cause);
	}
}
