package de.bonprix.vaadin.provider;

import java.util.Collection;

import de.bonprix.dto.masterdata.SimpleLanguage;

/**
 * A provider for accessing language data and modifying the current language.
 * 
 * @author cthiel
 * @date 22.11.2016
 *
 */
public interface UiLanguageProvider {

	/**
	 * Switches the current user session language to the given language. This
	 * will perform both a session change and also a complete reload of the
	 * Vaadin UI. So all beans or data scoped to UI or View are lost!
	 *
	 * @param language
	 *            the new language to switch to
	 */
	void switchLanguage(SimpleLanguage language);

	/**
	 * Returns a list of all languages available for this application.
	 *
	 * @return all languages of this application
	 */
	Collection<SimpleLanguage> getAvailableLanguages();

	/**
	 * Returns the current session's language.
	 *
	 * @return the current session's language
	 */
	SimpleLanguage getCurrentLanguage();

}