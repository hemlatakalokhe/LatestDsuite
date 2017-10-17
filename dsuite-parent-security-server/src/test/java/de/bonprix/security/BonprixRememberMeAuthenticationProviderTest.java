/**
 *
 */
package de.bonprix.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.testng.annotations.Test;

import de.bonprix.user.dto.Principal;

/**
 * @author cthiel
 * @date 01.11.2016
 *
 */
public class BonprixRememberMeAuthenticationProviderTest {

	@Test
	public void testAuthenticateSuccess() {
		final BonprixRememberMeAuthenticationProvider provider = new BonprixRememberMeAuthenticationProvider();

		final BonprixAuthentication authentication = new BonprixAuthentication(new Principal(1L, "unittest"),
				"qwertzui", null);

		final Authentication result = provider.authenticate(authentication);

		assertThat(authentication, equalTo(result));
	}

	@Test
	public void testAuthenticateFail() {
		final BonprixRememberMeAuthenticationProvider provider = new BonprixRememberMeAuthenticationProvider();

		final Authentication result = provider
			.authenticate(new RememberMeAuthenticationToken("13134", new Principal(1L, "unittest"), null));

		assertThat(result, nullValue());
	}

	@Test
	public void testSupportsTrue() {
		final BonprixRememberMeAuthenticationProvider provider = new BonprixRememberMeAuthenticationProvider();
		assertThat(provider.supports(BonprixAuthentication.class), equalTo(true));
	}

	@Test
	public void testSupportsFalse() {
		final BonprixRememberMeAuthenticationProvider provider = new BonprixRememberMeAuthenticationProvider();
		assertThat(provider.supports(RememberMeAuthenticationToken.class), equalTo(false));
		assertThat(provider.supports(UsernamePasswordAuthenticationToken.class), equalTo(false));
	}

}
