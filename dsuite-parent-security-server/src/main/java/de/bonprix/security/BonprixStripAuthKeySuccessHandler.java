/**
 *
 */
package de.bonprix.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author cthiel
 * @date 28.10.2016
 *
 */
public class BonprixStripAuthKeySuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BonprixStripAuthKeySuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse, final Authentication authentication)
            throws IOException, ServletException {
        // set stuff in session vars
        final BonprixAuthentication bonprixAuthentication = (BonprixAuthentication) authentication;
        httpRequest.getSession()
            .setAttribute(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE, bonprixAuthentication.getAuthKey());
        httpRequest.getSession()
            .setAttribute(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE, bonprixAuthentication.getRootAuthKey());

        // redirect to url without authkey
        final UriBuilder uriBuilder = UriBuilder.fromUri(httpRequest.getRequestURL()
            .append(httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "")
            .toString());
        uriBuilder.replaceQueryParam(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE);
        if (!httpRequest.getRequestURI()
            .endsWith("/")) {
            uriBuilder.path("/");
        }
        BonprixStripAuthKeySuccessHandler.LOGGER.debug("redirecting to URL {} to clean authKey", uriBuilder.build());
        httpResponse.sendRedirect(uriBuilder.build()
            .toString());
    }

}
