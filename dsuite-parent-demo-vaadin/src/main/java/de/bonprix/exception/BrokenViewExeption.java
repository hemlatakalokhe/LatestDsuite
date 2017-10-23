package de.bonprix.exception;

@SuppressWarnings("serial")
public class BrokenViewExeption extends RuntimeException {

    public BrokenViewExeption(final String message) {
        super(message);
    }
}
