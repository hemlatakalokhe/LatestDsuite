package de.bonprix.module.logins;

 

import javax.annotation.Resource;

import de.bonprix.base.demo.service.LoginService;
import de.bonprix.model.Paged;
import de.bonprix.module.style.StyleViewImpl;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class LoginsPresenter extends AbstractMvpViewPresenter<LoginsViewImpl>
		implements LoginsView.LoginPresenter {

  

	
	@Resource
	private  LoginService loginService; 
	
	
	
	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		getView().setAllBean(this.loginService.findAll(new Paged(0, 100))); 
		 
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

	@Override
	public void authorisingUser(String username, String password) {
		
		boolean exist = loginService.validateUser(username, password);
		if (exist) {

			getView().shownotification("Login success");
		//getView().tryNavigateTo(new NavigationRequest(StyleViewImpl.VIEW_NAME));
		} else {
			getView().shownotification("Login fail");
		}
		
	
	}

}