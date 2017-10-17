package de.bonprix.vaadin.jira.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/default-jira-rest.properties")
public class JiraRestConfiguration {

	public static final String JIRA_USER = "rest_user";
	public static final String JIRA_SECURITYWORD = "hallo123";
	public static final Long JIRA_ERROR_ISSUE_TYPE_ID = 10103L; // Error
	public static final String JIRA_SERVER_BASE_URI = "https://extranet.bonprix.net/jira/";
	public static final String JIRA_PROJECT_KEY = "HELP"; // HELP-ME Board
	public static final String JIRA_ATTACHMENT_FILE_NAME = "Error-Screenshot.jpg";

}
