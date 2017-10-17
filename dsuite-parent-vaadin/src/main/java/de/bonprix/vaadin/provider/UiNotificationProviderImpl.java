package de.bonprix.vaadin.provider;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.vaadin.server.Page;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogButtonAction;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.messagebox.MessageBoxConfiguration;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 *
 */
@UIScope
@Component
public class UiNotificationProviderImpl implements UiNotificationProvider {

	/**
	 * This is a marker interface for dialogs. The public interface of this
	 * class should be Vaadin independent to make it more flexible for UI
	 * technology changes. The current method of openDialog() expects a MvpView
	 * that is in fact a window and this is done to keep this class' interface
	 * Vaadin-free. However the MvpView interface is not exactly a marker
	 * interface. It forces the user to implement methods and have an observer,
	 * that again is a subclass of an interface. So for this reason this marker
	 * interface here does exactly what is needed here. It keeps the public
	 * interface Vaadin-free and does not force you do implement your dialog in
	 * a specific way.
	 *
	 * @author Axel Meier, akquinet engineering GmbH
	 *
	 */

	@Resource
	private Environment environment;

	@Override
	public void showInfoMessageBox(final String htmlMessage) {
		MessageBox.showInfo(htmlMessage);
	}

	@Override
	public void showQuestionMessageBox(final String htmlMessage, final DialogButtonAction listenerYes) {
		MessageBox.showQuestion(htmlMessage, listenerYes);
	}

	@Override
	public void showQuestionMessageBox(final String htmlMessage, final DialogButtonAction listenerYes,
			DialogButton primary) {
		MessageBox.showQuestion(htmlMessage, listenerYes, primary);
	}

	@Override
	public void showWarningMessageBox(final String htmlMessage) {
		MessageBox.showWarning(htmlMessage);
	}

	@Override
	public void showErrorMessageBox(final String htmlMessage) {
		MessageBox.showError(htmlMessage);
	}

	@Override
	public void showMessageBox(final MessageBoxConfiguration configuration) {
		MessageBox.show(configuration);
	}

	@Override
	public void showInfoNotification(final String caption) {
		showInfoNotification(caption, null);
	}

	@Override
	public void showInfoNotification(final String caption, final String message) {
		showInfoNotification(caption, message, 2000);
	}

	@Override
	public void showInfoNotification(final String caption, final int delay) {
		showInfoNotification(caption, null, delay);
	}

	@Override
	public void showInfoNotification(final String caption, final String message, final int delay) {
		showNotification(caption, message, delay, Type.HUMANIZED_MESSAGE);
	}

	@Override
	public void showWarningNotification(final String caption, final String message) {
		showWarningNotification(caption, message, 2000);
	}

	@Override
	public void showWarningNotification(final String caption, final String message, final int delay) {
		showNotification(caption, message, delay, Type.WARNING_MESSAGE);
	}

	@Override
	public void showErrorNotification(final String caption, final String message) {
		showErrorNotification(caption, message, 2000);
	}

	@Override
	public void showErrorNotification(final String caption, final String message, final int delay) {
		showNotification(caption, message, delay, Type.ERROR_MESSAGE);
	}

	private void showNotification(final String caption, final String message, final int delay, final Type type) {
		final Notification notification = new Notification(caption, type);
		notification.setDescription(message);
		notification.setDelayMsec(delay);
		notification.show(Page.getCurrent());
	}
}
