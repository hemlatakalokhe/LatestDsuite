package de.bonprix.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.bonprix.security.AuthorizationKeyStorage;

@WebFilter("/*")
/**
 * Servlet filter; puts authentication key from session's attribute into local threaded storage.
 */
public class RestoreAuthenticationKeyFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            final Object authKey = session.getAttribute(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE);
            final Object rootAuthKey = session.getAttribute(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE);
            if (authKey instanceof String) {
                AuthorizationKeyStorage.setAuthorizationKey((String) authKey);

                if (rootAuthKey != null && rootAuthKey instanceof String) {
                    AuthorizationKeyStorage.setRootAuthorizationKey((String) rootAuthKey);
                }
                else {
                    AuthorizationKeyStorage.setRootAuthorizationKey(AuthorizationKeyStorage.getAuthorizationKey());
                }
            }
            else {
                AuthorizationKeyStorage.setAuthorizationKey(null);
                AuthorizationKeyStorage.setRootAuthorizationKey(null);
            }
        }
        else {
            AuthorizationKeyStorage.setAuthorizationKey(null);
            AuthorizationKeyStorage.setRootAuthorizationKey(null);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}