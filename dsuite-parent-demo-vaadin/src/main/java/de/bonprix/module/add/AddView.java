package de.bonprix.module.add;

import java.util.List;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
public interface AddView extends MvpView {

    interface Presenter extends MvpViewPresenter<AddView> {

        /**
         * Proceed check box.
         *
         * @param value the value
         * @param request the request
         */
        void proceedCheckBox(Boolean value, NavigationRequest request);

        /**
         * Save style.
         *
         * @param styleNo the style no
         * @param styleDesc the style desc
         * @param country the country
         */
        void saveStyle(String styleNo, String styleDesc, Country country);

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
     * Show notification.
     *
     * @param string the string
     */
    void showNotification(String string);

    /**
     * Sets the all country beans.
     *
     * @param beans the new all country beans
     */
    void setAllCountryBeans(List<Country> beans);

}
