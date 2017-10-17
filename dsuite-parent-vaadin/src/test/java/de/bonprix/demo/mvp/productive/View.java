package de.bonprix.demo.mvp.productive;

import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface View {

	interface Presenter extends MvpViewPresenter<ViewImpl> {
		void presenterFunction();
	}

	void viewImplFunction();

}
