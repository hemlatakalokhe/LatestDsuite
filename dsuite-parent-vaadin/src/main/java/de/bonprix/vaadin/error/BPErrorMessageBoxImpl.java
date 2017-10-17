package de.bonprix.vaadin.error;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;

import de.bonprix.I18N;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.messagebox.MessageBoxConfigurationBuilder;
import de.bonprix.vaadin.messagebox.MessageBoxIcon;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * The BPErrorMessageBox is a Error MessageBox displaying the error thrown by
 * Application and extends MessageBox.
 *
 * @author r.desai
 *
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BPErrorMessageBoxImpl extends MessageBox implements BPErrorMessageBox {

	private static final long serialVersionUID = 1L;

	private BPErrorMessageBoxPresenter presenter;
	private Throwable error;

	/**
	 * Constructs Error MessageBox with specified config.
	 *
	 */
	public BPErrorMessageBoxImpl() {
		super(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.ERROR)
			.withPlainMessage(I18N.get("ERROR_DIALOG_MESSAGE"))
			// .withPrimaryButton(DialogButton.CREATE_JIRA_ISSUE)
			.withPrimaryButton("MAIL_TO_HELP_ME")
			.withButton(DialogButton.CLOSE)
			.withDetails(true)
			.withWidth(910)
			.build());

		// addButtonListener(DialogButton.CREATE_JIRA_ISSUE, (event) ->
		// this.presenter.openJiraIssueDialog(BPErrorMessageBoxImpl.getErrorDetailsText(this.error)));
		addButtonListener("MAIL_TO_HELP_ME", (event) -> this.presenter.sendHelpMeMail());

	}

	@Override
	protected Component detailsLayout() {
		final TextArea errorText = new TextArea();
		errorText.setValue(BPErrorMessageBoxImpl.getErrorDetailsText(this.error));
		errorText.setStyleName(DSuiteTheme.BP_ERROR_TEXTAREA);
		errorText.setHeight(300, Unit.PIXELS);
		errorText.setWidth(875, Unit.PIXELS);
		return errorText;
	}

	@Override
	public void setPresenter(final BPErrorMessageBoxPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setError(final Throwable error) {
		this.error = error;
	}

	/**
	 * @param error
	 * @return returns error stack trace in String.
	 */
	private static String getErrorDetailsText(final Throwable error) {
		final StringWriter writer = new StringWriter();
		error.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

}
