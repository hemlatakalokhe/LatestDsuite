package de.bonprix.vaadin.util;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

import de.bonprix.vaadin.theme.VaadinThemesDirectory;

/**
 * Helper for everything about a Vaadin Theme.
 */
public class ThemeHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ThemeHelper.class);

	/** changes theme to theme name */
	public static void changeTheme(final String themeName) {
		UI.getCurrent()
			.setTheme(themeName);

		final Cookie themeCookie = new Cookie(VaadinThemesDirectory.THEME_COOKIE_NAME, themeName);
		// Make cookie expire in 90 days
		themeCookie.setMaxAge(60 * 60 * 24 * 90);
		// Set the cookie path.
		themeCookie.setPath("/");
		// Save cookie
		VaadinService.getCurrentResponse()
			.addCookie(themeCookie);
	}

	/**
	 * Clears the theme cookie.
	 */
	public static void clearThemeCookie() {
		final Cookie themeCookie = new Cookie(VaadinThemesDirectory.THEME_COOKIE_NAME, "");
		themeCookie.setMaxAge(0);
		themeCookie.setPath("/");
		VaadinService.getCurrentResponse()
			.addCookie(themeCookie);
	}

	/**
	 * Reads theme name out of cookies
	 * 
	 * @return name of theme
	 */
	public static String readThemeCookie() {
		if (VaadinService.getCurrentRequest() != null && VaadinService.getCurrentRequest()
			.getCookies() != null) {
			for (final Cookie cookie : VaadinService.getCurrentRequest()
				.getCookies()) {
				if (VaadinThemesDirectory.THEME_COOKIE_NAME.equals(cookie.getName())) {
					return cookie.getValue() != null && !cookie.getValue()
						.isEmpty() ? cookie.getValue() : null;
				}
			}
		}
		return null;
	}

	/**
	 * Finds theme in cookie or uses default theme.
	 * 
	 * @return name of theme
	 */
	public static String getTheme() {
		String cookieTheme = ThemeHelper.readThemeCookie();
		if (cookieTheme != null) {

			// check if theme exists
			if (VaadinService.getCurrent() instanceof VaadinServletService) {
				final VaadinServletService servletService = (VaadinServletService) VaadinService.getCurrent();
				if (servletService.getServlet()
					.getServletContext()
					.getRealPath("/" + VaadinServlet.THEME_DIR_PATH + "/" + cookieTheme + "/") == null) {
					ThemeHelper.LOG.error(
											"cookie requested theme: {} but this doesn't exist in application - clear cookie",
											cookieTheme);
					ThemeHelper.clearThemeCookie();
				} else {
					return cookieTheme;
				}
			}

		}

		return VaadinThemesDirectory.DEFAULT_THEME;
	}

}
