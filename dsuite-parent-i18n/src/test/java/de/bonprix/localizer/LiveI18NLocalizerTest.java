package de.bonprix.localizer;

import java.text.MessageFormat;
import java.util.ArrayList;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.I18N;
import de.bonprix.LocalizeRepo;
import de.bonprix.SecurityContextSetter;
import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;
import de.bonprix.dto.masterdata.builder.SimpleLanguageBuilder;
import de.bonprix.exception.PrincipalMissingException;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.Principal;

@PrepareForTest({ PrincipalSecurityContext.class, MessageFormat.class, LiveI18NLocalizer.class, I18N.class })
public class LiveI18NLocalizerTest extends StaticMethodAwareUnitTest {

	private final LiveI18NLocalizer liveI18NLocalizer = new LiveI18NLocalizer();

	private SimpleLanguage languageEnglish;
	private SimpleLanguage languageGerman;

	private Principal mockPrincipal;

	private LocalizeRepo mockLocalizeRepo;

	@BeforeMethod
	public void setUp() {
		SecurityContextSetter.setDefaultSecurityContext();

		this.languageEnglish = new SimpleLanguageBuilder().withId(1L)
			.build();

		this.languageGerman = new SimpleLanguageBuilder().withId(2L)
			.build();

		this.mockPrincipal = PowerMockito.mock(Principal.class);
		Mockito.when(this.mockPrincipal.getLanguageId())
			.thenReturn(this.languageEnglish.getId());
		PowerMockito.spy(PrincipalSecurityContext.class);
		Mockito.when(PrincipalSecurityContext.getRootPrincipal())
			.thenReturn(this.mockPrincipal);

		this.mockLocalizeRepo = PowerMockito.mock(LocalizeRepo.class);
		LiveI18NLocalizer.setTranslationRepo(this.mockLocalizeRepo);
	}

	@Test
	public void getTranslationWithLanguageNotContainingTest() {
		String key = "key";

		PowerMockito.mockStatic(I18N.class);

		Mockito.when(this.mockLocalizeRepo.containsKey(Mockito.eq(key)))
			.thenReturn(false);

		final String output = this.liveI18NLocalizer.get(key, this.languageEnglish);
		Assert.assertEquals(output, key);

		Mockito.verify(this.mockLocalizeRepo)
			.containsKey(key);
	}

	@Test
	public void getTranslationWithLanguageContainingTest() {
		String key = "key";
		String translation = "translation {0} : {1}";
		Object[] args = { "arg1", "arg2" };

		Mockito.when(this.mockLocalizeRepo.containsKey(Mockito.eq(key)))
			.thenReturn(true);
		Mockito.when(this.mockLocalizeRepo.getTranslatedKey(key, this.languageEnglish))
			.thenReturn(translation);

		final String output = this.liveI18NLocalizer.get(key, this.languageEnglish, args);
		Assert.assertEquals(output, MessageFormat.format(translation, args));

		Mockito.verify(this.mockLocalizeRepo)
			.containsKey(key);
		Mockito.verify(this.mockLocalizeRepo)
			.getTranslatedKey(key, this.languageEnglish);
	}

	@Test
	public void getTranslationWithoutLanguageNotContainingTest() {
		String key = "key";

		Mockito.when(this.mockLocalizeRepo.containsKey(Mockito.eq(key)))
			.thenReturn(false);

		final String output = this.liveI18NLocalizer.get(key);
		Assert.assertEquals(output, key);

		Mockito.verify(this.mockLocalizeRepo)
			.containsKey(key);
	}

	@Test
	public void getTranslationWithoutLanguageContainingTest() {
		String key = "key";
		String translation = "translation";
		Object[] args = { "arg1", "arg2" };

		PowerMockito.when(PrincipalSecurityContext.getRootPrincipal())
			.thenThrow(new PrincipalMissingException());

		Mockito.when(this.mockLocalizeRepo.getDefaultLanguage())
			.thenReturn(this.languageEnglish);

		Mockito.when(this.mockLocalizeRepo.containsKey(Mockito.eq(key)))
			.thenReturn(true);
		Mockito.when(this.mockLocalizeRepo.getTranslatedKey(Mockito.eq(key), Mockito.eq(this.languageEnglish)))
			.thenReturn(translation);

		final String output = this.liveI18NLocalizer.get(key, args);
		Assert.assertEquals(output, translation);

		Mockito.verify(this.mockLocalizeRepo, Mockito.times(2))
			.containsKey(key);
		Mockito.verify(this.mockLocalizeRepo)
			.getDefaultLanguage();
	}

	@Test
	public void getCurrentLanguagePrincipalMissingExceptionTest() {
		PowerMockito.when(PrincipalSecurityContext.getRootPrincipal())
			.thenThrow(new PrincipalMissingException());

		Mockito.when(this.mockLocalizeRepo.getDefaultLanguage())
			.thenReturn(this.languageEnglish);

		Assert.assertEquals(this.liveI18NLocalizer.getCurrentLanguage(), this.languageEnglish);
		Mockito.verify(this.mockLocalizeRepo)
			.getDefaultLanguage();
	}

	@Test
	public void getCurrentLanguageByPrincipalLanguageIdTest() {
		Mockito.when(this.mockLocalizeRepo.getAvailableLanguageById(Mockito.anyLong()))
			.thenReturn(this.languageEnglish);

		Assert.assertEquals(this.liveI18NLocalizer.getCurrentLanguage(), this.languageEnglish);
		Mockito.verify(this.mockPrincipal)
			.getLanguageId();
		Mockito.verify(this.mockLocalizeRepo)
			.getAvailableLanguageById(this.mockPrincipal.getLanguageId());
	}

	@Test
	public void getCurrentLanguageByPrincipalLanguageIdButNotInAvailableLanguages() {
		Mockito.when(this.mockLocalizeRepo.getAvailableLanguageById(Mockito.anyLong()))
			.thenReturn(null);
		Mockito.when(this.mockLocalizeRepo.getDefaultLanguage())
			.thenReturn(this.languageEnglish);

		Assert.assertEquals(this.liveI18NLocalizer.getCurrentLanguage(), this.languageEnglish);
		Mockito.verify(this.mockPrincipal)
			.getLanguageId();
		Mockito.verify(this.mockLocalizeRepo)
			.getAvailableLanguageById(this.mockPrincipal.getLanguageId());
		Mockito.verify(this.mockLocalizeRepo)
			.getDefaultLanguage();
	}

	@Test
	public void reloadTranslationRepoTest() {
		this.liveI18NLocalizer.reloadLocalizeRepo();

		Mockito.verify(this.mockLocalizeRepo)
			.reload();
	}

	@Test
	public void getAvailableLanguagesTest() {
		this.liveI18NLocalizer.getAvailableLanguages();

		Mockito.verify(this.mockLocalizeRepo)
			.getAvailableLanguages();
	}

	@Test
	public void getLanguageTranslationsWithDefinedLanguageTest() {
		Long languageId = 999L;
		String expectedTranslation = "EXPECTED_TRANSLATION";

		SimpleLanguage languageContainerMock = Mockito.mock(SimpleLanguage.class);
		SimpleLanguageLanguage languageContainerElementMock = Mockito.mock(SimpleLanguageLanguage.class);
		I18NLanguageElementFunction<SimpleLanguageLanguage> functionMock = Mockito
			.mock(I18NLanguageElementFunction.class);

		Mockito.when(languageContainerMock.getLanguageElement(Mockito.anyLong()))
			.thenReturn(languageContainerElementMock);
		Mockito.when(functionMock.apply(Mockito.any()))
			.thenReturn(expectedTranslation);

		String translation = this.liveI18NLocalizer.get(languageContainerMock, functionMock, languageId);

		Mockito.verify(functionMock)
			.apply(languageContainerElementMock);

		Assert.assertEquals(translation, expectedTranslation);
	}

	@Test
	public void getLanguageTranslationsWithDefinedLanguageNoTranslationTest() {
		Long id = 999L;
		Long languageId = 111L;

		SimpleLanguage languageContainerMock = Mockito.mock(SimpleLanguage.class);
		Mockito.when(languageContainerMock.getId())
			.thenReturn(id);
		I18NLanguageElementFunction<SimpleLanguageLanguage> functionMock = Mockito
			.mock(I18NLanguageElementFunction.class);

		String translation = this.liveI18NLocalizer.get(languageContainerMock, functionMock, languageId);

		Mockito.verify(functionMock, Mockito.never())
			.apply(Mockito.any());

		Assert.assertEquals(translation, MessageFormat.format("missing translation for id: {0}", id));
	}

	@Test
	public void getLanguageTranslationsWithCurrentLanguageTest() {
		String expectedTranslation = "EXPECTED_TRANSLATION";

		Mockito.when(this.mockLocalizeRepo.getAvailableLanguages())
			.thenReturn(new ArrayList<>());
		Mockito.when(this.mockLocalizeRepo.getDefaultLanguage())
			.thenReturn(this.languageEnglish);

		SimpleLanguage languageContainerMock = Mockito.mock(SimpleLanguage.class);
		SimpleLanguageLanguage languageContainerElementMock = Mockito.mock(SimpleLanguageLanguage.class);
		I18NLanguageElementFunction<SimpleLanguageLanguage> functionMock = Mockito
			.mock(I18NLanguageElementFunction.class);

		Mockito.when(languageContainerMock.getLanguageElement(Mockito.anyLong()))
			.thenReturn(languageContainerElementMock);
		Mockito.when(functionMock.apply(Mockito.any()))
			.thenReturn(expectedTranslation);

		String translation = this.liveI18NLocalizer.get(languageContainerMock, functionMock);

		Mockito.verify(languageContainerMock)
			.getLanguageElement(this.languageEnglish.getId());
		Mockito.verify(functionMock)
			.apply(languageContainerElementMock);

		Assert.assertEquals(translation, expectedTranslation);
	}

}
