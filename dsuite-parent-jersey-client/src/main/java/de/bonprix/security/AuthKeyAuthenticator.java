package de.bonprix.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.jersey.ClientFactoryConfig;
import de.bonprix.user.dto.User;
import de.bonprix.user.service.AuthenticationService;

/**
 * Client side authentication filter for webservice calls. This filter will
 * query the user-ws for an authKey (via username + password) and add the
 * authKey to every request as header field. The authKey will be cached
 * internally for <code>AuthKeyAuthenticator.AUTH_KEY_CACHE_TTL</code>
 * milliseconds.
 *
 * @author cthiel
 *
 */
public class AuthKeyAuthenticator implements ClientRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthKeyAuthenticator.class);

	private static final Long APPLICATION_ID = 43153L; // applicationId of the
														// user webservice

	private static final Long AUTH_KEY_CACHE_TTL = 300000L;

	private final String user;
	private final String password;

	private final AuthenticationService authenticationService;

	private LocalDateTime authKeyCacheExpireDate = null;
	private String authKey = null;

	/**
	 * Creates an authKey authenticator with the given username and password.
	 * The username and password will be used to request an authKey from the
	 * user-ws.
	 *
	 * @param user
	 *            username
	 * @param password
	 *            password
	 * @param authenticationService
	 *            the authentication service
	 */
	public AuthKeyAuthenticator(final String user, final String password,
			final AuthenticationService authenticationService) {
		this.user = user;
		this.password = password;
		this.authenticationService = authenticationService;

		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setAddBasicAuthentication(true);
		config.setAddAuthKeyAuthentication(false);
	}

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		final String currentAuthKey = getAuthKey();

		AuthKeyAuthenticator.LOGGER.trace("Adding authKey {} to request", this.authKey);
		final MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		headers.add(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE, currentAuthKey);
	}

	/**
	 * Retrieve the authKey from the user-ws. Check before if the currently
	 * cached key is expired.
	 *
	 * @return the valid authKey
	 */
	private String getAuthKey() {
		if (this.authKey == null
				|| (this.authKeyCacheExpireDate != null && this.authKeyCacheExpireDate.isBefore(LocalDateTime.now()))) {
			AuthKeyAuthenticator.LOGGER.debug(	"No authKey found or expired, requesting new authKey for user {}",
												this.user);
			final User authenticatedUser = this.authenticationService
				.authenticateUser(	this.user, this.password, this.password, AuthKeyAuthenticator.APPLICATION_ID, 1,
									true);

			this.authKey = authenticatedUser.getAuthorizationKey();
			this.authKeyCacheExpireDate = LocalDateTime.now()
				.plus(AuthKeyAuthenticator.AUTH_KEY_CACHE_TTL, ChronoUnit.MILLIS);
			AuthKeyAuthenticator.LOGGER.debug("Got authKey {0}", this.authKey);
		}

		return this.authKey;
	}

}
