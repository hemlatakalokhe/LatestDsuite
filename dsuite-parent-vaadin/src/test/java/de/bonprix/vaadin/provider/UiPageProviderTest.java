package de.bonprix.vaadin.provider;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.internal.mockcreation.MockCreator.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import com.vaadin.util.ReflectTools;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.vaadin.mail.MailPopupConfiguration;
import de.bonprix.vaadin.provider.UiPageProviderImpl;

@PrepareForTest({ UI.class, Page.class, URI.class, UiPageProviderImpl.class })
public class UiPageProviderTest extends StaticMethodAwareUnitTest {

	private UI mockUI;
	private Page mockPage;

	@InjectMocks
	private UiPageProviderImpl uiPageProvider;

	@BeforeMethod
	public void init() {
		this.mockUI = PowerMockito.mock(UI.class);
		this.mockPage = PowerMockito.mock(Page.class);

		mockStatic(UI.class);
		when(UI.getCurrent()).thenReturn(this.mockUI);
		when(this.mockUI.getPage()).thenReturn(this.mockPage);
	}

	@Test
	public void testOpenDefaultMailClient() {
		MailPopupConfiguration mockMailPopupConfiguration = Mockito.mock(MailPopupConfiguration.class);
		Mockito	.when(mockMailPopupConfiguration.getUrl())
				.thenReturn("testUrl");

		this.uiPageProvider.openDefaultMailClient(mockMailPopupConfiguration);
		Mockito	.verify(this.mockPage)
				.open("testUrl", null);
	}

	@Test
	public void testGetBaseUrlPort80() throws URISyntaxException {
		final int port = 80;

		final URI uri = new URI("https://dsuite.bonprix.net:" + port + "/");
		when(this.mockPage.getLocation()).thenReturn(uri);

		// test method call
		final String url = UiPageProviderImpl.getBaseUrl();

		assertEquals(uri.getPort(), port);
		assertTrue(!url.contains(String.valueOf(port)));
	}

	@Test
	public void testGetBaseUrlPort443() throws URISyntaxException {
		final int port = 80;

		final URI uri = new URI("https://dsuite.bonprix.net:" + port + "/");
		when(this.mockPage.getLocation()).thenReturn(uri);

		// test method call
		final String url = UiPageProviderImpl.getBaseUrl();

		assertEquals(uri.getPort(), port);
		assertTrue(!url.contains(String.valueOf(port)));
	}

	@Test
	public void testGetBaseUrlPort8080() throws URISyntaxException {
		final int port = 8080;

		final URI uri = new URI("https://dsuite.bonprix.net:" + port + "/");
		when(this.mockPage.getLocation()).thenReturn(uri);

		// test method call
		final String url = UiPageProviderImpl.getBaseUrl();

		assertEquals(uri.getPort(), port);
		assertTrue(url.contains(String.valueOf(port)));
	}

	@Test
	public void testGetBaseUrlPortUndefined() throws URISyntaxException {
		final int port = -1;
		final URI uri = new URI("https://dsuite.bonprix.net/");
		when(this.mockPage.getLocation()).thenReturn(uri);

		// test method call
		final String url = UiPageProviderImpl.getBaseUrl();

		assertEquals(uri.getPort(), port);
		assertTrue(!url.contains(String.valueOf(port)));
	}

	@Test
	public void testGetBaseUrlUrlEnding() throws URISyntaxException {
		final URI uri = new URI("https://dsuite.bonprix.net");
		when(this.mockPage.getLocation()).thenReturn(uri);

		// test method call
		final String url = UiPageProviderImpl.getBaseUrl();

		assertEquals(url.charAt(url.length() - 1), '/');
	}

	@Test
	public void testRedirectToWithBaseUrl() {
		final String baseUrl = "https://dsuite.bonprix.net/";
		final String redirectToUrl = "?someParam=SomeValue";
		final String fragment = "someFragment";

		// only mocks UrlHelper.getaseUrl()
		final Method methodRedirectTo = ReflectTools.findMethod(UiPageProviderImpl.class, "getBaseUrl");
		mock(UiPageProviderImpl.class, true, false, null, null, (Method[]) Arrays	.asList(methodRedirectTo)
																					.toArray());
		when(UiPageProviderImpl.getBaseUrl()).thenReturn(baseUrl);
		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		this.uiPageProvider.localRedirect(redirectToUrl);

		PowerMockito.verifyStatic();
		UiPageProviderImpl.getBaseUrl();

		verify(this.mockPage, Mockito.times(2)).getUriFragment();
		verify(this.mockPage).setLocation(baseUrl + redirectToUrl + "#" + fragment);
	}

	@Test
	public void testRedirectToWithoutBaseUrl() {
		final String url = "https://dsuite.bonprix.net/";

		// test method call
		this.uiPageProvider.redirect(url);

		verify(this.mockPage).setLocation(url);
	}

	@Test
	public void testSetExtraFragmentParameterWithFragment() {
		final String parameter = "parameter";
		final String fragment = "!someFragment";

		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		this.uiPageProvider.setExtraFragmentParameter(parameter);

		verify(this.mockPage).setUriFragment(Mockito.eq(fragment + "/" + parameter));
	}

	@Test
	public void testSetExtraFragmentParameterWithoutFragment() {
		final String parameter = "parameter";
		final String fragment = "fragmentIsOnlyAParameter";

		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		this.uiPageProvider.setExtraFragmentParameter(parameter);

		verify(this.mockPage).setUriFragment(Mockito.eq(parameter));
	}

	@Test
	public void testGetExtraFragmentParameterWithParameter() {
		final String parameter = "parameter";
		final String fragment = "!someFragment/" + parameter;

		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		final String extraFragmentParameter = this.uiPageProvider.getExtraFragmentParameter();

		assertEquals(extraFragmentParameter, parameter);

		verify(this.mockPage).getUriFragment();
	}

	@Test
	public void testGetExtraFragmentParameterWithoutParameter() {
		final String fragment = "!someFragment";

		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		final String extraFragmentParameter = this.uiPageProvider.getExtraFragmentParameter();

		assertEquals(extraFragmentParameter, null);

		verify(this.mockPage).getUriFragment();
	}

	@Test
	public void testGetExtraFragmentParameterWithoutFragment() {
		final String fragment = "fragmentIsOnlyAParameter";

		when(this.mockPage.getUriFragment()).thenReturn(fragment);

		// test method call
		final String extraFragmentParameter = this.uiPageProvider.getExtraFragmentParameter();

		assertEquals(extraFragmentParameter, fragment);

		verify(this.mockPage).getUriFragment();
	}

}
