/**
 *
 */
package de.bonprix.vaadin.provider;

import de.bonprix.vaadin.mail.MailPopupConfiguration;

/**
 *
 * @author cthiel
 * @date 17.11.2016
 *
 */
public interface UiPageProvider {

	/**
	 * Performs a <code>mailto:</code> location change with the given
	 * mailPopupConfiguration. When the browser is configured correctly, the
	 * default mail client should startup.
	 *
	 * @param mailPopupConfiguration
	 *            the mail data
	 */
	void openDefaultMailClient(MailPopupConfiguration mailPopupConfiguration);

	/**
	 * Performs a HTTP redirect to the given absolute url location.
	 *
	 * @param url
	 *            the target URL.
	 */
	void redirect(String url);

	/**
	 * Opens a new tab (if supported by the browser) with location the given
	 * URL.
	 *
	 * @param url
	 *            the target URL.
	 */
	void openInNewTab(String url);

	/**
	 * Performa a local redirect to the given server-local query string. The
	 * current base URL of the Vaadin UI will be added to the queryString.
	 *
	 * @param queryString
	 */
	void localRedirect(String queryString);

	/**
	 * Perform a reload of the current page in the browser.
	 */
	void reload();

	/**
	 * takes care of existing navigationHash and removes this from fragment
	 * parameter
	 *
	 * @return null in case of not existing fragment
	 */
	String getExtraFragmentParameter();

	/**
	 * Takes care of existing navigationHash and adds a parameter after that via
	 * /
	 *
	 * @param parameter
	 */
	void setExtraFragmentParameter(String parameter);

}
