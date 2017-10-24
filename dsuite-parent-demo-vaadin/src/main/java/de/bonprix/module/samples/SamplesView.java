package de.bonprix.module.samples;

 

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface SamplesView extends MvpView {

	interface Presenter extends MvpViewPresenter<SamplesViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);

	}

	void checkCheckBox(NavigationRequest request);
	
	void setAllBean(List<Planperiod> beans);
}