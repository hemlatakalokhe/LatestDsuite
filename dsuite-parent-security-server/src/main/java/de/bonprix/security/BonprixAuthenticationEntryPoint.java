/**
 *
 */
package de.bonprix.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import de.bonprix.exception.RedirectUrlEncodeException;

/**
 * @author cthiel
 * @date 28.10.2016
 *
 */
public class BonprixAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger LOGGER = LoggerFactory.getLogger(BonprixAuthenticationEntryPoint.class);

	private final long applicationId;
	private final String userLoginUrl;

	public BonprixAuthenticationEntryPoint(final long applicationId, final String userLoginUrl) {
		this.applicationId = applicationId;
		this.userLoginUrl = userLoginUrl;
	}

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		BonprixAuthenticationEntryPoint.LOGGER.debug("Redirecting client to login URL {}", this.userLoginUrl);
		response.sendRedirect(response.encodeRedirectURL(appendRequestUrl(request, this.userLoginUrl)));
		return;
	}

	/**
	 * Forward the request to the user-ws login form.<br>
	 * <br>
	 * Appends also the following dynamic parameters to the URL:
	 * <ul>
	 * <li><b>redirectUrl: </b>the original URL of this request; used by the
	 * user-ws to redirect after successful authentication</li>
	 * <li><b>applicationId: </b>The applicationID of this application so the
	 * user-ws knows for which application the user will authenticating</li>
	 * </ul>
	 *
	 * @param httpRequest
	 *            the http request object
	 * @param httpResponse
	 *            the http response object
	 * @throws IOException
	 *             if an io error happened
	 */
	private String appendRequestUrl(final HttpServletRequest request, final String baseUrl) {
		final StringBuffer requestUrl = request.getRequestURL();
		if (request.getQueryString() != null) {
			requestUrl.append("?")
				.append(request.getQueryString());
		}

		final UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(baseUrl));
		try {
			uriBuilder.queryParam("redirectUrl", URLEncoder.encode(requestUrl.toString(), "UTF-8"));
			uriBuilder.queryParam("applicationId", this.applicationId);
		} catch (final UnsupportedEncodingException e) {
			throw new RedirectUrlEncodeException("Could not url encode RedirectUrl", e);
		}

		return uriBuilder.build()
			.toString();
	}
}
