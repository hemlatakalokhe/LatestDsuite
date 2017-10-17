package de.bonprix;

import java.util.Arrays;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;
import de.bonprix.dto.masterdata.builder.SimpleLanguageBuilder;
import de.bonprix.i18n.factory.I18NLocalizerFactory;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.i18n.localizer.I18NLocalizer;

public class I18NTest {

	private I18NLocalizer mockI18NLocalizer = Mockito.mock(I18NLocalizer.class);
	private I18NLocalizerFactory mockI18NLocalizerFactory = Mockito.mock(I18NLocalizerFactory.class);

	private enum TestEnum {
		VALUE1, VALUE2
	}

	@BeforeClass
	public void beforeClass() {
		I18N.setI18NLocalizerFactory(this.mockI18NLocalizerFactory);
		Mockito.when(this.mockI18NLocalizerFactory.createI18NLocalizer())
			.thenReturn(this.mockI18NLocalizer);
	}

	@Test
	public void getI18NLocalizerTest() {
		// 1. localizer is not initialize - do that
		I18NLocalizer actual = I18N.getI18NLocalizer();

		Assert.assertSame(actual, this.mockI18NLocalizer);

		// 2 call the test method again -> localizer is already initialized ->
		// the setted one in the previous step should be returned
		Mockito.reset(this.mockI18NLocalizerFactory);

		I18NLocalizer actual2 = I18N.getI18NLocalizer();

		Mockito.verify(this.mockI18NLocalizerFactory, Mockito.never())
			.createI18NLocalizer();

		Assert.assertSame(actual2, this.mockI18NLocalizer);
	}

	@Test
	public void reloadI18NLocalizerTest() {
		I18N.getI18NLocalizer();
		I18N.reloadI18NLocalizer();

		Mockito.verify(this.mockI18NLocalizer)
			.reloadLocalizeRepo();
	}

	@Test
	public void shortGetTest() {
		String key = "testKey";
		Object args = Arrays.asList("arg1");

		I18N.get(key, args);

		Mockito.verify(this.mockI18NLocalizer)
			.get(key, args);
	}

	@Test
	public void longGetTest() {
		String key = "testKey";
		SimpleLanguage language = new SimpleLanguage();
		Object args = Arrays.asList("arg1");

		I18N.get(key, language, args);

		Mockito.verify(this.mockI18NLocalizer)
			.get(key, language, args);
	}

	@Test
	public void shortGetLanguageTranslationTest() {
		SimpleLanguage languageContainer = new SimpleLanguage();
		I18NLanguageElementFunction<SimpleLanguageLanguage> function = languageLanguage -> languageLanguage.getName();

		I18N.get(languageContainer, function);

		Mockito.verify(this.mockI18NLocalizer)
			.get(languageContainer, function);
	}

	@Test
	public void longGetLanguageTranslationTest() {
		Long languageId = 999L;
		SimpleLanguage languageContainer = new SimpleLanguage();
		I18NLanguageElementFunction<SimpleLanguageLanguage> function = languageLanguage -> languageLanguage.getName();
		SimpleLanguage language = new SimpleLanguageBuilder().withId(languageId)
			.build();

		I18N.get(languageContainer, function, language);

		Mockito.verify(this.mockI18NLocalizer)
			.get(languageContainer, function, language.getId());
	}

	@Test
	public void getAvailableLanguagesTest() {
		I18N.getAvailableLanguages();

		Mockito.verify(this.mockI18NLocalizer)
			.getAvailableLanguages();
	}

	@Test
	public void enumParamGetTest() {
		TestEnum key = TestEnum.VALUE1;
		Object[] args = new Object[1];

		I18N.get(key, args);
		Mockito.verify(this.mockI18NLocalizer)
			.get("testenum.value1", args);
	}

	@Test
	public void getCurrentLanguageTest() {
		I18N.getCurrentLanguage();

		Mockito.verify(this.mockI18NLocalizer)
			.getCurrentLanguage();
	}
}
