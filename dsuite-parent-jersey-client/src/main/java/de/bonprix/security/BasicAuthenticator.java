package de.bonprix.security;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Authenticator client side filter for webservices, that need authentication
 * via user && password, like our user webservice. THis filter adds a HTTP401
 * Basic Authentication header to every request.
 */
public class BasicAuthenticator implements ClientRequestFilter {

	private final String user;
	private final String password;

	/**
	 * Creates a basic authentication filter.
	 *
	 * @param user
	 *            the username
	 * @param password
	 *            the password
	 */
	public BasicAuthenticator(final String user, final String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		final MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String basicAuthentication = getBasicAuthentication();
		headers.add("Authorization", basicAuthentication);

	}

	/**
	 * Generates the base64 encoded header string.
	 *
	 * @return the Base64 encoded header string
	 */
	private String getBasicAuthentication() {
		final String usernameAndPassword = this.user + ":" + this.password;
		return "Basic " + java.util.Base64.getEncoder()
			.encodeToString(usernameAndPassword.getBytes());
	}
}
