package de.bonprix.vaadin.spring;

import java.util.Iterator;
import java.util.Map.Entry;

import com.vaadin.server.UICreateEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.server.SpringUIProvider;
import com.vaadin.ui.UI;

import de.bonprix.vaadin.admin.VaadinApplicationInformation;
import de.bonprix.vaadin.theme.VaadinThemesDirectory;
import de.bonprix.vaadin.util.ThemeHelper;

/**
 * Subclass of the Vaadin {@link SpringUIProvider}. Helps us to include our own
 * Theme cookie reader. The UI classes must be annotated with
 * {@link com.vaadin.spring.annotation.SpringUI}.
 */

public class BpUIProvider extends SpringUIProvider {
	private static final long serialVersionUID = 1L;

	public BpUIProvider(final VaadinSession vaadinSession) {
		super(vaadinSession);

		// creating all directories for our themes for on the fly sass
		// compilation
		final Iterator<Entry<String, String>> iterator = VaadinThemesDirectory	.getThemes()
																				.entrySet()
																				.iterator();
		while (iterator.hasNext()) {
			final Entry<String, String> entry = iterator.next();
			// optionally create a directory for cached themes
			createThemeDirectory(entry.getKey());
		}
	}

	/**
	 * Loads the theme from annotation, if not set load from cookie or finally
	 * use default theme.
	 */
	@Override
	public String getTheme(final UICreateEvent event) {
		final String uiTheme = super.getTheme(event);
		if (uiTheme != null) {
			return uiTheme;
		}

		return ThemeHelper.getTheme();
	}

	@Override
	public UI createInstance(final UICreateEvent event) {
		final UI ui = super.createInstance(event);

		final VaadinApplicationInformation appInfo = getWebApplicationContext().getBean(VaadinApplicationInformation.class);
		ui.addAttachListener(appInfo);
		ui.addDetachListener(appInfo);

		return ui;
	}

}
