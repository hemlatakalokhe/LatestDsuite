package de.bonprix.exception;

@SuppressWarnings("serial")
public class AroundAnyRepositoryMethode extends RuntimeException {

	public AroundAnyRepositoryMethode() {
		// empty
	}

	public AroundAnyRepositoryMethode(String message) {
		super(message);
	}

	public AroundAnyRepositoryMethode(Throwable cause) {
		super(cause);
	}

	public AroundAnyRepositoryMethode(String message, Throwable cause) {
		super(message, cause);
	}
}
