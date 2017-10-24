package de.bonprix.module.update;

import java.util.List;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface UpdateView extends MvpView {

    interface Presenter extends MvpViewPresenter<UpdateView> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

        void updateStyle(Style style);

        void saveUpdatedStyle(String styleNo, String styleDesc, Country country);

    }

    void checkCheckBox(NavigationRequest request);

    void setFields(String styleNo, String styleDesc, Country country);

    void setCountryBeans(List<Country> beans);

}
