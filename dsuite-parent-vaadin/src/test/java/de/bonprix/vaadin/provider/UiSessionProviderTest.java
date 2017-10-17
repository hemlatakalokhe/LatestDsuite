package de.bonprix.vaadin.provider;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.net.URI;

import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bonprix.StaticMethodAwareUnitTest;

@PrepareForTest({ UI.class, Page.class, VaadinSession.class })
public class UiSessionProviderTest extends StaticMethodAwareUnitTest {

    @InjectMocks
    private UiSessionProviderImpl uiSessionProviderImpl;

    private UI mockUI;
    private VaadinSession mockSession;
    private WrappedSession mockWrappedSession;
    private Page mockPage;

    @BeforeMethod
    public void init() {
        this.mockUI = PowerMockito.mock(UI.class);
        this.mockPage = PowerMockito.mock(Page.class);
        this.mockSession = PowerMockito.mock(VaadinSession.class);
        this.mockWrappedSession = PowerMockito.mock(WrappedSession.class);

        mockStatic(UI.class);
        when(UI.getCurrent()).thenReturn(this.mockUI);
        mockStatic(Page.class);
        when(Page.getCurrent()).thenReturn(this.mockPage);
        mockStatic(VaadinSession.class);
        when(VaadinSession.getCurrent()).thenReturn(this.mockSession);
        when(this.mockSession.getSession()).thenReturn(this.mockWrappedSession);

        ReflectionTestUtils.setField(this.uiSessionProviderImpl, "applicationId", 42L);
        ReflectionTestUtils.setField(this.uiSessionProviderImpl, "logoutUrl", "http://user-ws/logout");
    }

    @Test
    public void testLogoutSimple() throws Exception {
        testLogout("http://unittest.bonprix.net/sometest", "http://user-ws/logout?redirectUrl=http%3A%2F%2Funittest.bonprix.net%2Fsometest&applicationId=42");
    }

    @Test
    public void testLogoutWithOneParam() throws Exception {
        testLogout("http://unittest.bonprix.net/sometest?foo=1",
                   "http://user-ws/logout?redirectUrl=http%3A%2F%2Funittest.bonprix.net%2Fsometest%3Ffoo%3D1&applicationId=42");
    }

    @Test
    public void testLogoutWithTwoParam() throws Exception {
        testLogout("http://unittest.bonprix.net/sometest?foo=1&bar=2",
                   "http://user-ws/logout?redirectUrl=http%3A%2F%2Funittest.bonprix.net%2Fsometest%3Ffoo%3D1%26bar%3D2&applicationId=42");
    }

    public void testLogout(final String currentUrl, final String expectedRedirectUrl) throws Exception {
        when(this.mockPage.getLocation()).thenReturn(new URI(currentUrl));

        this.uiSessionProviderImpl.logout();

        verify(this.mockUI).close();
        verify(this.mockWrappedSession).invalidate();
        verify(this.mockPage).setLocation(expectedRedirectUrl);
    }

}
