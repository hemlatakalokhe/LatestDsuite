package de.bonprix.module.logins;

import java.util.List;

import de.bonprix.base.demo.dto.Login;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface LoginsView extends MvpView {

    interface Presenter extends MvpViewPresenter<LoginsViewImpl> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

        void authorisingUser(String username, String password);

    }

    void checkCheckBox(NavigationRequest request);

    void setAllBean(List<Login> beans);

    void shownotification(String message);
}