package de.bonprix.security;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Strings;

import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * Utility class for setting / unsetting spring security context
 *
 * @author vbaghdas
 *
 */
public final class AuthenticationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtil.class);

	private AuthenticationUtil() {
	}

	/**
	 * Sets the system principal to security context.
	 */
	public static void setPrincipal(String systemuserUsername, String systemuserPassword,
			AuthenticationService authenticationService, Long applicationId, Long authKeyCacheTtl,
			BonprixAuthentication token, LocalDateTime authKeyCacheExpireDate) {
		final BonprixAuthentication bpAuthtoken = AuthenticationUtil
			.getSystemAuthentication(	systemuserUsername, systemuserPassword, authenticationService, applicationId,
										authKeyCacheTtl, token, authKeyCacheExpireDate);
		SecurityContextHolder.getContext()
			.setAuthentication(bpAuthtoken);
	}

	/**
	 * Retrieve the authentication from the user-ws. Check before if the
	 * currently cached token is expired.
	 *
	 * @return the valid authKey
	 */
	private static BonprixAuthentication getSystemAuthentication(String systemuserUsername, String systemuserPassword,
			AuthenticationService authenticationService, Long applicationId, Long authKeyCacheTtl,
			BonprixAuthentication token, LocalDateTime authKeyCacheExpireDate) {

		BonprixAuthentication newToken = null;
		if (Strings.isNullOrEmpty(systemuserUsername) || Strings.isNullOrEmpty(systemuserPassword)) {
			throw new IllegalStateException(
					"properties application.systemuser.username and application.systemuser.password have to be set when defining a JmsListener to provide a system principal");
		}

		if (token == null || (authKeyCacheExpireDate != null && authKeyCacheExpireDate.isBefore(LocalDateTime.now()))) {
			AuthenticationUtil.LOGGER.debug("No authToken found or expired, requesting new authentictaion for user {}",
											systemuserUsername);
			final User user = authenticationService.authenticateUser(	systemuserUsername, systemuserPassword,
																		systemuserPassword, applicationId, 1, true);

			final Principal principal = BonprixRememberMeService.extractPrincipal(user);
			authKeyCacheExpireDate = LocalDateTime.now()
				.plus(authKeyCacheTtl, ChronoUnit.MILLIS);

			final List<GrantedAuthority> grantedAuths = BonprixRememberMeService.getGrantedAuthorities(principal);

			newToken = new BonprixAuthentication(principal, user.getAuthorizationKey(), grantedAuths);
			newToken.setAuthenticated(true);
		}

		return newToken;
	}

	/**
	 * Clears the security context.
	 */
	public static void unsetPrincipal() {
		SecurityContextHolder.getContext()
			.setAuthentication(null);
	}
}
