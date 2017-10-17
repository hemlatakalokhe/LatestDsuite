/**
 *
 */
package de.bonprix.security;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.verify;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class BasicAuthenticatorTest {

    @Test
    public void testFilter() throws IOException {
        final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
        final MultivaluedMap<String, Object> headers = Mockito.mock(MultivaluedMap.class);

        Mockito.when(requestContext.getHeaders())
            .thenReturn(headers);

        final BasicAuthenticator filter = new BasicAuthenticator("unit", "test");
        filter.filter(requestContext);

        final ArgumentCaptor<String> headerKeyCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> headerValueCaptor = ArgumentCaptor.forClass(Object.class);

        verify(headers).add(headerKeyCaptor.capture(), headerValueCaptor.capture());

        assertThat(headerKeyCaptor.getValue(), equalTo("Authorization"));
        assertThat(headerValueCaptor.getValue(), equalTo("Basic dW5pdDp0ZXN0"));
    }

}
