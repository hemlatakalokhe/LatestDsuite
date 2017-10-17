package de.bonprix;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.i18n.dto.SimpleI18NKey;
import de.bonprix.i18n.dto.SimpleI18NTranslation;
import de.bonprix.i18n.service.SimpleApplicationLanguageService;
import de.bonprix.i18n.service.SimpleI18NKeyService;
import de.bonprix.i18n.service.fetch.builder.SimpleI18NKeyFetchOptionsBuilder;
import de.bonprix.i18n.service.filter.builder.SimpleI18NKeyFilterBuilder;
import de.bonprix.information.ApplicationProvider;

/**
 * Localize repository class, holding all informations about translations and
 * languages for the current application
 */
public class LocalizeRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalizeRepo.class);

	private final SimpleI18NKeyService i18nKeyService;
	private final SimpleApplicationLanguageService applicationLanguageService;
	private final ApplicationProvider applicationProvider;

	private final Map<String, Set<SimpleI18NTranslation>> repo = new HashMap<String, Set<SimpleI18NTranslation>>(128);
	private SimpleLanguage defaultLanguage;
	private Map<Long, SimpleLanguage> availableLanguagesMap;

	public LocalizeRepo(SimpleI18NKeyService i18nKeyService,
			SimpleApplicationLanguageService applicationLanguageService, ApplicationProvider applicationProvider) {
		this.i18nKeyService = i18nKeyService;
		this.applicationLanguageService = applicationLanguageService;
		this.applicationProvider = applicationProvider;
		loadRepo();
	}

	public Collection<SimpleLanguage> getAvailableLanguages() {
		return this.availableLanguagesMap.values();
	}

	public SimpleLanguage getAvailableLanguageById(Long id) {
		return this.availableLanguagesMap.get(id);
	}

	public SimpleLanguage getDefaultLanguage() {
		return this.defaultLanguage;
	}

	private void loadRepo() {
		this.defaultLanguage = this.applicationLanguageService.getDefaultLanguage();
		this.availableLanguagesMap = new HashMap<>();
		for (SimpleLanguage simpleLanguage : this.applicationLanguageService
			.getLanuagesConnectedToAnApplication(this.applicationProvider.getApplication()
				.getId())) {
			this.availableLanguagesMap.put(simpleLanguage.getId(), simpleLanguage);
		}

		this.repo.clear();
		for (final SimpleI18NKey key : this.i18nKeyService.findAll(new SimpleI18NKeyFilterBuilder()
			.withApplicationIds(Arrays.asList(this.applicationProvider.getApplication()
				.getId()))
			.build(), new SimpleI18NKeyFetchOptionsBuilder().withFetchTranslations(true)
				.build())) {
			this.repo.put(key.getKey(), key.getTranslations());
		}
	}

	public void reload() {
		loadRepo();
	}

	private Set<SimpleI18NTranslation> getTranslations(String key) {
		return this.repo.get(key);
	}

	public boolean containsKey(String key) {
		return this.repo.containsKey(key);
	}

	/**
	 * Detector for the right translation string; is based on bonprix - intern
	 * conventions
	 * 
	 * This conventions are, in the descending order of their priority
	 * 
	 * 1) Standard case -> there is a translation for UI language & client id
	 * combination (if principal is not available bonprix client id) 3) There is
	 * a translation for bonprix client id & english locale 4) There is no
	 * translation - key is returned as translation
	 */
	public String getTranslatedKey(final String key, final SimpleLanguage language) {
		Set<SimpleI18NTranslation> translations = getTranslations(key);

		// 0. if no translations where found, just return key
		if (translations == null) {
			LocalizeRepo.LOGGER.info("No translations found for key: {}", key);
			return key;
		}

		// 1. Standard case -> there is a translation for UI language id
		// combination
		for (final SimpleI18NTranslation translation : translations) {
			if (language.getId()
				.equals(translation.getLanguageId())) {
				return translation.getTranslation();
			}
		}

		// default language is not current language
		if (!this.defaultLanguage.getId()
			.equals(language.getId())) {
			// 2. There is a translation for the default language id
			for (final SimpleI18NTranslation translation : translations) {
				if (this.defaultLanguage.getId()
					.equals(translation.getLanguageId())) {
					LocalizeRepo.LOGGER.info("Translation found for key and default language: {}", key);
					return translation.getTranslation();
				}
			}
		}

		// no other choice -> return key
		LocalizeRepo.LOGGER.info("No translation found for key and given language or default language: {}", key);
		return key;
	}

}
