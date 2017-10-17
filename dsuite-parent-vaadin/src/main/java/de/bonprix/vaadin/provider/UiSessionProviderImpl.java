/**
 *
 */
package de.bonprix.vaadin.provider;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;

import de.bonprix.exception.MailEncodingException;

/**
 * @author cthiel
 * @date 18.11.2016
 *
 */
@Component
@UIScope
public class UiSessionProviderImpl implements UiSessionProvider {

	@Value("${application.id}")
	private Long applicationId;

	@Value("${user.ws.url.logout}")
	private String logoutUrl;

	@Override
	public void logout() {
		UI.getCurrent()
			.close();
		VaadinSession.getCurrent()
			.getSession()
			.invalidate();
		Page.getCurrent()
			.setLocation(createLogoutUrl());
	}

	private String createLogoutUrl() {
		final String currentRequestUrl = Page.getCurrent()
			.getLocation()
			.toString();

		final UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(currentRequestUrl));
		uriBuilder.fragment(null);
		return appendRequestUrl(currentRequestUrl, this.logoutUrl);
	}

	private String appendRequestUrl(final String currentRequestUrl, final String baseUrl) {
		final UriBuilder uriBuilder = UriBuilder.fromUri(URI.create(baseUrl));
		try {
			uriBuilder.queryParam("redirectUrl", URLEncoder.encode(currentRequestUrl, "UTF-8"));
			uriBuilder.queryParam("applicationId", this.applicationId);
		} catch (final UnsupportedEncodingException e) {
			throw new MailEncodingException("Could not url encode RedirectUrl", e);
		}
		return uriBuilder.build()
			.toString();

	}

}
