package de.bonprix.module.styles;

 

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface StylesView extends MvpView {

	interface StylesPresenter extends MvpViewPresenter<StylesViewImpl> {

		void proceedCheckBox(Boolean value, NavigationRequest request);
		
		public void addcomponent(String styleNo,String styleDesc,Country country);
		
		void add(String string);
		
		//void update();

	}

	void checkCheckBox(NavigationRequest request);
	
	void setAllBean(List<Country> beans,List<Style>beanstyle);
	
	void addFields();
	
	void shownotification(String message);
}