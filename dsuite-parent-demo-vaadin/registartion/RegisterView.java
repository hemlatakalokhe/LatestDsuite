package de.bonprix.module.registartion;

 

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface RegisterView extends MvpView {

	interface RegisterPresenter extends MvpViewPresenter<RegisterViewImpl> {

		 
		
		//void update();

	}

 
}