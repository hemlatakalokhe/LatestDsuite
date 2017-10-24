package de.bonprix.module.sample;

import javax.annotation.Resource;

import de.bonprix.base.demo.service.CountryService;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class SamplePresenter extends AbstractMvpViewPresenter<SampleViewImpl> implements SampleView.Presenter {

    @Resource
    private CountryService countryService;

    @Override
    public void init() {
        getView().setAllCountryBeans(this.countryService.findAll());
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

}