package de.bonprix.vaadin.jira;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.security.PrincipalProviderImpl;
import de.bonprix.user.dto.Principal;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.jira.event.JiraIssueInformationEvent;
import de.bonprix.vaadin.jira.model.JiraIssue;

public class JiraIssueDialogPresenterTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private JiraIssueDialogPresenter jiraIssueDialogPresenter;

	@Mock
	private EventBus sessionEventBus;

	@Mock
	private PrincipalProviderImpl principalProviderImpl;

	@Mock
	JiraIssueDialogViewImpl view;

	@Test
	public void testCreateJiraIssue() throws Exception {
		// Mock declarations
		Principal mockPrincipal = Mockito.mock(Principal.class);
		JiraIssue mockJiraIssue = Mockito.mock(JiraIssue.class);
		Mockito.when(this.principalProviderImpl.getAuthenticatedPrincipal())
			.thenReturn(mockPrincipal);
		Mockito.when(mockPrincipal.getName())
			.thenReturn("myTestName");

		// test run (of the productive method (method under test))
		this.jiraIssueDialogPresenter.createJiraIssue(mockJiraIssue);

		// Assertions && verifications after test run
		Mockito.verify(this.principalProviderImpl)
			.getAuthenticatedPrincipal();
		Mockito.verify(mockJiraIssue)
			.setIssueCreatedBy("myTestName");
		Mockito.verify(this.sessionEventBus)
			.fireEvent(Mockito.any(JiraIssueInformationEvent.class));
	}

}
