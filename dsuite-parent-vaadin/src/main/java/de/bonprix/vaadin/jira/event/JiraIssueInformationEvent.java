package de.bonprix.vaadin.jira.event;

import de.bonprix.vaadin.jira.model.JiraIssue;

/**
 * @author Torben Meißner
 */
public class JiraIssueInformationEvent {
	private final JiraIssue jiraIssue;

	public JiraIssueInformationEvent(final JiraIssue jiraIssue) {
		this.jiraIssue = jiraIssue;
	}

	/**
	 * @return the JiraIssue
	 */
	public JiraIssue getJiraIssue() {
		return this.jiraIssue;
	}
}
