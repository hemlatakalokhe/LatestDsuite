package de.bonprix.module.logins;

 

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface LoginsView extends MvpView {

	interface LoginPresenter extends MvpViewPresenter<LoginsViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);
		
		
		
		 void authorisingUser(String username, String password);


	}

	void checkCheckBox(NavigationRequest request);
	
	void setAllBean(List<Login> beans);
	
	void shownotification(String message);
}