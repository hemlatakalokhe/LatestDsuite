package de.bonprix.exception;

/**
 * Exception is thrown when unexcpectedly the principal of the security context is not set.
 * 
 * @author cthiel
 * @date 05.10.2016
 *
 */
public class PrincipalMissingException extends RuntimeException {
    private static final long serialVersionUID = -2147412240058191979L;

    public PrincipalMissingException() {
        super("Principal is missing!");
    }

}
