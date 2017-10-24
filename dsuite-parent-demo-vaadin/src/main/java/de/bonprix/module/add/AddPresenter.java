/*
 * 
 */
package de.bonprix.module.add;

import javax.annotation.Resource;

import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.LoginService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.module.style.ui.StyleViewImpl;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNavigationProvider;

/**
 * @author h.kalokhe
 *
 */
@SpringPresenter
public class AddPresenter extends AbstractMvpViewPresenter<AddView> implements AddView.Presenter {

    @Resource
    private StyleService style;

    @Resource
    private LoginService login;

    @Resource
    private CountryService country;

    @Resource
    private transient UiNavigationProvider navigationProvider;

    @Override
    public void init() {
        //
    }

    @Override
    public void onViewEnter() {
        getView().setAllStyleBeans(this.style.findAll());
        getView().setAllCountryBeans(this.country.findAll());
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
    public void saveStyle(final String styleNo, final String styleDesc, final Country country) {

        final Style styles = new Style();
        styles.setStyleNo(styleNo);
        styles.setDesc(styleDesc);
        styles.setCountry(country);

        this.style.saveStyle(styles);
        this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME);
    }

}