package de.bonprix.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import de.bonprix.RequestId;

@Provider
public class RequestIdProvider implements ClientRequestFilter {

    @Override
    public void filter(final ClientRequestContext requestContext) throws IOException {
        final MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        headers.add(RequestId.REQUEST_ID_HEADER, RequestId.getRequestId());
    }

}
