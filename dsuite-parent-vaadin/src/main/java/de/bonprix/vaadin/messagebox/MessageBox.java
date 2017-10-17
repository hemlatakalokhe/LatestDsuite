package de.bonprix.vaadin.messagebox;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogButtonAction;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * @author k.suba
 *
 */
@SuppressWarnings("serial")
public class MessageBox extends AbstractBaseDialog {

	private Embedded icon;
	private final Label message;

	/**
	 * The constructor to initialize the messageBox.
	 *
	 * @param configuration
	 *            the message box configuration.
	 */
	public MessageBox(final MessageBoxConfiguration configuration) {
		super(configuration.getAbstractBaseDialogConfiguration());

		setClosable(false);
		setResizable(false);

		addStyleName(DSuiteTheme.MESSAGE_BOX);

		// Adding default close click listener to all buttons
		getButtonMap().forEach((buttonKey, button) -> addButtonListener(buttonKey, event -> close()));

		this.message = new Label(configuration.getHtmlMessage(), ContentMode.HTML);

		if (configuration.getMessageBoxIcon() != null) {
			this.icon = new Embedded(null, configuration.getMessageBoxIcon()
				.getIcon());
			this.icon.setWidth(configuration.getIconWidth() + "px");
			this.icon.setHeight(configuration.getIconHeight() + "px");

			if (configuration.getAbstractBaseDialogConfiguration()
				.getWidth() != null) {
				this.message.setWidth(configuration.getAbstractBaseDialogConfiguration()
					.getWidth() - configuration.getIconWidth() - 30 * 2 /* padding */ - 15 /* spacing */, Unit.PIXELS);
			}
		}

	}

	@Override
	protected Component layout() {

		final HorizontalLayout contentLayout = new HorizontalLayout();
		if (this.icon != null) {
			contentLayout.addComponent(this.icon);
			contentLayout.setComponentAlignment(this.icon, Alignment.MIDDLE_CENTER);
		}
		if (this.message != null) {
			contentLayout.addComponent(this.message);
			contentLayout.setComponentAlignment(this.message, Alignment.MIDDLE_CENTER);
		}
		contentLayout.setSpacing(true);
		contentLayout.setMargin(true);

		return FluentUI.vertical()
			.add(contentLayout)
			.sizeFull()
			.get();

	}

	/**
	 * Creates and opens the window for the messagebox.
	 */
	private void open() {
		UI.getCurrent()
			.addWindow(this);
	}

	/**
	 * Closes the window if open.
	 */
	@Override
	public void close() {
		UI.getCurrent()
			.removeWindow(this);
	}

	/**
	 * Displays a message box with a custom component for message.
	 *
	 * @param configuration
	 *            configuration of the dialog.
	 * @return The {@link MessageBox} instance itself.
	 */
	public static MessageBox show(final MessageBoxConfiguration configuration) {
		final MessageBox result = new MessageBox(configuration);
		result.open();
		return result;
	}

	/**
	 * Displays a message as question message with "NO" and "YES" button ("NO"
	 * is the primary button"). When the "YES" button is clicked, the given
	 * yesClickListener will be executed.
	 *
	 * @param htmlMessage
	 *            the message itself
	 * @param listenerYes
	 *            will be called when the "YES" button is clicked
	 * @return the messageBox object
	 */
	@Deprecated
	public static MessageBox showQuestion(final String htmlMessage, final DialogButtonAction listenerYes) {
		return MessageBox.showQuestion(htmlMessage, listenerYes, DialogButton.NO);
	}

	/**
	 * Displays a message as question message with "NO" and "YES" button
	 * (definable primary button). When the "YES" button is clicked, the given
	 * yesClickListener will be executed.
	 *
	 * @param htmlMessage
	 *            the message itself
	 * @param listenerYes
	 *            will be called when the "YES" button is clicked
	 * @param primary
	 *            will be the primary button
	 * @return the messageBox object
	 */
	public static MessageBox showQuestion(final String htmlMessage, final DialogButtonAction listenerYes,
			DialogButton primary) {
		MessageBoxConfiguration mbcb = new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
			.withHtmlMessage(htmlMessage)
			.withButton(DialogButton.YES, listenerYes)
			.withButton(DialogButton.NO)
			.withCloseOnAnyButton(true)
			.build();

		DialogConfiguration abc = mbcb.getAbstractBaseDialogConfiguration();

		switch (primary) {
		case YES:
			abc.setPrimaryButtonConfig(abc.getButtonConfigs()
				.get(0));
			break;
		case NO:
			abc.setPrimaryButtonConfig(abc.getButtonConfigs()
				.get(1));
			break;
		default:
			break;

		}

		return MessageBox.show(mbcb);
	}

	/**
	 * Displays a message as info message with the "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	public static MessageBox showInfo(final String htmlMessage) {
		return MessageBox.show(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.INFO)
			.withHtmlMessage(htmlMessage)
			.withPrimaryButton(DialogButton.CLOSE)
			.withCloseOnAnyButton(true)
			.build());
	}

	/**
	 * Displays a message as warning message with the "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	public static MessageBox showWarning(final String htmlMessage) {
		return MessageBox.show(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.WARNING)
			.withHtmlMessage(htmlMessage)
			.withPrimaryButton(DialogButton.CLOSE)
			.withCloseOnAnyButton(true)
			.build());
	}

	/**
	 * Displays a message as error message with the "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	public static MessageBox showError(final String htmlMessage) {
		return MessageBox.show(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.ERROR)
			.withHtmlMessage(htmlMessage)
			.withButton(DialogButton.CONTACT_SUPPORT)
			.withPrimaryButton(DialogButton.CLOSE)
			.withCloseOnAnyButton(true)
			.build());
	}

}
