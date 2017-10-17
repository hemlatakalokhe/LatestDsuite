package de.bonprix.security.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDateTime;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.security.AuthenticationUtil;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author vbaghdas
 *
 */
public class AuthenticationUtilTest {

	private static final Long applicationId = 43153L; // applicationId of the

	// user webservice
	private static final Long AUTH_KEY_CACHE_TTL = 300000L;

	private LocalDateTime authKeyCacheExpireDate = null;

	private BonprixAuthentication token = null;

	@Test(expectedExceptions = IllegalStateException.class)
	public void testSetPrincipalPropertiesMissing() {
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);

		AuthenticationUtil.setPrincipal(null, null, authenticationService, applicationId, AUTH_KEY_CACHE_TTL,
										this.token, this.authKeyCacheExpireDate);
	}

	@Test
	public void testSetPrincipalPropertiesOk() {
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);

		final String username = "username";
		final String somePwd = "password";
		final User user = new User();
		user.setUsername(username);
		user.setAuthorizationKey("1234567890");

		Mockito.when(authenticationService.authenticateUser(username, somePwd, somePwd, 43153L, 1L, true))
			.thenReturn(user);

		AuthenticationUtil.setPrincipal(username, somePwd, authenticationService, applicationId, AUTH_KEY_CACHE_TTL,
										this.token, this.authKeyCacheExpireDate);

		assertThat(PrincipalSecurityContext.getAuthenticatedPrincipal()
			.getName(), equalTo(username));

		Mockito.verify(authenticationService, Mockito.times(1))
			.authenticateUser(	Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(),
								Mockito.anyLong(), Mockito.anyBoolean());
	}

	@Test(expectedExceptions = PrincipalMissingException.class)
	public void unsetPrincipal() {
		// first set the principal
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
		final String username = "username";
		final String somePwd = "password";

		final User user = new User();
		user.setUsername(username);
		user.setAuthorizationKey("1234567890");

		Mockito.when(authenticationService.authenticateUser(username, somePwd, somePwd, 43153L, 1L, true))
			.thenReturn(user);

		AuthenticationUtil.setPrincipal(username, somePwd, authenticationService, applicationId, AUTH_KEY_CACHE_TTL,
										this.token, this.authKeyCacheExpireDate);

		// unset principal and check
		AuthenticationUtil.unsetPrincipal();

		assertThat(PrincipalSecurityContext.getAuthenticatedPrincipal(), nullValue());
	}
}
