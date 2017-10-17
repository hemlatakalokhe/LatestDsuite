package de.bonprix.vaadin.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.bonprix.vaadin.error.BPErrorMessageBoxImpl;
import de.bonprix.vaadin.provider.ui.BlockingWindow;

@UIScope
@Component
public class UiDialogProviderImpl implements UiDialogProvider {

	private static final String DEFAULT_BLOCKING_MESSAGE_KEY = "PLEASE_WAIT";

	@Autowired
	private ApplicationContext applicationContext;

	private Dialog currentDialog = null;

	@Override
	public void openDialog(final Dialog dialog) {
		tryCastToWindowOrThrow(dialog);

		if (!isAllowedToOpen(dialog)) {
			throw new IllegalStateException("Opening this window is not allowed");
		}

		internalCloseCurrentDialog();
		internalOpenDialog(dialog);
	}

	private boolean isAllowedToOpen(final Dialog dialog) {
		if (isErrorDialog(dialog)) {
			return true;
		}

		if (this.currentDialog == null || !isAttached(this.currentDialog)) {
			return true;
		}

		if (!isBlockingDialog(this.currentDialog)) {
			return true;
		}

		return false;
	}

	private void internalCloseCurrentDialog() {
		if (this.currentDialog != null && isAttached(this.currentDialog)) {
			((Window) this.currentDialog).close();
			this.currentDialog = null;
		}
	}

	private void internalOpenDialog(final Dialog dialog) {
		UI	.getCurrent()
			.addWindow((Window) dialog);

		this.currentDialog = dialog;

		if (isBlockingDialog(dialog)) {
			UI	.getCurrent()
				.setPollInterval(500);
			((Window) this.currentDialog).addDetachListener((event) -> UI	.getCurrent()
																			.setPollInterval(-1));
		}
	}

	private void tryCastToWindowOrThrow(final Dialog dialog) {
		if (!(dialog instanceof Window)) {
			throw new IllegalArgumentException("The given view does not inherit from com.vaadin.ui.Window");
		}
	}

	@Override
	public void blockUi() {
		blockUi(UiDialogProviderImpl.DEFAULT_BLOCKING_MESSAGE_KEY);
	}

	@Override
	public void blockUi(final String messageKey) {
		final BlockingWindow window = this.applicationContext.getBean(BlockingWindow.class);
		window.setMessageKey(messageKey);

		blockUi(window);
	}

	@Override
	public void blockUi(final BlockingDialog dialog) {
		if (uiIsBlocked()) {
			throw new IllegalStateException("UI is already blocked by a different blocking window");
		}

		openDialog(dialog);
	}

	@Override
	public void unblockUi() {
		if (!uiIsBlocked()) {
			return;
		}

		internalCloseCurrentDialog();
	}

	@Override
	public boolean uiIsBlocked() {
		return (this.currentDialog != null && isBlockingDialog(this.currentDialog) && isAttached(this.currentDialog));
	}

	@Override
	public void closeCurrentDialog() {
		if (uiIsBlocked()) {
			throw new IllegalStateException("Cannot clsoe a blocking window with closeCurrentDialog, use unblockUi");
		}

		internalCloseCurrentDialog();
	}

	private boolean isErrorDialog(final Dialog dialog) {
		return dialog instanceof BPErrorMessageBoxImpl;

	}

	private boolean isBlockingDialog(final Dialog dialog) {
		return dialog instanceof BlockingDialog;
	}

	private boolean isAttached(final Dialog dialog) {
		return ((Window) dialog).isAttached();
	}

}
