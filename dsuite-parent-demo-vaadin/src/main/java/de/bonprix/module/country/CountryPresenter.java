package de.bonprix.module.country;

import javax.annotation.Resource;

import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.module.country.ui.CountryViewImpl;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
@SpringPresenter
public class CountryPresenter extends AbstractMvpViewPresenter<CountryViewImpl> implements CountryView.StylePresenter {

    @Resource
    private StyleService style;

    @Resource
    private CountryService countryService;

    @Override
    public void init() {
        // empty
    }

    @Override
    public void onViewEnter() {
        getView().setAllCountryBeans(this.countryService.findAll());
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