package de.bonprix.module.login;

import de.bonprix.module.login.ui.LoginViewImpl;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
public interface LoginView extends MvpView {

    interface Presenter extends MvpViewPresenter<LoginViewImpl> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

        /**
         * Authorizing user.
         *
         * @param username the username
         * @param password the password
         */
        void authorizingUser(String username, String password);

        /**
         * Open register dialog.
         */
        void openRegisterDialog();

    }

    void checkCheckBox(NavigationRequest request);

    /**
     * Show notification.
     *
     * @param string the string
     */
    void showNotification(String string);

}
