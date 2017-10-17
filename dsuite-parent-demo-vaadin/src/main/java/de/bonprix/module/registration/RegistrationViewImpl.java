package de.bonprix.module.registration;

import java.util.List;

import javax.annotation.Resource;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.UserData;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SpringView(name = RegistrationViewImpl.VIEW_NAME, ui = {VaadinUI.class }, isDefault = false, order = 85)
public class RegistrationViewImpl extends AbstractMvpView<RegistrationPresenter> implements RegistrationView {

    private static final long serialVersionUID = 1L;

    public static final String VIEW_NAME = "Registration";

    @Resource
    private UiNotificationProvider notificationProvider;

    private TextField name;

    private TextField email;

    private PasswordField password;

    private PasswordField confirmPassword;

    private TextField mobileNo;

    private Button  submit;

    private BeanFieldGroup <UserData> registerFielfGroup;

    @Override
    protected void initializeUI() {


        this.registerFielfGroup=new BeanFieldGroup<>(UserData.class);


        this.name=FluentUI.textField().caption("Name").get();
        this.registerFielfGroup.bind(this.name, "username");

        this.password=FluentUI.passwordField().caption("Password").bind(this.registerFielfGroup, "password").get();
        this.password.addValidator(new RegexpValidator( "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.+=*[@#$%^&+=])(?=\\S+$).{5,}$" ,
                "contain expression"));

        this.confirmPassword=FluentUI.passwordField().caption("Confirm Password").bind(this.registerFielfGroup, "cpassword").get();

        this.email=FluentUI.textField().caption("Email").bind(this.registerFielfGroup, "email").get();
        this.email.addValidator(new EmailValidator("wrong email"));

        this.mobileNo=FluentUI.textField().caption("mobile number").bind(this.registerFielfGroup, "mobileNo").get();
        this.mobileNo.addValidator(new RegexpValidator("^(?=.+[0-9]).{5,}", "mobileno"));

        this.submit=new Button("submit");
        this.submit.addClickListener(event ->
        {
            this.name.addValidator(new StringLengthValidator("name wrong",1,16, true));
            this.confirmPassword.addValidator(new RegexpValidator(this.password.getValue(), "cpassword"));

            if(this.registerFielfGroup.isValid())
            {
                this.notificationProvider.showInfoMessageBox("valid fieldgroup");
                this.notificationProvider.showInfoNotification(this.registerFielfGroup.getFields().toString());

                try {
                    this.registerFielfGroup.commit();
                    System.out.println("commited");
                } catch (final CommitException e) {
                    System.out.println("exception in commit");
                    e.printStackTrace();
                }


            }
        }
                );


        setCompositionRoot(FluentUI.form().add(this.name,this.password,this.confirmPassword,this.email,this.mobileNo,this.submit).get());
        setSizeFull();

    }

    @Override
    public void setAllBean(final List<UserData> beans) {
        // registerFielfGroup.setItemDataSource(new UserData());
    }

    @Override
    public void setBean(final UserData userData) {
        // TODO Auto-generated method stub

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





