package de.bonprix.module.interceptor;

import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class InterceptorPresenter extends AbstractMvpViewPresenter<InterceptorViewImpl>
		implements InterceptorView.InterceptorPresenter {

	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		// empty
	}

	@Override
	public void tryNavigateTo(final NavigationRequest request) {
		getView().checkCheckBox(request);
	}

	@Override
	public void proceedCheckBox(final Boolean value, final NavigationRequest request) {
		if (!value) {
			super.tryNavigateTo(request);
			return;
		}

		MessageBox.showQuestion("Do you really want to proceed?", () -> super.tryNavigateTo(request));
	}

}