package de.bonprix.vaadin.theme;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.themes.ValoTheme;

/**
 * holds ValoTheme StyleNames and custom Resources
 *
 * @author marten
 *
 */
public class DSuiteTheme extends ValoTheme {

	/**
	 * dsuite logo for production
	 */
	public static final String DSUITE_LOGO_MODULES_URL = "https://dsuite.bonprix.net/design/Logos_module/";
	/**
	 * dsuite logo for production
	 */
	public static final Resource DSUITE_LOGO = new ExternalResource("vaadin://images/dsuite_startscreen_logo.png");
	/**
	 * dsuite logo for development mode
	 */
	public static final Resource DSUITE_LOGO_DEV = new ExternalResource(
			"vaadin://images/dsuite_startscreen_logo_DEVELOPMENT.png");
	/**
	 * dsuite logo for integrationtest
	 */
	public static final Resource DSUITE_LOGO_INT = new ExternalResource(
			"vaadin://images/dsuite_startscreen_logo_INTEGRATIONTEST.png");
	/**
	 * dsuite logo for localhost
	 */
	public static final Resource DSUITE_LOGO_LOC = new ExternalResource(
			"vaadin://images/dsuite_startscreen_logo_LOCALHOST.png");

	public static final String CAPTION_PRIMARY = "bp-caption-primary";

	public static final String TABSHEET_INVERTED_TABCONTENT = "bp-inverted-tabcontent";

	public static final String TEXTFIELD_WITH_ICON_ONLY_FULL_WIDTH = "bp-with-button-icon-only-full-width";

	public static final String TEXTFIELD_UPPERCASE = "bp-uppercase";

	public static final String MESSAGE_BOX = "bp-message-box";

	public static final String DIALOG = "bp-dialog";

	public static final String DIALOG_HEADLINE = "bp-dialog-headline";

	public static final String STATUS_WIZARD = "bp-status-wizard";

	public static final String STATUS_WIZARD_MAIN = "bp-status-wizard-main";

	public static final String STATUS_WIZARD_LEFT_LAYOUT = "pb-status-wizard-left-layout";

	public static final String STATUS_WIZARD_LABEL_HEADER = "bp-status-wizard-label-header";

	public static final String STATUS_WIZARD_RIGHT_LAYOUT = "bp-status-wizard-right-layout";

	public static final String PANEL_INVERTED = "bp-panel--inverted";

	public static final String BUTTON_BAR = "bp-buttonbar";

	public static final String BUTTON_BAR_SMALL = "bp-buttonbar-small";

	public static final String DIALOG_BUTTON_BAR = "bp-dialog-button-bar";

	public static final String GRID_COMPACT = "compact";

	public static final String GRID_HIGHLIGHT_EDITABLE_COLUMNS = "bp-grid-highlight-editable-columns";

	public static final String DIALOG_PANEL_WHITE = "bp-dialog-panel-white";

	public static final String DIALOG_SUBLINE = "bp-dialog-subline";

	public static final String BP_ERROR_PANEL = "bp-error-panel";

	public static final String BP_ERROR_TEXTAREA = "bp-error-textarea";

	public static final String RATINGSTARS_INVERTED = "bp-ratingstars--inverted";

	public static final String BP_IMAGEBORWSER_ASPECT_RATIO = "bp-preview-image";

	public static final String BP_SLIDER_CURSOR = "bp-slider-cursor";

	public static final String BP_IMAGE_BROWSER_WRAP = "image-wrapper";

	public static final String COMPONENTBAR = "bp-componentbar";
	public static final String COMPONENTBAR_MENU = "bp-componentbar--menu";
	public static final String COMPONENTBAR_COMPONENT = "bp-componentbar--component";

	public static final String FORMLAYOUT_NO_PADDING_LAST_ROW = "bp-formlayout--no-padding-last-row";

	public static final String BACKGROUND_COLOR__MAIN_COLOR_DARK = "bp-background-color--main-color-dark";
	public static final String BACKGROUND_COLOR__MAIN_COLOR_MEDIUM = "bp-background-color--main-color-medium";
	public static final String BACKGROUND_COLOR__MAIN_COLOR_LIGHT = "bp-background-color--main-color-light";
	public static final String BACKGROUND_COLOR__MAIN_COLOR_ULTRA_LIGHT = "bp-background-color--main-color-ultra-light";

	public static final String BACKGROUND_COLOR__GRAY_COLOR_ULTRA_DARK = "bp-background-color--gray-color-ultra-dark";
	public static final String BACKGROUND_COLOR__GRAY_COLOR_DARK = "bp-background-color--gray-color-dark";
	public static final String BACKGROUND_COLOR__GRAY_COLOR_MEDIUM = "bp-background-color--gray-color-medium";
	public static final String BACKGROUND_COLOR__GRAY_COLOR_LIGHT = "bp-background-color--gray-color-light";
	public static final String BACKGROUND_COLOR__GRAY_COLOR_ULTRA_LIGHT = "bp-background-color--gray-color-ultra-light";

	public static final String BACKGROUND_COLOR__SPECIAL_COLOR_GREEN = "bp-background-color--special-color-green";
	public static final String BACKGROUND_COLOR__SPECIAL_COLOR_YELLOW = "bp-background-color--special-color-yellow";
	public static final String BACKGROUND_COLOR__SPECIAL_COLOR_RED = "bp-background-color--special-color-red";

	public static final String BACKGROUND_COLOR__SECONDARY_COLOR_DARK = "bp-background-color--secondary-color-dark";
	public static final String BACKGROUND_COLOR__SECONDARY_COLOR_MEDIUM = "bp-background-color--secondary-color-medium";
	public static final String BACKGROUND_COLOR__SECONDARY_COLOR_LIGHT = "bp-background-color--secondary-color-light";
	public static final String BACKGROUND_COLOR__SECONDARY_COLOR_ULTRA_LIGHT = "bp-background-color--secondary-color-ultra-light";

	/**
	 * fetches the correct log depending on the deploymentState
	 *
	 * @param deploymentState
	 *            for example DEVELOPMENT, INTEGRATIONTEST, LOCALHOST
	 * @return vaadin Resource
	 */
	public static Resource getStartscreenLogo(final String deploymentState) {
		if (deploymentState.toLowerCase()
			.contains("dev")) {
			return DSuiteTheme.DSUITE_LOGO_DEV;
		} else if (deploymentState.toLowerCase()
			.contains("int")) {
			return DSuiteTheme.DSUITE_LOGO_INT;
		} else if (deploymentState.toLowerCase()
			.contains("loc")) {
			return DSuiteTheme.DSUITE_LOGO_LOC;
		} else {
			return DSuiteTheme.DSUITE_LOGO;
		}
	}

}
