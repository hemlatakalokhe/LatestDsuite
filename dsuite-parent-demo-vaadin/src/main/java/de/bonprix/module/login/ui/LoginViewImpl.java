package de.bonprix.module.login.ui;

import javax.annotation.Resource;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.VaadinUI;
import de.bonprix.module.login.LoginPresenter;
import de.bonprix.module.login.LoginView;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * @author h.kalokhe
 *
 */
@SpringView(
    name = LoginViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class LoginViewImpl extends AbstractMvpView<LoginPresenter> implements LoginView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "Login";

    private CheckBox checkBox;

    @Resource
    private transient UiNotificationProvider notificatonProvider;

    @Override
    protected void initializeUI() {
        final PasswordField password;

        final TextField username;

        Button register;

        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        username = FluentUI.textField()
            .caption("User Name")
            .required(true)
            .get();
        password = FluentUI.passwordField()
            .caption("Password")
            .required(true)
            .get();
        register = FluentUI.button()
            .caption("Register")
            .get();
        final Button saveButton = FluentUI.button()
            .captionKey("Login")
            .onClick(e -> getPresenter().authorizingUser(username.getValue(), password.getValue()))
            .style(ValoTheme.BUTTON_PRIMARY)
            .get();
        register.addClickListener(event -> getPresenter().openRegisterDialog());

        setCompositionRoot(FluentUI.vertical()
            .add(FluentUI.form()
                .add(username, password, FluentUI.horizontal()
                    .add(saveButton, register)
                    .spacing()
                    .get())
                .spacing(true)
                .get())
            .get());

    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void showNotification(final String message) {
        //
    }

}
