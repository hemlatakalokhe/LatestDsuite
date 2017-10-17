package de.bonprix;

import java.util.Collection;

import javax.annotation.Resource;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.i18n.factory.I18NLocalizerFactory;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.i18n.localizer.I18NLocalizer;

/**
 * Entry point for i18n translation fetching
 */
public final class I18N {

	private static I18NLocalizer I18N_LOCALIZER;

	private static boolean initialized = false;

	private static I18NLocalizerFactory i18NLocalizerFactory;

	@Resource
	private static I18NLocalizer mockI18NLocalizer;

	public static void setI18NLocalizerFactory(I18NLocalizerFactory i18NLocalizerFactory) {
		de.bonprix.I18N.i18NLocalizerFactory = i18NLocalizerFactory;
	}

	/**
	 * Gets the i18n localizer, appropriate for the current environment
	 * (productive / test)
	 *
	 * @return an instance of @I18NLocalizer
	 */
	public static I18NLocalizer getI18NLocalizer() {
		initI18NLocalizer();

		return I18N_LOCALIZER;
	}

	/**
	 * reload translation repository by clearing and filling it from scratch
	 * from database
	 *
	 */
	public static void reloadI18NLocalizer() {
		if (!initI18NLocalizer()) {
			I18N_LOCALIZER.reloadLocalizeRepo();
		}
	}

	/**
	 * Initialisation of I18N Localizer if necessary
	 * 
	 * @return if I18N Localizer had to be initialised
	 */
	private static boolean initI18NLocalizer() {
		if (!initialized) {
			I18N_LOCALIZER = i18NLocalizerFactory.createI18NLocalizer();
			initialized = true;
			return true;
		}
		return false;
	}

	/**
	 * Retrieves the specified key from the i18n bundle in either the locale of
	 * the UI instance or the default locale of this i18n and applies the
	 * additional parameters to the string using String.format().
	 *
	 * @param key
	 *            the key
	 * @param objects
	 *            optional parameters for token replacement
	 * @return the formatted localized string
	 */
	public static String get(final String key, final Object... objects) {
		return getI18NLocalizer().get(key, objects);
	}

	/**
	 * Retrieves the specified key from the i18n bundle in the specified locale
	 * and applies the additional parameters to the string using
	 * String.format().
	 *
	 * @param key
	 *            the key
	 * @param language
	 *            language of wanted translation
	 * @param objects
	 *            optional parameters for token replacement
	 * @return the formatted localized string
	 */
	public static String get(final String key, final SimpleLanguage language, final Object... objects) {
		return getI18NLocalizer().get(key, language, objects);
	}

	/**
	 * Retrieves the formatted message for the specified key from the i18n
	 * bundle in either the locale of the UI instance or the default locale of
	 * this i18n and applies the additional parameters to the string using
	 * String.format().
	 *
	 * @param enumKey
	 *            the key; transformed to the lower case simple class and name
	 *            separated by a dot
	 * @param objects
	 *            optional parameters for token replacement
	 * @return the formatted localized string
	 */
	public static String get(final Enum<?> enumKey, final Object... objects) {
		final String key = enumKey.getClass()
			.getSimpleName()
			.toLowerCase() + '.'
				+ enumKey.name()
					.toLowerCase();
		return I18N.get(key, objects);
	}

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
	public static <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function) {
		return getI18NLocalizer().get(languageContainerEntity, function);
	}

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
	public static <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function,
			final SimpleLanguage language) {
		return getI18NLocalizer().get(languageContainerEntity, function, language.getId());
	}

	/**
	 * Retrieves the translation from the language container entity using the
	 * language and a function to get the correct property from the language id.
	 * 
	 * @param languageContainerEntity
	 *            containing the language entities
	 * @param function
	 *            providing the wanted property from the language entity
	 * @param languageId
	 *            wanted languageId for translation
	 * @return translated string
	 */
	public static <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function, final Long languageId) {
		return getI18NLocalizer().get(languageContainerEntity, function, languageId);
	}

	/**
	 * Returns a list of all available locales.
	 *
	 * @return a list of all available locales
	 */
	public static Collection<SimpleLanguage> getAvailableLanguages() {
		return getI18NLocalizer().getAvailableLanguages();
	}

	/**
	 * Retrieves the current language of the UI
	 *
	 * @return current language of the UI
	 */
	public static SimpleLanguage getCurrentLanguage() {
		return getI18NLocalizer().getCurrentLanguage();
	}

}
