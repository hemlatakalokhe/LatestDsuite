package de.bonprix.module.country;

import java.util.List;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.module.country.ui.CountryViewImpl;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
public interface CountryView extends MvpView {

    interface Presenter extends MvpViewPresenter<CountryViewImpl> {

        /**
         * Proceed check box.
         *
         * @param value the value
         * @param request the request
         */
        void proceedCheckBox(Boolean value, NavigationRequest request);

    }

    /**
     * Check check box.
     *
     * @param request the request
     */
    void checkCheckBox(NavigationRequest request);

    /**
     * Sets the all beans.
     *
     * @param beans the new all beans
     */
    void setAllBeans(List<Planperiod> beans);

    /**
     * Sets the all style beans.
     *
     * @param beans the new all style beans
     */
    void setAllStyleBeans(List<Style> beans);

    /**
     * Sets the all country beans.
     *
     * @param beans the new all country beans
     */
    void setAllCountryBeans(List<Country> beans);
}
