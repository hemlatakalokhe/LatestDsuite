package de.bonprix.exception;

import de.bonprix.user.dto.Principal;

/**
 * Expression is thrown when the principal type of the security contexts
 * principal is wrong.
 *
 * @author cthiel
 * @date 05.10.2016
 *
 */
@SuppressWarnings("serial")
public class WrongPrincipalTypeException extends RuntimeException {

	public WrongPrincipalTypeException(final Object foundPrincipal) {
		super("Principal in SecurityContext must be of type " + Principal.class + " but is of "
				+ foundPrincipal.getClass()
					.getName());
	}

}
