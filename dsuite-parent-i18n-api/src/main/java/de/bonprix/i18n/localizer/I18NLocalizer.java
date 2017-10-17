package de.bonprix.i18n.localizer;

import java.util.Collection;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;

/**
 * Interface definition for i18n localizer
 */
public interface I18NLocalizer {

	/**
	 * retrieves translation string for a i18n key string
	 * 
	 * @param key
	 *            i18n key string
	 * @param objects
	 *            object array for a case, that i18n key string is formatted
	 *            via @String.format
	 * @return translation string
	 */
	String get(final String key, final Object... objects);

	/**
	 * retrieves translation string for a i18n key string and a locale
	 * 
	 * @param key
	 *            i18n key string
	 * @param locale
	 *            locale
	 * @param objects
	 *            object array for a case, that i18n key string is formatted
	 *            via @String.format
	 * @return translation string
	 */
	<ELEMENT extends I18NLanguageElement> String get(final String key, final SimpleLanguage language,
			final Object... objects);

	/**
	 * retrieves current language of the UI
	 * 
	 * @return current language of the UI
	 */
	SimpleLanguage getCurrentLanguage();

	/**
	 * get available languages for the application
	 * 
	 * @return available languages
	 */
	Collection<SimpleLanguage> getAvailableLanguages();

	/**
	 * reload translation repository by clearing and filling it from scratch
	 * from database
	 *
	 */
	void reloadLocalizeRepo();

	/**
	 * Retrieves the translation from the language container entity using the
	 * current language and a function to get the correct property from the
	 * language entity.
	 * 
	 * @param languageContainerEntity
	 *            containing the language entities
	 * @param function
	 *            providing the wanted property from the language entity
	 * @return translated string
	 */
	<CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function);

	/**
	 * Retrieves the translation from the language container entity using the
	 * language and a function to get the correct property from the language
	 * entity.
	 * 
	 * @param languageContainerEntity
	 *            containing the language entities
	 * @param function
	 *            providing the wanted property from the language entity
	 * @param language
	 *            wanted language for translation
	 * @return translated string
	 */
	<CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function, Long languageId);

}
