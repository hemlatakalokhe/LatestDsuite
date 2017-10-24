package de.bonprix.module.regexPractice;

import java.util.List;

import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface RegexView extends MvpView {

	interface Presenter extends MvpViewPresenter<RegexViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

	}

	void checkCheckBox(NavigationRequest request);
	
	void setAllBeans(List<Planperiod> beans);
	
}
