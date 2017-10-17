/**
 *
 */
package de.bonprix.vaadin.dialog;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.addons.collapsiblepanel.CollapsiblePanel;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.CollapseEvent;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.CollapseExpandListener;
import org.vaadin.addons.collapsiblepanel.CollapsiblePanel.ExpandEvent;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bonprix.I18N;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.provider.UiDialogProvider.Dialog;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * The AbstractBaseDialog is a base class for general dialog window.
 *
 * @author Kartik Suba
 *
 */
public abstract class AbstractBaseDialog extends Window implements Dialog {

	private static final long serialVersionUID = 1L;
	private boolean initFinished = false;

	private static final String MAIL_TO_FOR_HELP = "mailto:help-me@bonprix.net";

	private final DialogButtonBar buttonBar = new DialogButtonBar();
	private final Map<String, Button> buttonMap = new HashMap<>();
	private DialogConfiguration configuration;
	private Component cachedDetailsLayout;

	/**
	 * Constructs a general dialog window with the specified configuration
	 * object.
	 *
	 * @param configuration
	 *            the dialog configuration
	 */
	public AbstractBaseDialog(final DialogConfiguration configuration) {
		setConfiguration(configuration);

		if (configuration != null) {
			for (final DialogButtonConfiguration buttonConfig : configuration.getButtonConfigs()) {

				final String caption = buttonConfig.getCaptionKey() != null ? I18N.get(buttonConfig.getCaptionKey())
						: null;
				final Button button = new Button(caption);

				// if primaryButton
				if (buttonConfig.equals(configuration.getPrimaryButtonConfig())) {
					button.addStyleName(DSuiteTheme.BUTTON_PRIMARY);
				}

				// Defined Handler if CONTACT_SUPPORT button is added
				if (buttonConfig.getCaptionKey()
					.equals(DialogButton.CONTACT_SUPPORT.name())) {
					button.addClickListener(e -> {
						Page.getCurrent()
							.open(AbstractBaseDialog.MAIL_TO_FOR_HELP, null);
					});
				}

				// Default Handler if CANCEL or CLOSE button is added
				if (buttonConfig.isCloseOnClick() || configuration.isCloseOnAnyButton() || (buttonConfig.getCaptionKey()
					.equals(DialogButton.CANCEL.name()) && configuration.isCloseOnCancelButton())
						|| (buttonConfig.getCaptionKey()
							.equals(DialogButton.CLOSE.name()) && configuration.isCloseOnCancelButton())) {
					button.addClickListener(e -> {
						close();
					});
				}

				// User Defined Listeners for all buttons except CONTACT_SUPPORT
				if (buttonConfig.getButtonAction() != null) {
					if (!buttonConfig.getCaptionKey()
						.equals(DialogButton.CONTACT_SUPPORT.name())) {
						button.addClickListener(event -> buttonConfig.getButtonAction()
							.onClick());
					}
				}

				this.buttonBar.addComponent(button);
				this.buttonMap.put(buttonConfig.getCaptionKey(), button);

			}

		}

		// Needed to have the area in Window/Dialog where you can drag the
		// Window!
		setCaption("Caption");

		setModal(configuration.isModal());
		setResizable(configuration.isResizable());
		setClosable(configuration.isClosable());
		setDraggable(configuration.isDragable());
		center();

		if (configuration.getWidth() != null && configuration.getHeight() != null && configuration.getWidth() <= 0
				&& configuration.getHeight() <= 0) {
			setSizeUndefined();
		} else {
			if (configuration.getWidth() != null && configuration.getWidth() > 0) {
				setWidth(configuration.getWidth(), Unit.PIXELS);
			}
			if (configuration.getHeight() != null && configuration.getHeight() > 0) {
				setHeight(configuration.getHeight(), Unit.PIXELS);
			}
		}

		this.initFinished = true;
	}

	@Override
	public void attach() {
		super.attach();
		addStyleName(DSuiteTheme.DIALOG);

		final Component content = layout();

		VerticalLayout header = new VerticalLayout();

		final Label subLine = FluentUI.label()
			.valueKey(getConfiguration().getSubline())
			.get();

		// Adding the sub line width if specified. After this widthis reached,
		// the sub line would wrap.
		if (getConfiguration().getMaxSubLineWidth() != null) {
			subLine.setWidth(String.valueOf(getConfiguration().getMaxSubLineWidth()));
		}

		if (getConfiguration().getHeadline() != null) {
			header = FluentUI.vertical()
				.add(FluentUI.label()
					.valueKey(getConfiguration().getHeadline())
					.style((DSuiteTheme.DIALOG_HEADLINE))
					.get())
				.sizeUndefined()
				.margin(true, true, false, true)
				.get();
			if (getConfiguration().getSubline() != null && !getConfiguration().getSubline()
				.isEmpty()) {
				header.addComponent(subLine);
			}

			if (getConfiguration().getSubline() != null && getConfiguration().getSubline()
				.length() > getConfiguration().getHeadline()
					.length()) {
				subLine.setStyleName(DSuiteTheme.DIALOG_SUBLINE);
			}
		}

		createMainLayout(content, header);
	}

	private void createMainLayout(final Component content, VerticalLayout header) {
		final VerticalLayout mainLayout;

		if (getConfiguration().getHeadline() != null) {
			mainLayout = new VerticalLayout(header, content, AbstractBaseDialog.this.buttonBar);
		} else {
			mainLayout = new VerticalLayout(content, AbstractBaseDialog.this.buttonBar);
		}
		mainLayout.setExpandRatio(content, 1);
		mainLayout.setComponentAlignment(content, Alignment.TOP_CENTER);

		if (getConfiguration().isWithDetails()) {
			mainLayout.addComponent(createDetailsPanel());
		}
		mainLayout.setComponentAlignment(AbstractBaseDialog.this.buttonBar, Alignment.MIDDLE_CENTER);
		mainLayout.setSizeUndefined();
		if (getWidth() > -1 && getHeight() > -1) {
			mainLayout.setSizeFull();
		}

		setContent(mainLayout);
	}

	@Override
	public void setCaption(final String caption) {
		if (this.initFinished) {
			throw new UnsupportedOperationException(
					"Please use the headline and subline to configure the dialogs caption");
		}
		super.setCaption(caption);
	}

	public void setHeadline(final String headlineKey) {
		super.setCaption(I18N.get(headlineKey));
	}

	public void setSubline(final String sublineKey) {

	}

	private Component createDetailsPanel() {

		final Panel panel = new Panel(I18N.get("SHOW_DETAILS"));
		panel.setStyleName(DSuiteTheme.BP_ERROR_PANEL);
		panel.setSizeFull();

		final CollapsiblePanel optionalDetailsPanel = new CollapsiblePanel(panel);
		optionalDetailsPanel.setCollapsed(true);
		optionalDetailsPanel.addCollapseExpandListener(new CollapseExpandListener() {

			@Override
			public void expand(final ExpandEvent event) {
				panel.setCaption(I18N.get("HIDE_DETAILS"));
				if (panel.getContent() == null) {
					panel.setContent(getCachedDetailsLayout());
				}
			}

			@Override
			public void collapse(final CollapseEvent event) {
				panel.setCaption(I18N.get("SHOW_DETAILS"));
			}
		});
		return panel;
	}

	/**
	 * Constructs the content of the dialog. This method gets called when the
	 * window gets attached to the UI.
	 *
	 * @return the content
	 */
	protected abstract Component layout();

	/**
	 * Constructs the content of the details-panel. This method is only called
	 * when the dialog is created with "isWithDetails == true". Also this method
	 * is only called once at the time of the first expansion of the details.
	 * Further calls will result in displaying the cached component.<br/>
	 * <br/>
	 * Override this method when setting dialog property "isWithDetails == true"
	 * .
	 *
	 * @return the details panel
	 */
	protected Component detailsLayout() {
		return null;
	}

	private Component getCachedDetailsLayout() {
		if (this.cachedDetailsLayout == null) {
			this.cachedDetailsLayout = detailsLayout();
		}

		return this.cachedDetailsLayout;
	}

	/**
	 * Maps the passed listener to the passed button with the help of buttonKey
	 *
	 * @param enumKey
	 * @param listener
	 */
	protected void addButtonListener(final DialogButton enumKey, final Button.ClickListener listener) {
		addButtonListener(enumKey.name(), listener);
	}

	/**
	 * Maps the passed listener to the passed button with the help of buttonKey
	 *
	 * @param buttonKey
	 * @param listener
	 */
	protected void addButtonListener(final String buttonKey, final Button.ClickListener listener) {
		if (!this.buttonMap.containsKey(buttonKey)) {
			throw new IllegalArgumentException(
					"The button with the ID " + buttonKey + " was not registered in constructor");
		}

		getButton(buttonKey).addClickListener(listener);
	}

	/**
	 *
	 * @param enumKey
	 * @return Button
	 */
	protected Button getButton(final DialogButton enumKey) {
		return getButton(enumKey.getCaptionKey());
	}

	/**
	 *
	 * @param buttonKey
	 * @return Button
	 */
	protected Button getButton(final String buttonKey) {
		return this.buttonMap.get(buttonKey);
	}

	public class DialogButtonBar extends HorizontalLayout {

		private static final long serialVersionUID = 1L;

		public DialogButtonBar() {
			setSpacing(true);
			setStyleName(DSuiteTheme.DIALOG_BUTTON_BAR);
		}

	}

	public DialogConfiguration getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(final DialogConfiguration configuration) {
		this.configuration = configuration;
	}

	public Map<String, Button> getButtonMap() {
		return this.buttonMap;
	}

}
