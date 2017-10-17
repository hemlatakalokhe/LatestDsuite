package de.bonprix.vaadin.theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Hold's available Themes and allow to register application specific themes
 *
 * @author marten
 *
 */
public final class VaadinThemesDirectory {

	private static final String DARK_THEME = "bonprix-dark";
	private static final String BLUE_THEME = "bonprix-blue";

	public static final String THEME_COOKIE_NAME = "dsuite-parent-vaadin-theme";
	public static final String DEFAULT_THEME = VaadinThemesDirectory.DARK_THEME;

	private static final Map<String, String> THEMES = new HashMap<>();

	static {
		VaadinThemesDirectory.THEMES.put(VaadinThemesDirectory.DARK_THEME, "Dark Edition");
		VaadinThemesDirectory.THEMES.put(VaadinThemesDirectory.BLUE_THEME, "Blue Edition");
	}

	private VaadinThemesDirectory() {

	}

	/**
	 * 
	 * @param path
	 *            foldername below VAADIN
	 * @param name
	 *            used to display in dropdown
	 */
	public static final void addTheme(final String path, final String name) {
		VaadinThemesDirectory.THEMES.put(path, name);
	}

	public static Map<String, String> getThemes() {
		return VaadinThemesDirectory.THEMES;
	}

}
