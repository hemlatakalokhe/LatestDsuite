package de.bonprix.vaadin.jira.model;

/**
 * @author Torben Meißner
 */
public class JiraIssue {

	private String issueSummary;
	private String issueDescription;
	private String issueCreatedBy;

	public String getIssueSummary() {
		return this.issueSummary;
	}

	public void setIssueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}

	public String getIssueDescription() {
		return this.issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getIssueCreatedBy() {
		return this.issueCreatedBy;
	}

	public void setIssueCreatedBy(String issueCreatedBy) {
		this.issueCreatedBy = issueCreatedBy;
	}

}
