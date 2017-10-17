package de.bonprix.security;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class SecurityHttpSessionListener implements HttpSessionListener {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityHttpSessionListener.class);

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        SecurityHttpSessionListener.LOG.info("session created: {}", se.getSession()
                .getId());
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        SecurityHttpSessionListener.LOG.info("session destroyed: {}", se.getSession()
                .getId());
    }

}
