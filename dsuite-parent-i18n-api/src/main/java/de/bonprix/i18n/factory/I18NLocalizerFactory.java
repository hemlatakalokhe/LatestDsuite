package de.bonprix.i18n.factory;

import de.bonprix.i18n.localizer.I18NLocalizer;

/**
 * Interface definition for i18n localizer factory
 */
@FunctionalInterface
public interface I18NLocalizerFactory {

	/**
	 * creates an instance of the type @I18NLocalizer
	 * 
	 * @return an instance of the type @I18NLocalizer
	 */
	public I18NLocalizer createI18NLocalizer();

}
