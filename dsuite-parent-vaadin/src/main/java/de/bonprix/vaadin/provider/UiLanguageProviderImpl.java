package de.bonprix.vaadin.provider;

import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;

import de.bonprix.I18N;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.user.dto.Principal;

@UIScope
@Component
public class UiLanguageProviderImpl implements UiLanguageProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(UiLanguageProviderImpl.class);

	@Autowired
	private UiPageProvider uiPageProvider;

	@Autowired
	private PrincipalProvider principalProvider;

	Locale detectLocale(final String languageIsoCode, final Principal principal) {
		// 1. detect by language iso code
		if (languageIsoCode != null) {
			final Locale locale = Locale.forLanguageTag(languageIsoCode.toLowerCase());
			if (locale != null && locale.getLanguage() != null && locale.getLanguage()
				.length() > 0) {
				UiLanguageProviderImpl.LOGGER.debug("detected locale by language iso code: {}", locale.getLanguage());
				return locale;
			}
		}

		// 2. detect by locale filled in the principal
		if (principal != null) {
			final Locale principalLocale = principal.getLocale();
			if (principalLocale != null) {
				UiLanguageProviderImpl.LOGGER.debug("detected language by principal's configuration: {}",
													principalLocale.getLanguage());
				return principalLocale;
			}
		}

		// 3. default using default/current language
		final SimpleLanguage currentLanguage = I18N.getCurrentLanguage();
		UiLanguageProviderImpl.LOGGER.debug("no language found, using default/current language: {}",
											currentLanguage.getIsoShort());
		return Locale.forLanguageTag(currentLanguage.getIsoShort()
			.toLowerCase());
	}

	@Override
	public void switchLanguage(final SimpleLanguage language) {
		final Locale locale = detectLocale(language.getIsoShort(), null);

		// update session set locale
		UI.getCurrent()
			.getSession()
			.setLocale(locale);

		// update principal context locale
		updatePrincipalContextLanguage(language);

		// reload page
		this.uiPageProvider.reload();
	}

	private void updatePrincipalContextLanguage(final SimpleLanguage language) {
		final Principal principal = this.principalProvider.getRootPrincipal();
		if (principal != null) {
			final Long principalLanguageId = principal.getLanguageId();
			if (principalLanguageId == null || !principalLanguageId.equals(language.getId())) {
				final SecurityContext context = SecurityContextHolder.getContext();
				if (context != null) {
					principal.setLanguageId(language.getId());
					context.setAuthentication(new BonprixAuthentication(principal, null, context.getAuthentication()
						.getAuthorities()));
					UiLanguageProviderImpl.LOGGER.debug(
														"updated principal's language in SecurityContext to: {}({}) for {}",
														language.getIsoShort(), language.getId(), principal.getName());
				}
			}
		}
	}

	@Override
	public Collection<SimpleLanguage> getAvailableLanguages() {
		return I18N.getAvailableLanguages();
	}

	@Override
	public SimpleLanguage getCurrentLanguage() {
		return I18N.getCurrentLanguage();
	}

}
