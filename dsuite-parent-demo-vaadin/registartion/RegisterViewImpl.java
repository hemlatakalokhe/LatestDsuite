package de.bonprix.module.registartion;

 

import static org.mockito.Matchers.charThat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PasswordValidationCallback;

import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState.TableSelectionMode;

import com.fasterxml.jackson.core.sym.Name;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.CompositeValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Item;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.UserData;
import de.bonprix.module.styles.StylesView.StylesPresenter;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.data.converter.StringToIntegerConverter;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;
 
 

@SpringView(name = RegisterViewImpl.VIEW_NAME, ui = {VaadinUI.class }, isDefault = false, order = 85)
public class RegisterViewImpl extends AbstractMvpView<RegisterPresenter> implements RegisterView {

	 

	public static final String VIEW_NAME = "Register";

	@Resource
	private UiNotificationProvider notificationProvider;

	//private FieldGroup fieldGroup;

	private TextField name;

	private TextField email;

	private PasswordField password;

	private PasswordField confirmPassword;

	private TextField mobileNo;

    private Button  submit;
    
     
    private BeanItemFieldGroup <UserData> registerFielfGroup;
	
    @SuppressWarnings("deprecation")
	@Override
	protected void initializeUI() {
	
		
		this.registerFielfGroup=new BeanItemFieldGroup<UserData>(UserData.class);
		
		this.name=FluentUI.textField().caption("Name").bind(this.registerFielfGroup, "username").get();
		name.addValidator(new StringLengthValidator("name wrong",1, 6, true));
		
		this.password=FluentUI.passwordField().caption("Password").bind(this.registerFielfGroup, "password").get();
	    password.addValidator(new RegexpValidator( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$" , 
				 "contain expression"));
		
	    this.confirmPassword=FluentUI.passwordField().caption("Confirm Password").bind(this.registerFielfGroup, "cpassword").get();
	    
	    this.email=FluentUI.textField().caption("Email").bind(this.registerFielfGroup, "email").get();
		email.addValidator(new EmailValidator("wrong email")); 
        
		this.mobileNo=FluentUI.textField().caption("mobile number").bind(this.registerFielfGroup, "mobileNo").get();
	    mobileNo.addValidator(new RegexpValidator("^(?=.*[0-9]).{10,}", "mobileno"));
		
	 
	    
		
		this.submit=new Button("submit");
		 
	
		submit.addClickListener(event ->
		{
			 confirmPassword.addValidator(new RegexpValidator(password.getValue(), "cpassword"));
			 
			 System.out.println(mobileNo.getValue());
			 System.out.println("mobileNo.isValid()"+mobileNo.isValid());
			 System.out.println("cpassword"+confirmPassword);
			 System.out.println("cpassword.isValid()"+confirmPassword.isValid());
			 System.out.println("password.isValid()"+ password.isValid());
			 System.out.println("name.isValid()"+name.isValid());
			 System.out.println("email.isValid()"+email.isValid());
			
			 
			 
			if( this.registerFielfGroup.isValid())
				{ 
				 this.notificationProvider.showInfoMessageBox("valid fieldgroup");
				 }
			
			
		this.notificationProvider.showInfoNotification(this.registerFielfGroup.getFields().toString());
		}
		);
		 
		
		setCompositionRoot(FluentUI.form().add(name,password,confirmPassword,email,mobileNo,submit).get());
		setSizeFull();
		
	 

 
			
			 
				
				  
				
				
			
		 
			}


	

   /* this.selectedModel = new PartnerGroup();
    this.fieldGroup = new BeanFieldGroup<>(PartnerGroup.class);
    this.fieldGroup.setItemDataSource(this.selectedModel);
    getButton(DialogButton.SAVE).setEnabled(false);
    initialDescriptionTextArea();

    return FluentUI.form()
        .add(FluentUI.textField()
            .bind(this.fieldGroup, "name")
            .captionKey(I18N.get("GROUP_NAME"))
            .width(TEXTFIELD_WIDTH, Unit.PERCENTAGE)
            .validator(new StringLengthValidator(I18N.get("INVALID_INPUT"), NAME_MIN_LENGTH, NAME_MAX_LENGTH, false))
            .required(true)
            .onValueChange(e -> getButton(DialogButton.SAVE).setEnabled(this.fieldGroup.isValid()))
            .get())
        .add(this.descriptionTextArea)
        .margin(true)
        .spacing(true)
        .get();
}
 */
 
	/*name.addValidator(new Validator() {
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		if(value.equals("ABC"))
			System.out.println("Name is correct");
			
	}
});*/

	 

}


	 
	
	
	