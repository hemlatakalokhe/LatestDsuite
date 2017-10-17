package de.bonprix.vaadin.jira;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import de.bonprix.I18N;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.jira.model.JiraIssue;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;

/**
 * @author Torben Meißner
 * 
 *         ViewImpl of the mvp-dialog to insert Jira-Issue details like
 *         description ...
 * 
 */
@SpringViewComponent
public class JiraIssueDialogViewImpl extends AbstractMvpDialogView<JiraIssueDialogPresenter>
		implements JiraIssueDialogView<JiraIssueDialogPresenter> {

	private static final long serialVersionUID = 1L;

	// Create FieldGroup for Jira-Issue-Creation-Dialog
	public JiraIssueDialogViewImpl() {
		super(new DialogConfigurationBuilder().withHeadline("JIRA_ISSUE_DIALOG_HEADLINE")
			.withSubline("JIRA_ISSUE_DIALOG_SUBLINE")
			.withHeight(500)
			.withWidth(800)
			.withClosable(true)
			.withCloseOnAnyButton(true)
			.withModal(true)
			.withPrimaryButton(DialogButton.OK)
			.withButton(DialogButton.CANCEL)
			.build());
	}

	@Override
	protected Component layout() {

		TextField issueSummary = new TextField(I18N.get("JIRA_ISSUE_SUMMARY"));
		issueSummary.setSizeFull();
		issueSummary.setRequired(true);
		issueSummary.setImmediate(true);

		TextArea issueDescription = new TextArea();
		issueDescription.setCaption(I18N.get("JIRA_ISSUE_DESCRIPTION"));
		issueDescription.setSizeFull();
		issueDescription.setRequired(true);
		issueDescription.setImmediate(true);

		// add a click listener to the OK button
		addButtonListener(DialogButton.OK, event -> {

			JiraIssue jiraIssue = new JiraIssue();
			jiraIssue.setIssueSummary(issueSummary.getValue());
			jiraIssue.setIssueDescription(issueDescription.getValue());
			getPresenter().createJiraIssue(jiraIssue);

			close();
		});

		addButtonListener(DialogButton.CANCEL, event -> {
			close();
		});

		return FluentUI.vertical()
			.margin()
			.spacing()
			.add(issueSummary)
			.add(issueDescription)
			.get();

	}

}
