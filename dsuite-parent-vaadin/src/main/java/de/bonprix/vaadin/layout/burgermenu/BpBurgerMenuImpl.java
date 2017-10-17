package de.bonprix.vaadin.layout.burgermenu;

import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.I18N;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.theme.VaadinThemesDirectory;

/**
 * The bp menu at the top right of every bonprix vaadin application.
 */
@UIScope
@Component
public class BpBurgerMenuImpl extends MenuBar implements BpBurgerMenu {
	private static final long serialVersionUID = 1L;

	@Autowired
	private transient BpBurgerMenuPresenter presenter;

	private MenuItem i18nRootItem;
	private MenuItem confluence;
	private MenuItem handout;
	private MenuItem adminInfo;
	private MenuItem refreshTranslations;
	private MenuItem themeRootItem;

	@PostConstruct
	public void postConstruct() {
		getPresenter().setView(this);
		getPresenter().init();
	}

	private BpBurgerMenuPresenter getPresenter() {
		return this.presenter;
	}

	public BpBurgerMenuImpl() {
		addStyleName("topMenuBar-globalToolbar");
		addStyleName(ValoTheme.BUTTON_BORDERLESS);
		addStyleName(ValoTheme.BUTTON_HUGE);
		addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		setAutoOpen(true);

		final MenuItem settingsBurgerMenu = addItem("", FontBonprix.MENU, null);
		settingsBurgerMenu.setDescription(I18N.get("SETTINGS"));
		addElementsToSettingsBurgerMenu(settingsBurgerMenu);
	}

	private void addElementsToSettingsBurgerMenu(final MenuItem settingsBurgerMenu) {
		settingsBurgerMenu.addItem(I18N.get("HELP_ME"), selectedItem -> getPresenter().mailToHelpMe());

		this.confluence = settingsBurgerMenu.addItem(	I18N.get("CONFLUENCE"),
														selectedItem -> getPresenter().openConfluence());

		this.handout = settingsBurgerMenu.addItem(I18N.get("HANDOUT"), selectedItem -> getPresenter().openHandout());

		settingsBurgerMenu.addItem(I18N.get("SCREENSHOT"), selectedItem -> getPresenter().takeScreenshot());

		this.i18nRootItem = settingsBurgerMenu.addItem(I18N.get("LANGUAGE"), null);

		this.themeRootItem = settingsBurgerMenu.addItem(I18N.get("THEME"), null);

		settingsBurgerMenu.addItem(I18N.get("PORTAL"), selectedItem -> getPresenter().openPortal());

		this.adminInfo = settingsBurgerMenu.addItem(I18N.get("ADMIN_INFO"),
													selectedItem -> getPresenter().openApplicationInfoDialog());

		this.refreshTranslations = settingsBurgerMenu.addItem(	I18N.get("REFRESH_TRANSLATIONS"),
																selectedItem -> getPresenter().refreshTranslations());

		settingsBurgerMenu.addItem(I18N.get("SHORTCUT_INFO"), selectedItem -> this.presenter.openShortcutInfoDialog());

		settingsBurgerMenu.addItem(I18N.get("LOGOUT"), selectedItem -> getPresenter().logout());

	}

	@Override
	public void setLanguages(final List<SimpleLanguage> languages) {
		for (final SimpleLanguage language : languages) {
			this.i18nRootItem.addItem(	I18N.get(language, SimpleLanguageLanguage::getName),
										selectedItem -> getPresenter().switchLanguage(language));
		}
	}

	@Override
	public void setHandoutItemEnabled(boolean enabled) {
		this.handout.setEnabled(enabled);
	}

	@Override
	public void setConfluenceItemEnabled(boolean enabled) {
		this.confluence.setEnabled(enabled);
	}

	@Override
	public void setAdminItemsVisible(boolean visible) {
		this.refreshTranslations.setVisible(visible);
		this.adminInfo.setVisible(visible);
	}

	@Override
	public void refreshThemeItemChildren(String themeName) {
		this.themeRootItem.removeChildren();
		for (final Entry<String, String> themeEntry : VaadinThemesDirectory.getThemes()
			.entrySet()) {
			if (!themeEntry.getKey()
				.equals(themeName)) {
				this.themeRootItem.addItem(	themeEntry.getValue(),
											selectedItem -> getPresenter().changeTheme(themeEntry.getKey()));
			}
		}
	}

}
