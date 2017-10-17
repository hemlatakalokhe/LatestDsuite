package de.bonprix.module.interceptor;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface InterceptorView extends MvpView {

	interface InterceptorPresenter extends MvpViewPresenter<InterceptorViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

	}

	void checkCheckBox(NavigationRequest request);

}
