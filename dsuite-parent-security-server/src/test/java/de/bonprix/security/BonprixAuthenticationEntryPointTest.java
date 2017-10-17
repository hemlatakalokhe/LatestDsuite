/**
 *
 */
package de.bonprix.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author cthiel
 * @date 01.11.2016
 *
 */
public class BonprixAuthenticationEntryPointTest {

    @Test
    public void testSimpleRedirect() throws Exception {
        testRedirect("http://dfrog.bonprix.net/", null, "https://dsuite.net/ui/login?redirectUrl=http%3A%2F%2Fdfrog.bonprix.net%2F&applicationId=42");
    }

    @Test
    public void testRedirectWithOneParameter() throws Exception {
        testRedirect("http://dfrog.bonprix.net/", "foo=bar",
                     "https://dsuite.net/ui/login?redirectUrl=http%3A%2F%2Fdfrog.bonprix.net%2F%3Ffoo%3Dbar&applicationId=42");
    }

    @Test
    public void testRedirectWithTwoParameters() throws Exception {
        testRedirect("http://dfrog.bonprix.net/", "foo=bar&some=other",
                     "https://dsuite.net/ui/login?redirectUrl=http%3A%2F%2Fdfrog.bonprix.net%2F%3Ffoo%3Dbar%26some%3Dother&applicationId=42");
    }

    private void testRedirect(final String requestUrl, final String queryString, final String excpectedUrl) throws Exception {
        final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse httpResponse = Mockito.mock(HttpServletResponse.class);

        final Long applicationId = 42L;
        final String loginUrl = "https://dsuite.net/ui/login";
        final AuthenticationException exception = new RememberMeAuthenticationException("");

        when(httpResponse.encodeRedirectURL(Mockito.anyString())).thenAnswer(invocation -> invocation.getArgumentAt(0, String.class));
        when(httpRequest.getRequestURL()).thenReturn(new StringBuffer(requestUrl));
        when(httpRequest.getQueryString()).thenReturn(queryString);

        final BonprixAuthenticationEntryPoint entryPoint = new BonprixAuthenticationEntryPoint(applicationId, loginUrl);

        entryPoint.commence(httpRequest, httpResponse, exception);

        verify(httpResponse, times(1)).sendRedirect(excpectedUrl);

    }

}
