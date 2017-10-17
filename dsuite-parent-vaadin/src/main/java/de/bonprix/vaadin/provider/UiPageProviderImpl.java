/**
 *
 */
package de.bonprix.vaadin.provider;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.vaadin.server.Page;
import com.vaadin.spring.annotation.UIScope;

import de.bonprix.vaadin.mail.MailPopupConfiguration;

/**
 * @author cthiel
 * @date 17.11.2016
 *
 */
@Component
@UIScope
public class UiPageProviderImpl implements UiPageProvider {

	@Override
	public void openDefaultMailClient(final MailPopupConfiguration mailPopupConfiguration) {
		Page.getCurrent()
			.open(mailPopupConfiguration.getUrl(), null);
	}

	@Override
	public void redirect(final String url) {
		Page.getCurrent()
			.setLocation(url);
	}

	@Override
	public void openInNewTab(final String url) {
		Page.getCurrent()
			.open(url, "_blank");
	}

	@Override
	public void localRedirect(final String queryString) {
		Page.getCurrent()
			.setLocation(getBaseUrl() + queryString + (Page.getCurrent()
				.getUriFragment() != null
						? "#" + Page.getCurrent()
							.getUriFragment()
						: ""));
	}

	@Override
	public void reload() {
		Page.getCurrent()
			.reload();
	}

	@Override
	public void setExtraFragmentParameter(final String parameter) {
		final String fragment = Page.getCurrent()
			.getUriFragment();
		if (fragment != null && fragment.startsWith("!")) {
			final String[] splitted = fragment.split("\\/");
			Page.getCurrent()
				.setUriFragment(String.format("%s/%s", splitted[0], parameter));
		} else {
			Page.getCurrent()
				.setUriFragment(parameter);
		}
	}

	@Override
	public String getExtraFragmentParameter() {
		final String fragment = Page.getCurrent()
			.getUriFragment();
		if (fragment != null && fragment.startsWith("!")) {
			final String[] splitted = fragment.split("\\/", 2);
			if (splitted.length == 2) {
				return splitted[1];
			} else {
				return null;
			}
		} else {
			return StringUtils.isEmpty(fragment) ? null : fragment;
		}
	}

	/**
	 * gets the base url of the java application server running
	 *
	 * @return base url
	 */
	static final String getBaseUrl() {
		final URI uri = Page.getCurrent()
			.getLocation();

		String url = null;
		if ((uri.getPort() == 80) || (uri.getPort() == 443) || (uri.getPort() <= 0)) {
			url = uri.getScheme() + "://" + uri.getHost() + uri.getPath();
		} else {
			url = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();
		}

		if (!url.endsWith("/")) {
			url += "/";
		}

		return url;
	}

}
