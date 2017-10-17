/**
 *
 */
package de.bonprix.security.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 10.10.2016
 *
 */
public class CachedAuthenticationServiceTest {

	/**
	 * Test only the delegation, not the caching.
	 */
	@Test
	public void testAuthenticate() {
		final AuthenticationService wrappedService = mock(AuthenticationService.class);

		final User user = new User();
		user.setId(42L);

		Mockito.when(wrappedService.authenticateUser(	Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
														Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
			.thenReturn(user);

		final CachedAuthenticationService service = new CachedAuthenticationService();
		service.setDelegatedAuthenticationService(wrappedService);

		final User other = service.authenticateUser("user", "password", "password", 1L, 1L, true);

		assertThat(other, equalTo(user));
	}

	/**
	 * Test only the delegation, not the caching.
	 */
	@Test
	public void testRemembermeKey() {
		final AuthenticationService wrappedService = mock(AuthenticationService.class);

		final User user = new User();
		user.setId(42L);

		Mockito.when(wrappedService.validateRemeberMeKey(Mockito.anyString(), Mockito.anyLong()))
			.thenReturn(user);

		final CachedAuthenticationService service = new CachedAuthenticationService();
		service.setDelegatedAuthenticationService(wrappedService);

		final User other = service.validateRemeberMeKey("1234567890", 1L);

		assertThat(other, equalTo(user));
	}

}
