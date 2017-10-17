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
public class AuthorizationKeyProviderTest {

    @Test
    public void addAuthKeyTest() throws IOException {
        final String authKey = "123456789";
        final String nestedAuthKey = "1234567890";

        AuthorizationKeyStorage.setAuthorizationKey(authKey);
        AuthorizationKeyStorage.setRootAuthorizationKey(nestedAuthKey);

        final RootAuthKeyProvider filter = new RootAuthKeyProvider();

        final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
        final MultivaluedMap<String, Object> headers = Mockito.mock(MultivaluedMap.class);

        Mockito.when(requestContext.getHeaders())
            .thenReturn(headers);

        final ArgumentCaptor<String> headerKeyCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> headerValueCaptor = ArgumentCaptor.forClass(Object.class);

        filter.filter(requestContext);

        Mockito.verify(headers)
            .add(headerKeyCaptor.capture(), headerValueCaptor.capture());

        assertThat(headerKeyCaptor.getValue(), equalTo(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE));
        assertThat(headerValueCaptor.getValue(), equalTo(nestedAuthKey));
    }

    @Test
    public void addAuthKeyNullTest() throws IOException {
        final String authKey = "123456789";

        AuthorizationKeyStorage.setAuthorizationKey(authKey);
        AuthorizationKeyStorage.setRootAuthorizationKey(null);

        final RootAuthKeyProvider filter = new RootAuthKeyProvider();

        final ClientRequestContext requestContext = Mockito.mock(ClientRequestContext.class);
        final MultivaluedMap<String, Object> headers = Mockito.mock(MultivaluedMap.class);

        Mockito.when(requestContext.getHeaders())
            .thenReturn(headers);

        filter.filter(requestContext);

        verify(headers, Mockito.never()).add(Mockito.any(), Mockito.any());
    }

}
