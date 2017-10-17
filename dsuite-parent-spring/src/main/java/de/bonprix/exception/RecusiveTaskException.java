package de.bonprix.exception;

public class RecusiveTaskException extends RuntimeException {

	public RecusiveTaskException(String message, Exception e) {
		super(message, e);
	}
}
