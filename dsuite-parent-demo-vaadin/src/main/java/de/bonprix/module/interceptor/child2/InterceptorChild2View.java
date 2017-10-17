package de.bonprix.module.interceptor.child2;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface InterceptorChild2View extends MvpView {

	interface InterceptorPresenter extends MvpViewPresenter<InterceptorChild2ViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

	}

	void checkCheckBox(NavigationRequest request);

}
