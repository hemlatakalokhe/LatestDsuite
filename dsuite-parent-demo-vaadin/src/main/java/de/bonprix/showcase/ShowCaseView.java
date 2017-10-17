package de.bonprix.showcase;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface ShowCaseView extends MvpView {

	interface Presenter extends MvpViewPresenter<ShowCaseViewImpl> {

		void openMvpDialog();

		void openMvpWizard();

	}

}
