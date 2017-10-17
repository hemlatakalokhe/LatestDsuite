package de.bonprix.vaadin.layout;

import com.vaadin.ui.Layout;

/**
 * Interface to for the application layout having a main content and a menu.
 */
public interface BpApplicationLayout {

	/**
	 * @return the layout to be used for the main content.
	 */
	Layout getMainContent();

	/**
	 * refreshes the menu of the application.
	 */
	void refreshMenu(String viewName);

}
