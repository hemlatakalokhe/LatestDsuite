package de.bonprix.exception;

public class RedirectUrlEncodeException extends RuntimeException {

	public RedirectUrlEncodeException(String msg, Exception e) {
		super(msg, e);
	}
}
