package de.bonprix.vaadin.jira;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;
import org.vaadin.addons.screenshot.ScreenshotImage;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import de.bonprix.vaadin.jira.configuration.JiraRestConfiguration;
import de.bonprix.vaadin.jira.model.JiraIssue;

/**
 * Creates a Jira-Issue via Jira Rest Client API
 *
 * @author Torben Meißner
 *
 */
@Component
public class JiraIssueCreationProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueCreationProvider.class);

	public static Issue createJiraIssue(final JiraIssue jiraIssueValues, final ScreenshotImage screenshotImage,
			String issueException) {
		Issue createdIssue = null;

		try {
			final JiraRestClient restClient = JiraIssueCreationProvider.createRestClientFactory();

			IssueInput issueInput = JiraIssueCreationProvider
				.initializeIssueBuilder(jiraIssueValues, issueException, jiraIssueValues.getIssueCreatedBy());

			// create Jira-Issue
			Promise<Issue> issue = restClient.getIssueClient()
				.getIssue(restClient.getIssueClient()
					.createIssue(issueInput)
					.get()
					.getKey());
			createdIssue = issue.get();

			// Add the attachment
			if (screenshotImage != null) {
				restClient.getIssueClient()
					.addAttachment(	createdIssue.getAttachmentsUri(),
									new ByteArrayInputStream(screenshotImage.getImageData()),
									JiraRestConfiguration.JIRA_ATTACHMENT_FILE_NAME);
			}

		} catch (InterruptedException e) {
			JiraIssueCreationProvider.LOGGER
				.error("Could not create Jira-Issue via Jira-Rest-Client! Check Class: JiraIssueCreationProvider", e);
			// clean up state...
			Thread.currentThread()
				.interrupt();
		} catch (ExecutionException e) {
			JiraIssueCreationProvider.LOGGER
				.error("Could not create Jira-Issue via Jira-Rest-Client! Check Class: JiraIssueCreationProvider", e);
		}

		return createdIssue;
	}

	private static JiraRestClient createRestClientFactory() {
		JiraRestClient restClient = null;
		try {
			URI jiraServerUri = new URI(JiraRestConfiguration.JIRA_SERVER_BASE_URI);
			AsynchronousJiraRestClientFactory asynchronousJiraRestClientFactory = new AsynchronousJiraRestClientFactory();
			restClient = asynchronousJiraRestClientFactory
				.createWithBasicHttpAuthentication(	jiraServerUri, JiraRestConfiguration.JIRA_USER,
													JiraRestConfiguration.JIRA_SECURITYWORD);
		} catch (URISyntaxException e) {
			JiraIssueCreationProvider.LOGGER.error(
													"Could not create Jira-Issue via Jira-Rest-Client because JiraRestClient could not be created! Check Class: JiraIssueCreationProvider",
													e);
		}
		return restClient;
	}

	private static IssueInput initializeIssueBuilder(final JiraIssue jiraIssueValues, String issueException,
			String issueCreatedByUser) {

		StringBuilder issueDescriptionBuilder = new StringBuilder();
		issueDescriptionBuilder.append(jiraIssueValues.getIssueDescription());
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder
			.append("--------------------------------------------------------------------------------------------------------------------------------------------");
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append("Exception:");
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		issueDescriptionBuilder.append(System.getProperty("line.separator"));
		if (issueException != null) {
			issueDescriptionBuilder.append(issueException);
		}

		IssueInputBuilder issueBuilder = new IssueInputBuilder().setProjectKey(JiraRestConfiguration.JIRA_PROJECT_KEY)
			.setIssueTypeId(JiraRestConfiguration.JIRA_ERROR_ISSUE_TYPE_ID)
			.setReporterName(issueCreatedByUser)
			.setSummary(jiraIssueValues.getIssueSummary())
			.setDescription(issueDescriptionBuilder.toString());

		return issueBuilder.build();
	}

}
