package de.bonprix.module.sample;

import java.util.List;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface SampleView extends MvpView {

    interface SamplePresenter extends MvpViewPresenter<SampleViewImpl> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

    }

    void checkCheckBox(NavigationRequest request);

    void setAllBeans(List<Planperiod> beans);

    void setAllCountryBeans(List<Country> beans);

}
