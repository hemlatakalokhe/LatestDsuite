package de.bonprix.vaadin.provider;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import java.net.URI;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import static org.mockito.Mockito.when;

import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.vaadin.provider.UiPageProviderImpl;

@PrepareForTest({ UI.class, Page.class, URI.class, UiPageProviderImpl.class })
public class UiNavigationProviderTest extends StaticMethodAwareUnitTest {

    private UI mockUI;
    private Navigator mockNavigator;

    @InjectMocks
    private UiNavigationProviderImpl uiNavigationProvider;

    @BeforeMethod
    public void init() {
        this.mockUI = PowerMockito.mock(UI.class);
        this.mockNavigator = Mockito.mock(Navigator.class);

        mockStatic(UI.class);
        when(UI.getCurrent()).thenReturn(this.mockUI);
        when(this.mockUI.getNavigator()).thenReturn(this.mockNavigator);
    }

    @Test
    public void testNavigateToValid() {
        final String viewName = "VIEW_1";

        // test method call
        this.uiNavigationProvider.navigateTo(viewName);

        Mockito.verify(this.mockNavigator)
            .navigateTo(viewName);
    }

    @Test(
        expectedExceptions = IllegalArgumentException.class)
    public void testNavigateToInvalid() {
        final String viewName = "VIEW_1";

        Mockito.doThrow(new IllegalArgumentException())
            .when(this.mockNavigator)
            .navigateTo(Mockito.anyString());

        // test method call
        this.uiNavigationProvider.navigateTo(viewName);
    }

}
