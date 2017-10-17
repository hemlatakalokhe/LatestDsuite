package de.bonprix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.dto.masterdata.SimpleApplication;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.builder.SimpleLanguageBuilder;
import de.bonprix.i18n.dto.builder.SimpleI18NKeyBuilder;
import de.bonprix.i18n.dto.builder.SimpleI18NTranslationBuilder;
import de.bonprix.i18n.service.SimpleApplicationLanguageService;
import de.bonprix.i18n.service.SimpleI18NKeyService;
import de.bonprix.i18n.service.fetch.SimpleI18NKeyFetchOptions;
import de.bonprix.i18n.service.filter.SimpleI18NKeyFilter;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.Principal;

@PrepareForTest(PrincipalSecurityContext.class)
public class LocalizeRepoTest extends StaticMethodAwareUnitTest {

	private SimpleLanguage languageEnglish;
	private SimpleLanguage languageGerman;

	private SimpleI18NKeyService mockI18NKeyService;
	private SimpleApplicationLanguageService mockApplicationLanguageService;
	private ApplicationProvider mockApplicationProvider;
	private SimpleApplication mockApplication;
	private Principal mockPrincipal;

	private Long applicationId = 1L;

	private LocalizeRepo localizeRepo;

	@BeforeMethod
	public void init() {
		this.languageEnglish = new SimpleLanguageBuilder().withId(1L)
			.withIsoShort(Locale.ENGLISH.getLanguage())
			.build();

		this.languageGerman = new SimpleLanguageBuilder().withId(2L)
			.withIsoShort(Locale.GERMAN.getLanguage())
			.build();

		this.mockI18NKeyService = PowerMockito.mock(SimpleI18NKeyService.class);
		Mockito.when(this.mockI18NKeyService.findAll(Mockito.any(), Mockito.any()))
			.thenReturn(Arrays.asList(new SimpleI18NKeyBuilder().withKey("KEY")
				.withTranslations(new HashSet<>(
						Arrays.asList(new SimpleI18NTranslationBuilder().withLanguageId(this.languageEnglish.getId())
							.withTranslation(this.languageEnglish.getIsoShort())
							.build(), new SimpleI18NTranslationBuilder().withLanguageId(this.languageGerman.getId())
								.withTranslation(this.languageGerman.getIsoShort())
								.build())))
				.build()));

		this.mockPrincipal = PowerMockito.mock(Principal.class);
		PowerMockito.mockStatic(PrincipalSecurityContext.class);
		Mockito.when(PrincipalSecurityContext.getRootPrincipal())
			.thenReturn(this.mockPrincipal);

		this.mockApplicationProvider = Mockito.mock(ApplicationProvider.class);
		this.mockApplication = Mockito.mock(SimpleApplication.class);
		Mockito.when(this.mockApplication.getId())
			.thenReturn(this.applicationId);
		Mockito.when(this.mockApplicationProvider.getApplication())
			.thenReturn(this.mockApplication);

		this.mockApplicationLanguageService = Mockito.mock(SimpleApplicationLanguageService.class);
		Mockito.when(this.mockApplicationLanguageService.getDefaultLanguage())
			.thenReturn(this.languageEnglish);
		Mockito.when(this.mockApplicationLanguageService.getLanuagesConnectedToAnApplication(Mockito.anyLong()))
			.thenReturn(Arrays.asList(this.languageEnglish, this.languageGerman));

		this.localizeRepo = new LocalizeRepo(this.mockI18NKeyService, this.mockApplicationLanguageService,
				this.mockApplicationProvider);
	}

	@Test
	public void initTest() {
		Mockito.verify(this.mockApplicationLanguageService)
			.getDefaultLanguage();
		ArgumentCaptor<SimpleI18NKeyFilter> i18nKeyFilterCaptor = ArgumentCaptor.forClass(SimpleI18NKeyFilter.class);
		ArgumentCaptor<SimpleI18NKeyFetchOptions> i18nKeyFetchOptionsCaptor = ArgumentCaptor
			.forClass(SimpleI18NKeyFetchOptions.class);
		Mockito.verify(this.mockI18NKeyService)
			.findAll(i18nKeyFilterCaptor.capture(), i18nKeyFetchOptionsCaptor.capture());

		SimpleI18NKeyFilter capturedI18NKeyFilter = i18nKeyFilterCaptor.getValue();
		Assert.assertEquals(capturedI18NKeyFilter.getApplicationIds()
			.size(), 1);
		Assert.assertEquals(capturedI18NKeyFilter.getApplicationIds()
			.get(0), this.mockApplication.getId());

		SimpleI18NKeyFetchOptions capturedI18NKeyFetchOptions = i18nKeyFetchOptionsCaptor.getValue();
		Assert.assertEquals(capturedI18NKeyFetchOptions.isFetchTranslations(), true);

		Assert.assertEquals(this.localizeRepo.getAvailableLanguages(), this.mockApplicationLanguageService
			.getLanuagesConnectedToAnApplication(this.mockApplication.getId()));
		Assert.assertEquals(this.localizeRepo.getDefaultLanguage(), this.languageEnglish);
	}

	@Test
	public void loadRepoTest() {
		// test method call
		this.localizeRepo.reload();

		Mockito.verify(this.mockApplicationLanguageService, Mockito.times(2))
			.getDefaultLanguage();
		Mockito.verify(this.mockI18NKeyService, Mockito.times(2))
			.findAll(Mockito.any(), Mockito.any());
	}

	@Test
	public void containsKeyTest() {
		// test method call
		Assert.assertEquals(this.localizeRepo.containsKey("KEY"), true);
		Assert.assertEquals(this.localizeRepo.containsKey("some other not existing key"), false);
	}

	@Test
	public void getTranslatedKeyKeyNotExistingTest() {
		Mockito.when(this.mockI18NKeyService.findAll(Mockito.any(), Mockito.any()))
			.thenReturn(new ArrayList<>());

		this.localizeRepo.reload();

		// test method call
		Assert.assertEquals(this.localizeRepo.getTranslatedKey("KEY", this.languageGerman), "KEY");
	}

	@Test
	public void getTranslatedKeyTest() {
		Mockito.when(this.mockI18NKeyService.findAll(Mockito.any(), Mockito.any()))
			.thenReturn(Arrays.asList(new SimpleI18NKeyBuilder().withKey("KEY")
				.withTranslations(new HashSet<>(
						Arrays.asList(new SimpleI18NTranslationBuilder().withLanguageId(this.languageEnglish.getId())
							.withTranslation(this.languageEnglish.getIsoShort())
							.build(), new SimpleI18NTranslationBuilder().withLanguageId(this.languageGerman.getId())
								.withTranslation(this.languageGerman.getIsoShort())
								.build())))
				.build()));

		this.localizeRepo.reload();

		// test method call
		Assert.assertEquals(this.localizeRepo.getTranslatedKey("KEY", this.languageGerman),
							this.languageGerman.getIsoShort());
	}

	@Test
	public void getTranslatedKeyByDefaultLanguageTest() {
		Mockito.when(this.mockI18NKeyService.findAll(Mockito.any(), Mockito.any()))
			.thenReturn(Arrays.asList(new SimpleI18NKeyBuilder().withKey("KEY")
				.withTranslations(new HashSet<>(
						Arrays.asList(new SimpleI18NTranslationBuilder().withLanguageId(this.languageEnglish.getId())
							.withTranslation(this.languageEnglish.getIsoShort())
							.build())))
				.build()));

		this.localizeRepo.reload();

		// test method call
		Assert.assertEquals(this.localizeRepo.getTranslatedKey("KEY", this.languageGerman),
							this.languageEnglish.getIsoShort());
	}

	@Test
	public void getTranslatedKeyNoTranslationByCurrentLanguageOrDefaultLanguageFoundTest() {
		Mockito.when(this.mockI18NKeyService.findAll(Mockito.any(), Mockito.any()))
			.thenReturn(Arrays.asList(new SimpleI18NKeyBuilder().withKey("KEY")
				.withTranslations(new HashSet<>())
				.build()));

		this.localizeRepo.reload();

		// test method call
		Assert.assertEquals(this.localizeRepo.getTranslatedKey("KEY", this.languageGerman), "KEY");
	}

}
