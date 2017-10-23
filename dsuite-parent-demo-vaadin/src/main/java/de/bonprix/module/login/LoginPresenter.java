package de.bonprix.module.login;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import de.bonprix.base.demo.service.LoginService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.DialogModeEvent;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.login.ui.LoginViewImpl;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.module.style.ui.StyleViewImpl;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNavigationProvider;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * @author h.kalokhe
 *
 */
@SpringPresenter
public class LoginPresenter extends AbstractMvpViewPresenter<LoginViewImpl> implements LoginView.LoginPresenter {

    @Resource
    private StyleService style;

    @Resource
    private LoginService login;

    @Resource
    private UiNotificationProvider notificationProvider;

    @Resource
    private UiNavigationProvider navigationProvider;

    @Resource
    private EventBus localEventBus;

    @PostConstruct
    public void initialize() {
        this.localEventBus.addHandler(this);
    }

    @Override
    public void init() {
        //
    }

    @Override
    public void onViewEnter() {
        //
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

        MessageBox.showQuestion("Do you really want to proceed?", () -> super.tryNavigateTo(request), DialogButton.PRINT);
    }

    @Override
    public void authorizingUser(final String username, final String password) {
        final boolean validate = this.login.validateUser(username, password);

        if (!validate) {
            this.notificationProvider.showInfoNotification("Authentication", "Login Successful");
            this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME);
        }
        else {
            this.notificationProvider.showErrorNotification("Authentication", "Login Failed");

        }
    }

    @Override
    public void openRegisterDialog() {
        final UpdateMvpDialogPresenter updateMvpDialogPresenter = createPresenter(UpdateMvpDialogPresenter.class);
        this.localEventBus.fireEvent(new DialogModeEvent(Mode.REGISTER));
        updateMvpDialogPresenter.open();
    }
}