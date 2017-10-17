package de.bonprix.module.interceptor.child1;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface InterceptorChild1View extends MvpView {

	interface InterceptorPresenter extends MvpViewPresenter<InterceptorChild1ViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

	}

	void checkCheckBox(NavigationRequest request);

}
