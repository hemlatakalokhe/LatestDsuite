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

@SpringView(
    name = RegistrationViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 85)
public class RegistrationViewImpl extends AbstractMvpView<RegistrationPresenter> implements RegistrationView {

    private static final long serialVersionUID = 1L;

    public static final String VIEW_NAME = "Registration";

    @Resource
    private transient UiNotificationProvider notificationProvider;

    @Override
    protected void initializeUI() {

        TextField name;

        TextField email;

        PasswordField password;

        PasswordField confirmPassword;

        TextField mobileNo;

        Button submit;

        BeanFieldGroup<UserData> registerFielfGroup;

        registerFielfGroup = new BeanFieldGroup<>(UserData.class);

        name = FluentUI.textField()
            .caption("Name")
            .get();
        registerFielfGroup.bind(name, "username");

        password = FluentUI.passwordField()
            .caption("Password")
            .bind(registerFielfGroup, "password")
            .get();
        password.addValidator(new RegexpValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.+=*[@#$%^&+=])(?=\\S+$).{5,}$", "contain expression"));

        confirmPassword = FluentUI.passwordField()
            .caption("Confirm Password")
            .bind(registerFielfGroup, "cpassword")
            .get();

        email = FluentUI.textField()
            .caption("Email")
            .bind(registerFielfGroup, "email")
            .get();
        email.addValidator(new EmailValidator("wrong email"));

        mobileNo = FluentUI.textField()
            .caption("mobile number")
            .bind(registerFielfGroup, "mobileNo")
            .get();
        mobileNo.addValidator(new RegexpValidator("^(?=.+[0-9]).{5,}", "mobileno"));

        submit = new Button("submit");
        submit.addClickListener(event -> {
            name.addValidator(new StringLengthValidator("name wrong", 1, 16, true));
            confirmPassword.addValidator(new RegexpValidator(password.getValue(), "cpassword"));

            if (registerFielfGroup.isValid()) {
                this.notificationProvider.showInfoMessageBox("valid fieldgroup");
                this.notificationProvider.showInfoNotification(registerFielfGroup.getFields()
                    .toString());

                try {
                    registerFielfGroup.commit();
                }
                catch (final CommitException e) {
                    //
                }

            }
        });

        setCompositionRoot(FluentUI.form()
            .add(name, password, confirmPassword, email, mobileNo, submit)
            .get());
        setSizeFull();

    }

    @Override
    public void setAllBean(final List<UserData> beans) {
        //
    }

    @Override
    public void setBean(final UserData userData) {
        //

    }

}
