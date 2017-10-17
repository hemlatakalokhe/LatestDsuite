package de.bonprix.vaadin.jira;

import de.bonprix.vaadin.jira.model.JiraIssue;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

public interface JiraIssueDialogView<PRESENTER extends JiraIssueDialogView.Presenter> extends MvpDialogView<PRESENTER> {

	interface Presenter extends MvpDialogPresenter {

		void createJiraIssue(JiraIssue jiraIssue);

	}

}
