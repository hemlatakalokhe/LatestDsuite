/**
 *
 */
package de.bonprix.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author cthiel
 * @date 07.10.2016
 *
 */
public class BonprixStripAuthKeySuccessHandlerTest {

    @Test
    public void redirectRemoveAuthKeyTest() throws Exception {
        testRedirectWithoutAuthKey("http://unittest/test?authKey=1234567890", "/test", "http://unittest/test/");
    }

    @Test
    public void redirectRemoveAuthKeyWithSlashTest() throws Exception {
        testRedirectWithoutAuthKey("http://unittest/test/?authKey=1234567890", "/test", "http://unittest/test/");
    }

    @Test
    public void redirectNoAuthKeyTest() throws Exception {
        testRedirectWithoutAuthKey("http://unittest/test", "/test", "http://unittest/test/");
    }

    @Test
    public void redirectRemoveAuthKeyWithParamTest() throws Exception {
        testRedirectWithoutAuthKey("http://unittest/test?foo=bar&authKey=1234567890", "/test", "http://unittest/test/?foo=bar");
    }

    private void testRedirectWithoutAuthKey(final String requestUrl, final String requestUri, final String excpectedRedirectUrl) throws Exception {
        final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        final HttpSession httpSession = mock(HttpSession.class);
        final HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer(requestUrl));
        when(httpRequest.getRequestURI()).thenReturn(requestUri);

        final BonprixAuthentication authentication = Mockito.mock(BonprixAuthentication.class);

        when(authentication.getAuthKey()).thenReturn("qwertzuio");
        when(authentication.getRootAuthKey()).thenReturn("1234567890");

        final BonprixStripAuthKeySuccessHandler handler = new BonprixStripAuthKeySuccessHandler();

        handler.onAuthenticationSuccess(httpRequest, httpResponse, authentication);

        verify(httpResponse, Mockito.times(1)).sendRedirect(excpectedRedirectUrl);
        verify(httpSession).setAttribute(AuthorizationKeyStorage.AUTHENTICATION_KEY_ATTRIBUTE, "qwertzuio");
        verify(httpSession).setAttribute(AuthorizationKeyStorage.ROOT_AUTHENTICATION_KEY_ATTRIBUTE, "1234567890");
    }

}
