package de.bonprix.security;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client side filter; adds to each request the authentifiaction key from local thread storage, if there is one (as a header)
 */
public class RootAuthKeyProvider implements ClientRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RootAuthKeyProvider.class);

    @Override
    public void filter(final ClientRequestContext requestContext) throws IOException {
        final String rootAuthorizationKey = AuthorizationKeyStorage.getRootAuthorizationKey();
        if (rootAuthorizationKey != null) {
            RootAuthKeyProvider.LOG.trace("adding rootAuthKey to ClientRequest {}", rootAuthorizationKey);
            requestContext.getHeaders()
                .add(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE, rootAuthorizationKey);
        }
    }

}
