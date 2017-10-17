package de.bonprix.exception;

public class CustomNoSuchMethodException extends RuntimeException {
	public CustomNoSuchMethodException(String msg, Exception e) {
		super(msg, e);
	}
}
