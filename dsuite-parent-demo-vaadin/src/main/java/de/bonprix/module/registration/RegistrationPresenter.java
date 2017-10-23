package de.bonprix.module.registration;

import javax.annotation.Resource;

import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.UserDataService;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class RegistrationPresenter extends AbstractMvpViewPresenter<RegistrationViewImpl> implements RegistrationView.RegisterPresenter {

    @Resource
    private UserDataService userDataService;

    @Resource
    private CountryService countryService;

    @Override
    public void init() {
        //
    }

    @Override
    public void onViewEnter() {
        //
    }

}
