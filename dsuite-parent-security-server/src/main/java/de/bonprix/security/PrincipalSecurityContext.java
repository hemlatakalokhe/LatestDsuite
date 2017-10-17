package de.bonprix.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.exception.WrongPrincipalTypeException;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Principal;

public final class PrincipalSecurityContext {

	/**
	 * search for principal set by Spring Security
	 *
	 * @return the current principal otherwise an Exception is risen
	 * @throws PrincipalMissingException
	 *             when no Principal is set at all
	 * @throws WrongPrincipalTypeException
	 *             when principal in SecurityContext is of wrong type
	 */
	public static Principal getAuthenticatedPrincipal() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null
				|| AnonymousAuthenticationToken.class.isAssignableFrom(context.getAuthentication()
					.getClass())) {
			throw new PrincipalMissingException();
		}
		final Object genericPrincipal = context.getAuthentication()
			.getPrincipal();
		if (genericPrincipal == null) {
			throw new PrincipalMissingException();
		}
		if (!(genericPrincipal instanceof Principal)) {
			throw new WrongPrincipalTypeException(genericPrincipal);
		}
		return (Principal) genericPrincipal;
	}

	/**
	 * Search for principal set by Spring Security
	 *
	 * @return the current principal otherwise an Exception is risen
	 * @throws PrincipalMissingException
	 *             when no Principal is set at all
	 * @throws WrongPrincipalTypeException
	 *             when principal in SecurityContext is of wrong type
	 */
	public static Principal getRootPrincipal() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() == null) {
			throw new PrincipalMissingException();
		}
		final BonprixAuthentication authentication = (BonprixAuthentication) context.getAuthentication();
		final Principal rootPrincipal = authentication.getRootPrincipal();
		if (rootPrincipal == null) {
			throw new PrincipalMissingException();
		}
		return rootPrincipal;
	}

	/**
	 * Search for the capability key in the principal capability list and checks
	 * the permission types.
	 * 
	 * @param capabilityKey
	 * @param permissionType
	 */
	public static Boolean hasPermission(String capabilityKey, PermissionType permissionType) {
		if (StringUtils.isEmpty(capabilityKey) && permissionType == null) {
			return Boolean.TRUE;
		}
		return PrincipalSecurityContext.getAuthenticatedPrincipal()
			.getPermissionType(capabilityKey) == permissionType;
	}
}
