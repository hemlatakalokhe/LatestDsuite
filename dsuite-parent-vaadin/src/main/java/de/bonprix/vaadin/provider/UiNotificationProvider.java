package de.bonprix.vaadin.provider;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogButtonAction;
import de.bonprix.vaadin.messagebox.MessageBoxConfiguration;

public interface UiNotificationProvider {

	/**
	 * Opens a html text messageBox with the configured icon, title, plain text
	 * content and buttons.
	 *
	 * @param configuration
	 */
	void showMessageBox(MessageBoxConfiguration configuration);

	/**
	 * Displays a message as info message with the "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	void showInfoMessageBox(String htmlMessage);

	/**
	 * Displays a message as question message with "NO" and "YES" button ("NO"
	 * is the primary button"). When the "YES" button is clicked, the given
	 * yesClickListener will be executed.
	 *
	 * @param htmlMessage
	 *            the message itself
	 * @param listenerYes
	 *            will be called when the "YES" button is clicked
	 */
	@Deprecated
	void showQuestionMessageBox(String htmlMessage, DialogButtonAction listenerYes);

	/**
	 * Displays a message as question message with "NO" and "YES" button. When
	 * the "YES" button is clicked, the given yesClickListener will be executed.
	 * With a definable primary button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 * @param listenerYes
	 *            will be called when the "YES" button is clicked
	 * @param primary
	 *            defines the primary button
	 */
	void showQuestionMessageBox(String htmlMessage, DialogButtonAction listenerYes, DialogButton primary);

	/**
	 * Displays a message as warning message with "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	void showWarningMessageBox(String htmlMessage);

	/**
	 * Displays a message as error message with the "CLOSE" button.
	 *
	 * @param htmlMessage
	 *            the message itself
	 */
	void showErrorMessageBox(String htmlMessage);

	/**
	 * Displays a message as info message.
	 *
	 * @param caption
	 *            the caption of the message
	 */
	void showInfoNotification(String caption);

	/**
	 * Displays a message as info message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param delay
	 *            delay (in ms) before the message disappears
	 */
	void showInfoNotification(String caption, int delay);

	/**
	 * Displays a message as info message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 */
	void showInfoNotification(String caption, String message);

	/**
	 * Displays a message as info message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 * @param delay
	 *            delay (in ms) before the message disappears
	 */
	void showInfoNotification(String caption, String message, int delay);

	/**
	 * Displays a message as warning message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 */
	void showWarningNotification(String caption, String message);

	/**
	 * Displays a message as warning message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 * @param delay
	 *            delay (in ms) before the message disappears
	 */
	void showWarningNotification(String caption, String message, int delay);

	/**
	 * Displays a message as error message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 */
	void showErrorNotification(String caption, String message);

	/**
	 * Displays a message as error message.
	 *
	 * @param caption
	 *            the caption of the message
	 * @param message
	 *            the message itself
	 * @param delay
	 *            delay (in ms) before the message disappears
	 */
	void showErrorNotification(String caption, String message, int delay);

}