package de.bonprix.vaadin.jira;

import java.net.URI;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.vaadin.jira.configuration.JiraRestConfiguration;
import de.bonprix.vaadin.jira.model.JiraIssue;

@PrepareForTest({ AsynchronousJiraRestClientFactory.class, JiraIssueCreationProvider.class })
public class JiraIssueCreationProviderTest extends StaticMethodAwareUnitTest {

	@Test
	public void testCreateJiraIssue() throws Exception {
		final JiraIssue jiraIssue = new JiraIssue();
		jiraIssue.setIssueSummary("issueSummary");
		jiraIssue.setIssueDescription("issueDescription");
		jiraIssue.setIssueCreatedBy(JiraRestConfiguration.JIRA_USER);

		final String errorText = "TestErrorText";

		// Mock JiraRestClient
		JiraRestClient mockJiraRestClient = Mockito.mock(JiraRestClient.class);
		AsynchronousJiraRestClientFactory mockedFactory = Mockito.mock(AsynchronousJiraRestClientFactory.class);

		PowerMockito.whenNew(AsynchronousJiraRestClientFactory.class)
			.withNoArguments()
			.thenReturn(mockedFactory);
		Mockito
			.when(mockedFactory.createWithBasicHttpAuthentication(	Mockito.any(URI.class),
																	Mockito.eq(JiraRestConfiguration.JIRA_USER), Mockito
																		.eq(JiraRestConfiguration.JIRA_SECURITYWORD)))
			.thenReturn(mockJiraRestClient);

		// Mock User
		User mockUser = Mockito.mock(User.class);
		UserRestClient mockUserRestClient = Mockito.mock(UserRestClient.class);
		Promise<User> mockPromisUser = Mockito.mock(Promise.class);

		Mockito.when(mockJiraRestClient.getUserClient())
			.thenReturn(mockUserRestClient);
		Mockito.when(mockUserRestClient.getUser(Mockito.eq(jiraIssue.getIssueCreatedBy())))
			.thenReturn(mockPromisUser);
		Mockito.when(mockPromisUser.get())
			.thenReturn(mockUser);

		// create the promisBasicIssue
		IssueRestClient mockIssueRestClient = Mockito.mock(IssueRestClient.class);
		Promise<BasicIssue> mockPromiseBasicIssue = Mockito.mock(Promise.class);
		Mockito.when(mockJiraRestClient.getIssueClient())
			.thenReturn(mockIssueRestClient);
		Mockito.when(mockIssueRestClient.createIssue(Mockito.any()))
			.thenReturn(mockPromiseBasicIssue);

		// create the BasicIssue
		BasicIssue mockBasicIssue = Mockito.mock(BasicIssue.class);
		Mockito.when(mockPromiseBasicIssue.get())
			.thenReturn(mockBasicIssue);

		// create the PromisIssue
		Promise<Issue> mockPromiseIssue = Mockito.mock(Promise.class);
		Mockito.when(mockIssueRestClient.getIssue(mockBasicIssue.getKey()))
			.thenReturn(mockPromiseIssue);

		Issue expected = new Issue(jiraIssue.getIssueSummary(), new URI("testSelUri"), null, null, null, null, null,
				jiraIssue.getIssueDescription(), null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(mockPromiseIssue.get())
			.thenReturn(expected);

		Issue actual = JiraIssueCreationProvider.createJiraIssue(jiraIssue, null, errorText);

		Assert.assertEquals(actual.getDescription(), expected.getDescription());
		Assert.assertEquals(actual.getSummary(), expected.getSummary());
		Assert.assertEquals(expected.getSelf()
			.getPath(), "testSelUri");

	}

}
