/**
 *
 */
package de.bonprix.vaadin.layout.burgermenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import de.bonprix.I18N;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.Principal;
import de.bonprix.user.dto.PrincipalRole;
import de.bonprix.vaadin.admin.ApplicationInfoDialog;
import de.bonprix.vaadin.layout.BpScreenshot;
import de.bonprix.vaadin.mail.MailPopupConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.provider.UiDialogProvider;
import de.bonprix.vaadin.provider.UiLanguageProvider;
import de.bonprix.vaadin.provider.UiPageProvider;
import de.bonprix.vaadin.provider.UiSessionProvider;
import de.bonprix.vaadin.shortcut.ShortcutInfoDialog;
import de.bonprix.vaadin.util.ThemeHelper;

/**
 * @author cthiel
 * @date 17.11.2016
 */
@SpringPresenter
public class BpBurgerMenuPresenter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BpBurgerMenuPresenter.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PrincipalProvider principalProvider;

	@Autowired
	private UiDialogProvider uiDialogProvider;

	@Autowired
	private UiPageProvider uiPageProvider;

	@Autowired
	private UiLanguageProvider uiLanguageProvider;

	@Autowired
	private UiSessionProvider uiSessionProvider;

	@Autowired
	private ApplicationProvider applicationProvider;

	@Autowired
	private BpScreenshot bpScreenshot;

	@Value("${helpme.mailAddress}")
	private String helpMeTo;

	@Value("${portal.url}")
	private String portalUrl;

	@Value("${handout.url}")
	private String handoutUrl;

	@Value("${confluence.url}")
	private String confluenceUrl;

	private BpBurgerMenu view;

	public void setView(final BpBurgerMenu view) {
		this.view = view;
	}

	private BpBurgerMenu getView() {
		return this.view;
	}

	public void init() {
		// languages
		final List<SimpleLanguage> languages = new ArrayList<>();
		for (final SimpleLanguage language : I18N.getAvailableLanguages()) {
			if (!language.getId()
				.equals(PrincipalSecurityContext.getRootPrincipal()
					.getLanguageId())) {
				languages.add(language);
			}
		}
		getView().setLanguages(languages);
		getView().setConfluenceItemEnabled(!StringUtils.isEmpty(this.confluenceUrl));
		getView().setHandoutItemEnabled(!StringUtils.isEmpty(this.handoutUrl));
		getView().setAdminItemsVisible(hasAdminRole(this.principalProvider.getAuthenticatedPrincipal()));
		getView().refreshThemeItemChildren(ThemeHelper.getTheme());
	}

	/**
	 * checks if the provided principal has a role containing the word admin
	 * (ignoring case)
	 * 
	 * @param principal
	 *            principal to check for role
	 * @return
	 */
	private boolean hasAdminRole(Principal principal) {
		// TODO workaround
		// needs to be fixed with a property on the principal role
		for (PrincipalRole principalRole : principal.getPrincipalRoles()) {
			if ("ADMIN".equalsIgnoreCase(principalRole.getName())) {
				return true;
			}
		}
		return false;
	}

	public void openApplicationInfoDialog() {
		final ApplicationInfoDialog dialog = this.applicationContext.getBean(ApplicationInfoDialog.class);
		this.uiDialogProvider.openDialog(dialog);
	}

	public void logout() {
		this.uiSessionProvider.logout();
	}

	/**
	 * @param language
	 */
	public void switchLanguage(final SimpleLanguage language) {
		this.uiLanguageProvider.switchLanguage(language);
	}

	public void redirectToConfluence() {
		this.uiPageProvider.redirect(this.confluenceUrl);
	}

	public void openConfluence() {
		this.uiPageProvider.openInNewTab(this.confluenceUrl);
	}

	public void mailToHelpMe() {
		final String subject = this.applicationProvider.getApplication()
			.getName();
		this.uiPageProvider.openDefaultMailClient(new MailPopupConfigurationBuilder().withTo(this.helpMeTo)
			.withSubject(subject)
			.build());
	}

	public void openHandout() {
		this.uiPageProvider.openInNewTab(this.handoutUrl);
	}

	public void openPortal() {
		this.uiPageProvider.openInNewTab(this.portalUrl);
	}

	public void openShortcutInfoDialog() {
		final ShortcutInfoDialog dialog = this.applicationContext.getBean(ShortcutInfoDialog.class);
		this.uiDialogProvider.openDialog(dialog);
	}

	public void refreshTranslations() {
		I18N.reloadI18NLocalizer();
		this.uiPageProvider.reload();
	}

	public void takeScreenshot() {
		// Wait until the Burgermenu is closed ...
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			BpBurgerMenuPresenter.LOGGER.error(e.getLocalizedMessage());
			Thread.currentThread()
				.interrupt();
		}
		// ... then make the Screenshot
		this.bpScreenshot.takeScreenshot();
	}

	public void changeTheme(String themeName) {
		ThemeHelper.changeTheme(themeName);
		getView().refreshThemeItemChildren(themeName);
	}

}
