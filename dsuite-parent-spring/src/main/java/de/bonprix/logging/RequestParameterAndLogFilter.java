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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.RequestId;
import de.bonprix.configuration.LoggingConfiguration;

@WebFilter("/*")
public class RequestParameterAndLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParameterAndLogFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getHeader(RequestId.REQUEST_ID_HEADER) != null) {
            RequestId.setRequestId(httpRequest.getHeader(RequestId.REQUEST_ID_HEADER));
        }
        else {
            RequestId.setRequestId(null);
        }
        final StringBuffer requestUri = httpRequest.getRequestURL();
        if (httpRequest.getQueryString() != null) {
            requestUri.append("?");
            if (httpRequest.getQueryString()
                .contains("authKey=")) {
                requestUri.append(httpRequest.getQueryString()
                    .replaceFirst("&?authKey=[^&]+", ""));
            }
            else {
                requestUri.append(httpRequest.getQueryString());
            }
        }
        requestUri.insert(0, httpRequest.getMethod() + " ");

        ThreadContext.put(LoggingConfiguration.REQUEST_URL, requestUri.toString());
        ThreadContext.put(LoggingConfiguration.APPLICATION, request.getServletContext()
            .getContextPath());
        ThreadContext.put(LoggingConfiguration.REQUEST_ID, RequestId.getRequestId());
        ThreadContext.put(LoggingConfiguration.ENVIRONMENT, "DEV");
        try {
            chain.doFilter(httpRequest, response);
        }
        catch (final Throwable ex) {
            RequestParameterAndLogFilter.LOGGER.error("catching:", ex);
            throw ex;
        }
        finally {
            ThreadContext.remove(LoggingConfiguration.REQUEST_ID);
            ThreadContext.remove(LoggingConfiguration.APPLICATION);
            ThreadContext.remove(LoggingConfiguration.REQUEST_URL);
            ThreadContext.remove(LoggingConfiguration.ENVIRONMENT);
        }
    }

    @Override
    public void destroy() {
    }
}
