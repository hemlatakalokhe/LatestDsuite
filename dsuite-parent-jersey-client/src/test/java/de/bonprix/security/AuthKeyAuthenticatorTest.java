/**
 *
 */
package de.bonprix.security;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class AuthKeyAuthenticatorTest {

	@Test
	public void testFilter() throws Exception {
		final String username = "unittest";
		final String securityWord = "somepassword";
		final String authKey = "qwefgvhenjtbfz";

		final AuthenticationService authService = Mockito.mock(AuthenticationService.class);
		final AuthKeyAuthenticator authenticator = new AuthKeyAuthenticator(username, securityWord, authService);

		final User user = new User();
		user.setAuthorizationKey(authKey);

		Mockito.when(authService.authenticateUser(username, securityWord, securityWord, 43153L, 1L, true))
			.thenReturn(user);
		final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
		final MultivaluedMap<String, Object> headers = Mockito.mock(MultivaluedMap.class);
		Mockito.when(requestContext.getHeaders())
			.thenReturn(headers);

		authenticator.filter(requestContext);
		authenticator.filter(requestContext);
		authenticator.filter(requestContext);

		Mockito.verify(headers, Mockito.times(3))
			.add(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE, authKey);
		Mockito.verify(authService, Mockito.times(1))
			.authenticateUser(username, securityWord, securityWord, 43153L, 1L, true);
	}

}
