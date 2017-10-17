/**
 *
 */
package de.bonprix.vaadin.layout.burgermenu;

import java.util.List;

import de.bonprix.dto.masterdata.SimpleLanguage;

/**
 * @author cthiel
 * @date 17.11.2016
 *
 */
public interface BpBurgerMenu {

	/**
	 * @param languages
	 */
	void setLanguages(List<SimpleLanguage> languages);

	void setHandoutItemEnabled(boolean enabled);

	void setConfluenceItemEnabled(boolean enabled);

	void setAdminItemsVisible(boolean visible);

	void refreshThemeItemChildren(String themeName);

}
