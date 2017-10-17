package de.bonprix.model;

public class JiraIssueHelper {

	private String projectKey;
	private Long issueTypeId;
	private String description;
	private String summary;

	public String getProjectKey() {
		return this.projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public Long getIssueTypeId() {
		return this.issueTypeId;
	}

	public void setIssueTypeId(Long issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
