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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.security.PrincipalSecurityContext;

@WebFilter("/rest**")
public class RequestLogFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// empty
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		final StringBuffer requestUri = httpRequest.getRequestURL();
		if (httpRequest.getQueryString() != null) {
			requestUri.append("?");
			if (httpRequest.getQueryString()
				.contains("authKey=")) {
				requestUri.append(httpRequest.getQueryString()
					.replaceFirst("&?authKey=[^&]+", ""));
			} else {
				requestUri.append(httpRequest.getQueryString());
			}
		}
		requestUri.insert(0, httpRequest.getMethod() + " ");

		final long start = System.currentTimeMillis();
		try {
			chain.doFilter(httpRequest, response);
		} finally {
			final long duration = System.currentTimeMillis() - start;

			RequestLogFilter.LOGGER.info(duration + "ms - " + PrincipalSecurityContext.getRootPrincipal()
				.getName() + " - " + requestUri);
		}
	}

	@Override
	public void destroy() {
		// empty
	}
}
