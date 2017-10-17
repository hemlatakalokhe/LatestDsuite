package de.bonprix.sqlcount.exception;

@SuppressWarnings("serial")
public class OptimisticConcurrencyControlAspectProceedException extends RuntimeException {

	public OptimisticConcurrencyControlAspectProceedException(String msg) {
		super(msg);
	}

	public OptimisticConcurrencyControlAspectProceedException(String msg, Exception e) {
		super(msg, e);
	}
}
