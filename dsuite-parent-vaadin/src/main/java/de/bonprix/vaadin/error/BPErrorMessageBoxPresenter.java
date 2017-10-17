/**
 *
 */
package de.bonprix.vaadin.error;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.atlassian.jira.rest.client.api.domain.Issue;

import de.bonprix.I18N;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventHandler;
import de.bonprix.vaadin.jira.JiraIssueCreationProvider;
import de.bonprix.vaadin.jira.JiraIssueDialogPresenter;
import de.bonprix.vaadin.jira.event.JiraIssueInformationEvent;
import de.bonprix.vaadin.layout.BpScreenshot;
import de.bonprix.vaadin.mail.MailPopupConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.provider.UiPageProvider;

/**
 * @author cthiel
 * @date 17.11.2016
 *
 */
@SpringPresenter
public class BPErrorMessageBoxPresenter extends AbstractMvpDialogPresenter<BPErrorMessageBox> {

	@Resource
	private ApplicationProvider applicationProvider;

	@Resource
	private EventBus sessionEventBus;

	@Autowired
	private JiraIssueCreationProvider jiraIssueCreationProvider;

	@Autowired
	private UiNotificationProvider notificationProvider;

	@Autowired
	private BpScreenshot bpScreenshot;

	@Autowired
	private UiPageProvider uiPageProvider;

	@Value("${helpme.mailAddress}")
	private String helpmeAddress;

	private Throwable error;
	private String issueException = null;

	@PostConstruct
	public void postConstruct() {
		this.sessionEventBus.addHandler(this);
	}

	@Override
	public void open() {
		if (this.error == null) {
			throw new IllegalStateException("Error has to be set before opening the dialog");
		}

		super.open();
	}

	public void createErrorScreenshot() {
		BPErrorMessageBoxPresenter.this.bpScreenshot.takeScreenshotWithoutDownload();
	}

	public void setError(final Throwable error) {
		this.error = error;
		getView().setError(error);
	}

	/**
	 * Sending a message to helpMe.
	 */
	public void sendHelpMeMail() {
		final StringBuilder result = new StringBuilder();
		for (Throwable cause = this.error; cause != null; cause = cause.getCause()) {
			if (result.length() > 0) {
				result.append("Caused by: ");
			}
			result.append(cause.getClass()
				.getName());
			result.append(": ");
			result.append(cause.getMessage());
			result.append("\n");
		}

		this.uiPageProvider.openDefaultMailClient(new MailPopupConfigurationBuilder().withTo(this.helpmeAddress)
			.withSubject(this.applicationProvider.getApplication()
				.getName())
			.withBody(result.toString())
			.build());
	}

	public void openJiraIssueDialog(String issueException) {
		this.issueException = issueException;
		final JiraIssueDialogPresenter jiraIssueDialogPresenter = createPresenter(JiraIssueDialogPresenter.class);
		jiraIssueDialogPresenter.open();
	}

	@EventHandler
	public void onJiraIssueDialogClosed(final JiraIssueInformationEvent event) {
		Issue issue = this.jiraIssueCreationProvider
			.createJiraIssue(event.getJiraIssue(), this.bpScreenshot.getScreenshotResource(), this.issueException);

		if (issue != null && issue.getSummary() != null && !issue.getSummary()
			.isEmpty()) {
			this.notificationProvider.showInfoNotification(I18N.get("JIRA_ISSUE_CREATED_MESSAGE"));
		}

	}

}
