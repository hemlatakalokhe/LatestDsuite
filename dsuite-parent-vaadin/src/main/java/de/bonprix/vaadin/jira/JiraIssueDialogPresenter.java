package de.bonprix.vaadin.jira;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import de.bonprix.security.PrincipalProviderImpl;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.jira.event.JiraIssueInformationEvent;
import de.bonprix.vaadin.jira.model.JiraIssue;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;

/**
 * @author Torben Meißner
 * 
 *         Presenter of the mvp-dialog to insert Jira-Issue details like
 *         description ...
 * 
 */
@SpringPresenter
public class JiraIssueDialogPresenter extends AbstractMvpDialogPresenter<JiraIssueDialogView>
		implements JiraIssueDialogView.Presenter {

	@Resource
	private EventBus sessionEventBus;

	@Autowired
	private PrincipalProviderImpl principalProviderImpl;

	public void createJiraIssue(JiraIssue jiraIssue) {
		jiraIssue.setIssueCreatedBy(this.principalProviderImpl.getAuthenticatedPrincipal()
			.getName());
		this.sessionEventBus.fireEvent(new JiraIssueInformationEvent(jiraIssue));

	}

}