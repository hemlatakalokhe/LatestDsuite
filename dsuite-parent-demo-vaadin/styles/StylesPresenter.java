package de.bonprix.module.styles;

 

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
public class StylesPresenter extends AbstractMvpViewPresenter<StylesViewImpl>
		implements de.bonprix.module.styles.StylesView.StylesPresenter {

  

	
	@Resource
	private  StyleService styleService; 
	
	@Resource
	private  CountryService countryService; 
	
	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		 
		getView().setAllBean(this.countryService.findAll(new Paged(0, 100)),this.styleService.findAll(new Paged(0, 100)));
		// empty
	}

	@Override
	public void tryNavigateTo(final NavigationRequest request) {
		//getView().checkCheckBox(request);
	}

	@Override
	public void proceedCheckBox(final Boolean value, final NavigationRequest request) {
		if (!value) {
			super.tryNavigateTo(request);
			return;
		}

		MessageBox.showQuestion("Do you really want to proceed?", () -> super.tryNavigateTo(request));
	}

	@Override
	public void addcomponent(String styleNo,String styleDesc,Country country) {
	
	Style style = new Style();
	style.setStyleNo(styleNo);
	style.setDesc(styleDesc);
    style.setCountry(country);	
	
	styleService.saveStyle(style);
	getView().shownotification("save success");
		
	}

 

	@Override
	public void add(String string) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void update() {
		Style style=styleService.findById(id); 
		addcomponent(style.getStyleNo(), style.getDesc(),style.getCountry());
	}*/

}
