package de.bonprix.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.builder.SimpleLanguageBuilder;
import de.bonprix.i18n.factory.I18NLocalizerFactory;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.i18n.localizer.I18NLocalizer;
import de.bonprix.spring.ConfigurationProfile;

/**
 * Configuration for i18n support used in unit tests
 */
@Configuration
@Profile(ConfigurationProfile.UNITTEST)
public class I18NTestConfiguration {

	@Bean
	public I18NLocalizerFactory i18NLocalizerFactory() {

		return new I18NLocalizerFactory() {

			@Override
			public I18NLocalizer createI18NLocalizer() {
				return new TestI18NLocalizer();
			}

		};
	}

	@Bean
	public MethodInvokingFactoryBean i18NLocalizerMethodInvokingFactoryBean(
			final I18NLocalizerFactory i18NLocalizerFactory) {
		MethodInvokingFactoryBean result = new MethodInvokingFactoryBean();
		result.setStaticMethod("de.bonprix.I18N.setI18NLocalizerFactory");
		Object[] newObjs = new Object[1];
		newObjs[0] = i18NLocalizerFactory;
		result.setArguments(newObjs);
		return result;
	}

	private class TestI18NLocalizer implements I18NLocalizer {

		public final Logger logger = LoggerFactory.getLogger(TestI18NLocalizer.class);

		private final Map<String, String> keyValues = new HashMap<>();

		public TestI18NLocalizer() {
			try {
				Properties prop = new Properties();
				InputStream input = this.getClass()
					.getResourceAsStream("/i18n/test_i18_translations.properties");
				if (input != null) {
					prop.load(input);

					for (Object key : prop.keySet()) {
						String keyAsStr = (String) key;
						this.keyValues.put(keyAsStr, prop.getProperty(keyAsStr));
					}
				}

			} catch (IOException e) {
				this.logger.error(e.getLocalizedMessage(), e);
			}
		}

		@Override
		public String get(final String key, final Object... objects) {
			if (this.keyValues.containsKey(key)) {
				return this.keyValues.get(key);
			}
			return null;
		}

		@Override
		public String get(final String key, final SimpleLanguage language, final Object... objects) {
			return key;
		}

		@Override
		public <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
				CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function) {
			return "NO_TRANSLATION_FOR_CURRENT_LANGUAGE";
		}

		@Override
		public <CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> String get(
				CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function, Long languageId) {
			return "NO_TRANSLATION_FOR_LANGUAGE";
		}

		@Override
		public SimpleLanguage getCurrentLanguage() {
			return new SimpleLanguageBuilder().withId(301L)
				.withIsoShort("en")
				.build();
		}

		@Override
		public void reloadLocalizeRepo() {
		}

		@Override
		public Collection<SimpleLanguage> getAvailableLanguages() {
			return new ArrayList<>(Arrays.asList(getCurrentLanguage()));
		}
	}

}
