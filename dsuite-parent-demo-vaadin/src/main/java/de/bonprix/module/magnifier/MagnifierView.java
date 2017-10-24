package de.bonprix.module.magnifier;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface MagnifierView extends MvpView {

	interface Presenter extends MvpViewPresenter<MagnifierViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

		

	}

	void checkCheckBox(NavigationRequest request);

	
}
