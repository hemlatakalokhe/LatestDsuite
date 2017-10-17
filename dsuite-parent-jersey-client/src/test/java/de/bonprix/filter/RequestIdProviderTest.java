/**
 *
 */
package de.bonprix.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bonprix.RequestId;

/**
 * @author cthiel
 * @date 24.10.2016
 *
 */
public class RequestIdProviderTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testException() throws IOException {
        final String requestId = "qwertzuio";

        final ClientRequestContext requestContext = mock(ClientRequestContext.class);
        final MultivaluedMap<String, Object> headers = mock(MultivaluedMap.class);

        RequestId.setRequestId(requestId);

        when(requestContext.getHeaders()).thenReturn(headers);

        final RequestIdProvider filter = new RequestIdProvider();

        filter.filter(requestContext);

        verify(headers).add(RequestId.REQUEST_ID_HEADER, requestId);
    }

}
