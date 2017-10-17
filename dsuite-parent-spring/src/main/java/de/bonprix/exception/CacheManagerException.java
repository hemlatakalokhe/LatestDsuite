package de.bonprix.exception;

public class CacheManagerException extends RuntimeException {

	public CacheManagerException(String msg, Exception e) {
		super(msg, e);
	}
}
