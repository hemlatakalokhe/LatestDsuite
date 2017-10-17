/**
 *
 */
package de.bonprix.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.RememberMeServices;

import de.bonprix.user.dto.Capability;
import de.bonprix.user.dto.Group;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.PrincipalGroup;
import de.bonprix.user.dto.PrincipalRole;
import de.bonprix.user.dto.Role;
import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 28.10.2016
 *
 */
public class BonprixRememberMeService implements RememberMeServices {
	private static final Logger LOGGER = LoggerFactory.getLogger(BonprixRememberMeService.class);

	private final AuthenticationService authenticationService;

	private final Long applicationId;

	/**
	 * @param authenticationService
	 */
	public BonprixRememberMeService(final AuthenticationService authenticationService, final Long applicationId) {
		this.authenticationService = authenticationService;
		this.applicationId = applicationId;
	}

	@Override
	public Authentication autoLogin(final HttpServletRequest request, final HttpServletResponse response) {
		BonprixRememberMeService.LOGGER.debug("starting autLogin of user by remembermeKey");

		// fetch all authKey attributes from header or query param
		final String authKey = lookupParam(request, AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE);
		String rootAuthKey = lookupParam(request, AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE);

		if (authKey == null) {
			return null;
		}

		if (rootAuthKey == null) {
			rootAuthKey = authKey;
		}

		BonprixRememberMeService.LOGGER.debug("found authKey {}", anonymousAuthKey(authKey));
		BonprixRememberMeService.LOGGER.debug("found rootAuthKey {}", anonymousAuthKey(rootAuthKey));

		return executeUserValidation(request, response, authKey, rootAuthKey);
	}

	@Override
	public void loginFail(final HttpServletRequest request, final HttpServletResponse response) {
		BonprixRememberMeService.LOGGER.debug("loginFail");
	}

	@Override
	public void loginSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication successfulAuthentication) {
		BonprixRememberMeService.LOGGER.debug("loginSuccess");
	}

	/**
	 * performs the actual validation of the authentication key. If the
	 * authentication is successful, the principal data fetched from the user-ws
	 * will be set into the security context and session. If the authKey could
	 * not be validated successfully, the filter will handle the request as
	 * specified with {@link UnauthorizedAction}.
	 *
	 * @param request
	 *            the servlet request
	 * @param response
	 *            the servlet response
	 * @param chain
	 *            the follow up filter chain
	 * @param httpRequest
	 *            the http request object
	 * @param httpResponse
	 *            the http response object
	 * @param authenticationKey
	 *            the authKey of the request
	 * @throws IOException
	 *             in case of an IO error
	 * @throws ServletException
	 *             general error
	 */
	private BonprixAuthentication executeUserValidation(final ServletRequest request, final ServletResponse response,
			final String authenticationKey, final String rootAuthenticationKey) {
		try {
			BonprixRememberMeService.LOGGER
				.trace("authenticating main user with authKey ..." + anonymousAuthKey(authenticationKey));
			BonprixRememberMeService.LOGGER
				.trace("authenticating root user with authKey " + anonymousAuthKey(rootAuthenticationKey));

			final Principal principal = validateAuthKey(authenticationKey);

			final List<GrantedAuthority> grantedAuths = BonprixRememberMeService.getGrantedAuthorities(principal);

			final BonprixAuthentication token = new BonprixAuthentication(principal, authenticationKey, grantedAuths);
			token.setAuthenticated(true);

			if (!rootAuthenticationKey.equals(authenticationKey)) {
				final Principal rootPrincipal = validateAuthKey(rootAuthenticationKey);
				token.setRootPrincipal(rootPrincipal);
				token.setRootAuthKey(rootAuthenticationKey);
			}
			return token;
		} catch (final NotAuthorizedException e) {
			BonprixRememberMeService.LOGGER.debug("Invalid authentication " + anonymousAuthKey(authenticationKey), e);
		} catch (final Exception e) {
			BonprixRememberMeService.LOGGER.error("Error during validation", e);
		}
		return null;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(final Principal principal) {
		final List<GrantedAuthority> grantedAuths = new ArrayList<>();
		for (final PrincipalRole role : principal.getPrincipalRoles()) {
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + role.getId()));
		}
		for (final String capabilityKey : principal.getCapabilityMap()
			.keySet()) {
			grantedAuths.add(new SimpleGrantedAuthority("CAP_" + capabilityKey));
		}
		return grantedAuths;
	}

	private Principal validateAuthKey(final String authKey) throws WebApplicationException {
		final User validationResult = this.authenticationService.validateRemeberMeKey(authKey, this.applicationId);
		BonprixRememberMeService.LOGGER.trace(
												"authenticated authenticationKey {} against authenticationService with result {}",
												anonymousAuthKey(authKey), validationResult);
		return BonprixRememberMeService.extractPrincipal(validationResult);
	}

	/**
	 * Extracts the principal from the return value of the user service.
	 *
	 * @param validationResult
	 *            the authentication result
	 * @return the principal
	 */
	public static Principal extractPrincipal(final User validationResult) {
		Principal principal;
		principal = new Principal(validationResult.getUsername());
		principal.setId(validationResult.getId());
		principal.setFullname(validationResult.getFullname());
		principal.setClientId(validationResult.getClientId());
		principal.setLanguageId(validationResult.getLanguageId());

		for (final Role role : validationResult.getRoles()) {
			principal.addRole(new PrincipalRole(role.getId(), role.getName()));
		}

		for (final Group group : validationResult.getGroups()) {
			principal.addGroup(new PrincipalGroup(group.getId(), group.getName()));
		}

		for (final Capability capability : validationResult.getCapabilities()) {
			principal.addCapability(capability.getCapabilityKey(), capability.getPermissionType());
		}

		return principal;
	}

	/**
	 * Searches for the given param in parameters and header (first header, then
	 * query parameter).
	 *
	 * @param httpRequest
	 *            the http request object
	 * @param key
	 *            the parameter key name
	 * @return the value string if found (otherwise null)
	 */
	private String lookupParam(final HttpServletRequest httpRequest, final String key) {
		String value = httpRequest.getHeader(key);

		if (value == null) {
			value = httpRequest.getParameter(key);
		}

		return value;
	}

	private String anonymousAuthKey(final String authKey) {
		final int amountOfCharsFromTheEnd = 10;
		final int beginIndex = authKey.length() - amountOfCharsFromTheEnd;
		if (beginIndex > 0) {
			return authKey.substring(beginIndex);
		}

		// if authKey is smaller then amountOfCharsFromTheEnd length then return
		// half of the authKey
		return authKey.substring(authKey.length() / 2);
	}
}
