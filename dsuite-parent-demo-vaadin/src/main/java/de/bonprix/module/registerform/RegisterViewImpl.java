package de.bonprix.module.registerform;

import javax.annotation.Resource;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;

import de.bonprix.VaadinUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SpringView(
    name = RegisterViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class RegisterViewImpl extends AbstractMvpView<RegisterPresenter> implements RegisterView {

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Register";
    private CheckBox checkBox;
    @Resource
    private transient UiNotificationProvider notificatonProvider;

    @Override
    protected void initializeUI() {
        //
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

}
