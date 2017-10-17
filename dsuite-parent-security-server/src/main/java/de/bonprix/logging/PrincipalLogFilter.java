package de.bonprix.logging;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.ThreadContext;
import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.security.PrincipalSecurityContext;

@WebFilter("/*")
public class PrincipalLogFilter implements Filter {

    public static final String AUTH_PRINCIPAL = "AUTH_PRINCIPAL";
    public static final String ROOT_PRINCIPAL = "ROOT_PRINCIPAL";

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            ThreadContext.put(PrincipalLogFilter.AUTH_PRINCIPAL, PrincipalSecurityContext.getAuthenticatedPrincipal()
                .getName());
            ThreadContext.put(PrincipalLogFilter.ROOT_PRINCIPAL, PrincipalSecurityContext.getRootPrincipal()
                .getName());
            try {
                chain.doFilter(httpRequest, response);
            }
            finally {
                ThreadContext.remove(PrincipalLogFilter.AUTH_PRINCIPAL);
                ThreadContext.remove(PrincipalLogFilter.ROOT_PRINCIPAL);
            }
        }
        catch (final PrincipalMissingException e) {
            chain.doFilter(httpRequest, response);
        }
    }

    @Override
    public void destroy() {
    }
}
