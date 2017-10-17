package de.bonprix.module.registerform;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface RegisterView extends MvpView {

	interface RegisterPresenter extends MvpViewPresenter<RegisterViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

		

	}

	void checkCheckBox(NavigationRequest request);

	
}
