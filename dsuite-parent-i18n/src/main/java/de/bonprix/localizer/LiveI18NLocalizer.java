package de.bonprix.localizer;

import java.text.MessageFormat;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.I18N;
import de.bonprix.LocalizeRepo;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;
import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.i18n.localizer.I18NLocalizer;
import de.bonprix.security.PrincipalSecurityContext;

/**
 * I18n localizer used in productive environment
 */
public class LiveI18NLocalizer implements I18NLocalizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(LiveI18NLocalizer.class);
	private static final Long LANGUAGE_ID_ENGLISH = 301L;

	private static LocalizeRepo localizeRepo;

	public static void setTranslationRepo(LocalizeRepo translationRepo) {
		de.bonprix.localizer.LiveI18NLocalizer.localizeRepo = translationRepo;
	}

	@Override
	public String get(final String key, final Object... objects) {
		if (!localizeRepo.containsKey(key)) {
			LiveI18NLocalizer.LOGGER.warn("No value for key {} in resource bundle", key);
			return key;
		}
		return get(key, getCurrentLanguage(), objects);
	}

	@Override
	public String get(final String key, final SimpleLanguage language, final Object... objects) {
		if (!localizeRepo.containsKey(key)) {
			LiveI18NLocalizer.LOGGER.warn(	"No value for key {} and language {} in resource bundle", key,
											I18N.get(language, SimpleLanguageLanguage::getName));
			return key;
		}

		final String translation = localizeRepo.getTranslatedKey(key, language);
		return MessageFormat.format(translation, objects);
	}

	@Override
	public void reloadLocalizeRepo() {
		localizeRepo.reload();
	}

	@Override
	public Collection<SimpleLanguage> getAvailableLanguages() {
		return localizeRepo.getAvailableLanguages();
	}

	@Override
	public SimpleLanguage getCurrentLanguage() {
		try {
			Long languageId = PrincipalSecurityContext.getRootPrincipal()
				.getLanguageId();
			SimpleLanguage currentLanguage = localizeRepo.getAvailableLanguageById(languageId);
			if (currentLanguage != null) {
				return currentLanguage;
			}
		} catch (PrincipalMissingException e) {
			LOGGER.info(e.getLocalizedMessage() + " Using default language.", e);
			return localizeRepo.getDefaultLanguage();
		}
		return localizeRepo.getDefaultLanguage();
	}

	@Override
	public <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function) {
		return get(languageContainerEntity, function, getCurrentLanguage().getId());
	}

	@Override
	public <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function, Long languageId) {
		ELEMENT element = languageContainerEntity.getLanguageElement(languageId);

		if (element == null) {
			return MessageFormat.format("missing translation for id: {0}", languageContainerEntity.getId());
		}

		return function.apply(element);
	}

}
