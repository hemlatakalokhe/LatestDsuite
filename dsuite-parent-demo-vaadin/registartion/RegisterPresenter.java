package de.bonprix.module.registartion;

 

import javax.annotation.Resource;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.Paged;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class RegisterPresenter extends AbstractMvpViewPresenter<RegisterViewImpl>
		implements RegisterView.RegisterPresenter {

  

	
	@Resource
	private  StyleService styleService; 
	
	@Resource
	private  CountryService countryService;

	@Override
	public void init() {
//		
	}

	@Override
	public void onViewEnter() {
//		
	} 
	
	 


}
