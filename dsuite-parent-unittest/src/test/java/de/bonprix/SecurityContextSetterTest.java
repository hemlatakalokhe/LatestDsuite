package de.bonprix;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.user.dto.Principal;

/**
 * @author vbaghdas
 */
public class SecurityContextSetterTest {

	@Test
	public void setSecurityContextTest() {
		SecurityContextSetter.setDefaultSecurityContext();

		final PreAuthenticatedAuthenticationToken authentication = (PreAuthenticatedAuthenticationToken) SecurityContextHolder
			.getContext()
			.getAuthentication();
		final Principal principal = (Principal) authentication.getPrincipal();
		Assert.assertSame(principal.getId(), SecurityContextSetter.DEFAULT_PRINCIPAL_ID);
		Assert.assertSame(principal.getName(), SecurityContextSetter.DEFAULT_PRINCIPAL);
		Assert.assertSame(principal.getClientId(), SecurityContextSetter.DEFAULT_CLIENT_ID);
		Assert.assertSame(principal.getLanguageId(), SecurityContextSetter.DEFAULT_LANGUAGE_ID);
	}
}
