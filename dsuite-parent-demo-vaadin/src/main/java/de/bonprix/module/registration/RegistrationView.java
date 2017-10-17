package de.bonprix.module.registration;

 

import java.util.List;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.UserData;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface RegistrationView extends MvpView {

	interface RegisterPresenter extends MvpViewPresenter<RegistrationViewImpl> {

		 
		
		//void update();

	}
	void setAllBean(List<UserData> beans);
	
	void setBean(UserData userData);

 
}