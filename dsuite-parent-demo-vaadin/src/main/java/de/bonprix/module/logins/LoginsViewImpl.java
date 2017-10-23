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

@SpringView(
    name = LoginsViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 85)
public class LoginsViewImpl extends AbstractMvpView<LoginPresenter> implements LoginsView {

    /**
     * 
     */
    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "LogIns";

    @javax.annotation.Resource
    private transient UiNotificationProvider notificationProvider;

    private CheckBox checkBox;
    private BeanItemComboBox<Login> LoginCombo;

    @Override
    protected void initializeUI() {
        TextField username;
        PasswordField password;

        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        this.LoginCombo = new BeanItemComboBox<>(Login.class);
        this.LoginCombo.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        this.LoginCombo.setItemCaptionPropertyId("username");

        username = FluentUI.textField()
            .caption("Username")
            .get();
        password = FluentUI.passwordField()
            .caption("Password")
            .get();

        final Button login = FluentUI.button()
            .caption("Login")
            .onClick(e1 -> getPresenter().authorisingUser(username.getValue(), password.getValue()))
            .get();

        setCompositionRoot(FluentUI.vertical()
            .add(username, password, this.LoginCombo, login)
            .spacing(true)
            .get());
        setSizeFull();
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void shownotification(final String message) {

        this.notificationProvider.showInfoNotification(message);

    }

    @Override
    public void setAllBean(final List<Login> beans) {
        this.LoginCombo.addAllBeans(beans);

    }

}
