package de.bonprix.exception;

public class MailEncodingException extends RuntimeException {

	public MailEncodingException(String msg, Exception e) {
		super(msg, e);
	}
}
