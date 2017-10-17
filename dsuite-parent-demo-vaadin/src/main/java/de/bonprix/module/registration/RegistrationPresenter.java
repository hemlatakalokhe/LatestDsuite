package de.bonprix.module.registration;

 

import javax.annotation.Resource;

import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.base.demo.service.UserDataService;
import de.bonprix.model.Paged;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class RegistrationPresenter extends AbstractMvpViewPresenter<RegistrationViewImpl>
		implements RegistrationView.RegisterPresenter {

  

	
	@Resource
	private  UserDataService userDataService; 
	
	@Resource
	private  CountryService countryService;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onViewEnter() {
		 
		//getView().setAllBean(this.userDataService.findAll(new Paged(0, 100)));
	} 
	
	 

	/*@Override
	public void update() {
		Style style=styleService.findById(id); 
		addcomponent(style.getStyleNo(), style.getDesc(),style.getCountry());
	}*/

}
