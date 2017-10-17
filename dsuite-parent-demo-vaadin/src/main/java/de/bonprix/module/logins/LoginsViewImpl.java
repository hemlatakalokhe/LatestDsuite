package de.bonprix.module.logins;

 

import java.util.List;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.bonprix.VaadinUI;
 
import de.bonprix.base.demo.dto.Login;
import de.bonprix.module.logins.LoginsView.LoginPresenter;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;
 
 

@SpringView(name = LoginsViewImpl.VIEW_NAME, ui = {VaadinUI.class }, isDefault = false, order = 85)
public class LoginsViewImpl extends AbstractMvpView<LoginPresenter> implements LoginsView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2688782241672861374L;

	public static final String VIEW_NAME = "LogIns"; 

	@javax.annotation.Resource
	private UiNotificationProvider notificationProvider;

	
	private CheckBox checkBox;
	private TextField username;
	private PasswordField password;
	
	private BeanItemComboBox<Login>LoginCombo;

	//private Button login;

	 
	@Override
	protected void initializeUI() {
		this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
		this.LoginCombo=new BeanItemComboBox<>(Login.class);
		LoginCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		LoginCombo.setItemCaptionPropertyId("username");
		
		this.username = FluentUI.textField().caption("Username").get();
		this.password = FluentUI.passwordField().caption("Password").get();
		
		 Button login=FluentUI.button().caption("Login").onClick(e1->
		getPresenter().authorisingUser(username.getValue(), password.getValue())
		).get();
		/*setCompositionRoot( FluentUI.vertical().add(FluentUI.label().value("Sample screen for Planperiod").sizeFull().get())
				.add(this.LoginCombo).sizeFull().get());*/
						 
		setCompositionRoot(FluentUI.vertical().add(username, password,LoginCombo,login).spacing(true).get());
		setSizeFull();
	}

	@Override
	public void checkCheckBox(NavigationRequest request) {
		getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
	}


	 
	@Override
	public void shownotification(String message) {
		
		notificationProvider.showInfoNotification(message);
		
	}

	@Override
	public void setAllBean(List<Login> beans) {
		this.LoginCombo.addAllBeans(beans);
		
	}
	 

}

	
/*	private TextField username;
	private PasswordField password;

	private Button login;
	 
	@Override
	protected void initializeUI() {
		 
		
		username=new TextField("Enter User Name");
		password=new PasswordField("Enter Password");
		
		login=new Button("Submit");
		
		login.addClickListener ( e-> getPresenter().isAuthorized(username.getValue(),password.getValue()));
		
		 
 
		this.login=FluentUI.button().caption("Submit").onClick( e-> getPresenter().isAuthorized(username.getValue(),password.getValue())).get();
	                setCompositionRoot(
						FluentUI.form().add(username,password,login).get());
						 
				setSizeFull();
				 
					}

	@Override	public void checkCheckBox(NavigationRequest request) {
	}

 
*/


