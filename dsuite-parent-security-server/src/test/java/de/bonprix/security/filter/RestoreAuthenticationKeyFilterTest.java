/**
 *
 */
package de.bonprix.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bonprix.security.AuthorizationKeyStorage;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class RestoreAuthenticationKeyFilterTest {

    @Test
    public void testFilterBothKeysSet() throws IOException, ServletException {
        // general mockings
        final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);
        final HttpSession httpSession = Mockito.mock(HttpSession.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpRequest.getSession(Mockito.anyBoolean())).thenReturn(httpSession);

        // test specific mockings
        final String authKey = "1234567890";
        final String rootAuthKey = "qwertzuio";

        when(httpSession.getAttribute(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);
        when(httpSession.getAttribute(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(rootAuthKey);

        final RestoreAuthenticationKeyFilter filter = new RestoreAuthenticationKeyFilter();

        filter.doFilter(httpRequest, httpResponse, chain);

        assertThat(AuthorizationKeyStorage.getAuthorizationKey(), equalTo(authKey));
        assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), equalTo(rootAuthKey));
        verify(chain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testFilterAuthKeySet() throws IOException, ServletException {
        // general mockings
        final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);
        final HttpSession httpSession = Mockito.mock(HttpSession.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpRequest.getSession(Mockito.anyBoolean())).thenReturn(httpSession);

        // test specific mockings
        final String authKey = "1234567890";
        final String rootAuthKey = "qwertzuio";

        when(httpSession.getAttribute(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(authKey);

        final RestoreAuthenticationKeyFilter filter = new RestoreAuthenticationKeyFilter();

        filter.doFilter(httpRequest, httpResponse, chain);

        assertThat(AuthorizationKeyStorage.getAuthorizationKey(), equalTo(authKey));
        assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), equalTo(authKey));
        verify(chain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testFilterNoKeySet() throws IOException, ServletException {
        // general mockings
        final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);
        final HttpSession httpSession = Mockito.mock(HttpSession.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpRequest.getSession(Mockito.anyBoolean())).thenReturn(httpSession);

        final RestoreAuthenticationKeyFilter filter = new RestoreAuthenticationKeyFilter();

        filter.doFilter(httpRequest, httpResponse, chain);

        assertThat(AuthorizationKeyStorage.getAuthorizationKey(), nullValue());
        assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), nullValue());
        verify(chain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void testFilterNestedAuthKeySet() throws IOException, ServletException {
        // general mockings
        final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);
        final HttpSession httpSession = Mockito.mock(HttpSession.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpRequest.getSession(Mockito.anyBoolean())).thenReturn(httpSession);

        // test specific mockings
        final String authKey = "1234567890";
        final String nestedAuthKey = "qwertzuio";

        when(httpSession.getAttribute(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE)).thenReturn(nestedAuthKey);

        // the test
        final RestoreAuthenticationKeyFilter filter = new RestoreAuthenticationKeyFilter();

        filter.doFilter(httpRequest, httpResponse, chain);

        assertThat(AuthorizationKeyStorage.getAuthorizationKey(), nullValue());
        assertThat(AuthorizationKeyStorage.getRootAuthorizationKey(), nullValue());
        verify(chain, times(1)).doFilter(httpRequest, httpResponse);
    }

}
