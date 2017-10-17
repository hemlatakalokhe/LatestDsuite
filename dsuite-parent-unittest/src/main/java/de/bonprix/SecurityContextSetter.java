package de.bonprix;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import de.bonprix.security.BonprixAuthentication;
import de.bonprix.user.dto.Principal;

/**
 * Dummy security client setup for unit tests
 *
 * @author vbaghdas
 *
 */
public final class SecurityContextSetter {

	public static final Long DEFAULT_PRINCIPAL_ID = -1L;
	public static final String DEFAULT_PRINCIPAL = "inttest";
	public static final long DEFAULT_CLIENT_ID = 1;
	public static final Long DEFAULT_LANGUAGE_ID = 299L;
	public static final String DEFAULT_AUTH_KEY = "qwertzuiopasdfghjkl";

	public static void setDefaultSecurityContext() {
		final SecurityContext context = SecurityContextHolder.getContext();
		final Principal principal = new Principal();

		principal.setId(SecurityContextSetter.DEFAULT_PRINCIPAL_ID);
		principal.setName(SecurityContextSetter.DEFAULT_PRINCIPAL);
		principal.setClientId(SecurityContextSetter.DEFAULT_CLIENT_ID);
		principal.setLanguageId(SecurityContextSetter.DEFAULT_LANGUAGE_ID);

		final BonprixAuthentication authentication = new BonprixAuthentication(principal,
				SecurityContextSetter.DEFAULT_AUTH_KEY, null);
		context.setAuthentication(authentication);
	}
}
