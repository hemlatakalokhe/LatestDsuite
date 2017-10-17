/**
 *
 */
package de.bonprix.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.testng.annotations.Test;

import de.bonprix.user.dto.Capability;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.user.dto.Role;
import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class BonprixRememberMeServiceTest {

	@Test
	public void filterPrincipalUnsetAuthKeyProvidedInHeader() throws IOException, ServletException {
		filterPrincipalUnsetAuthKeyProvided(true, false);
	}

	@Test
	public void filterPrincipalUnsetAuthKeyProvidedInQuery() throws IOException, ServletException {
		filterPrincipalUnsetAuthKeyProvided(false, true);
	}

	@Test
	public void filterPrincipalUnsetAuthKeyProvidedInBoth() throws IOException, ServletException {
		filterPrincipalUnsetAuthKeyProvided(true, true);
	}

	private void filterPrincipalUnsetAuthKeyProvided(final boolean authKeyInHeader, final boolean authKeyInQuery)
			throws IOException, ServletException {
		// mock default stuff
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
		final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

		final String authKey = "1234567890";
		final Long applicationId = 42L;

		final Role r = new Role();
		r.setId(10L);
		r.setName("ADMIN");

		final User user = new User();
		user.setAuthorizationKey(authKey);
		user.setClientId(1L);
		user.setFullname("Unit Test");
		user.setId(42L);
		user.setLanguageId(301L);
		user.setUsername("unittest");
		user.getRoles()
			.add(r);

		if (authKeyInQuery) {
			when(httpRequest.getParameter(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);
		}
		if (authKeyInHeader) {
			when(httpRequest.getHeader(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);
		}
		when(authenticationService.validateRemeberMeKey(authKey, applicationId)).thenReturn(user);

		// do the test
		final BonprixRememberMeService service = new BonprixRememberMeService(authenticationService, applicationId);
		final Authentication authentication = service.autoLogin(httpRequest, httpResponse);

		// principal is not set in session but authKey is provided
		verify(authenticationService, times(1)).validateRemeberMeKey(authKey, applicationId);
		assertThat(authentication, instanceOf(BonprixAuthentication.class));
		final BonprixAuthentication bpAuth = (BonprixAuthentication) authentication;
		assertThat(bpAuth.getPrincipal()
			.getId(), equalTo(42L));
		assertThat(bpAuth.getPrincipal()
			.getName(), equalTo("unittest"));
		assertThat(bpAuth.getAuthKey(), equalTo("1234567890"));
		assertThat(bpAuth.getAuthKey(), equalTo(bpAuth.getRootAuthKey()));
		assertThat(bpAuth.getRootPrincipal(), equalTo(bpAuth.getPrincipal()));
	}

	@Test
	public void filterPrincipalUnsetAuthKeyAndRootAuthKeyProvided() throws IOException, ServletException {
		// mock default stuff
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
		final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

		final String authKey = "1234567890";
		final String rootAuthKey = "qwertzuiokjhg";
		final Long applicationId = 42L;

		final Role r = new Role();
		r.setId(10L);
		r.setName("ADMIN");

		final Capability c1 = new Capability();
		c1.setCapabilityKey("CAP_001");
		c1.setPermissionType(PermissionType.EDIT);
		final Capability c2 = new Capability();
		c2.setCapabilityKey("CAP_002");
		c2.setPermissionType(PermissionType.EDIT);

		final User user = new User();
		user.setAuthorizationKey(authKey);
		user.setClientId(1L);
		user.setFullname("Unit Test");
		user.setId(42L);
		user.setLanguageId(301L);
		user.setUsername("unittest");
		user.getRoles()
			.add(r);
		user.getCapabilities()
			.add(c1);
		user.getCapabilities()
			.add(c2);

		final User rootUser = new User();
		rootUser.setAuthorizationKey(authKey);
		rootUser.setClientId(1L);
		rootUser.setFullname("Root Test");
		rootUser.setId(43L);
		rootUser.setLanguageId(299L);
		rootUser.setUsername("roottest");

		when(httpRequest.getHeader(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);
		when(httpRequest.getHeader(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(rootAuthKey);
		when(authenticationService.validateRemeberMeKey(authKey, applicationId)).thenReturn(user);
		when(authenticationService.validateRemeberMeKey(rootAuthKey, applicationId)).thenReturn(rootUser);

		// do the test
		final BonprixRememberMeService service = new BonprixRememberMeService(authenticationService, applicationId);
		final Authentication authentication = service.autoLogin(httpRequest, httpResponse);

		// principal is not set in session but authKey is provided
		verify(authenticationService, times(1)).validateRemeberMeKey(authKey, applicationId);
		assertThat(authentication, instanceOf(BonprixAuthentication.class));
		final BonprixAuthentication bpAuth = (BonprixAuthentication) authentication;
		assertThat(bpAuth.getPrincipal()
			.getId(), equalTo(42L));
		assertThat(bpAuth.getPrincipal()
			.getName(), equalTo("unittest"));
		assertThat(bpAuth.getPrincipal()
			.hasCapability("CAP_001"), equalTo(true));
		assertThat(bpAuth.getPrincipal()
			.getPermissionType("CAP_001"), equalTo(PermissionType.EDIT));
		assertThat(bpAuth.getAuthKey(), equalTo("1234567890"));
		assertThat(bpAuth.getRootAuthKey(), equalTo("qwertzuiokjhg"));
		assertThat(bpAuth.getRootPrincipal()
			.getId(), equalTo(43L));
		assertThat(bpAuth.getRootPrincipal()
			.getName(), equalTo("roottest"));
	}

	@Test
	public void filterNothingProvided() throws IOException, ServletException {
		// mock everything needed
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
		final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

		// do the test
		final BonprixRememberMeService service = new BonprixRememberMeService(authenticationService, 42L);
		final Authentication authentication = service.autoLogin(httpRequest, httpResponse);

		assertThat(authentication, nullValue());
	}

	@Test
	public void filterInvalidAuthKeyProvided() throws IOException, ServletException {
		// mock everything needed
		final AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);
		final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
		final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

		final String authKey = "1234567890";
		final Long applicationId = 42L;

		when(httpRequest.getParameter(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);
		when(authenticationService.validateRemeberMeKey(authKey, applicationId))
			.thenThrow(NotAuthorizedException.class);

		// do the test
		final BonprixRememberMeService service = new BonprixRememberMeService(authenticationService, 42L);
		final Authentication authentication = service.autoLogin(httpRequest, httpResponse);

		// no authKey, so nothing should happen
		assertThat(authentication, nullValue());
	}
}
