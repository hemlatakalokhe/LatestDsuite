package de.bonprix.module.update;

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

@SpringPresenter
public class UpdatePresenter extends AbstractMvpViewPresenter<UpdateView> implements UpdateView.UpdatePresenter {

    @Resource
    private StyleService style;
    @Resource
    private LoginService login;
    @Resource
    private CountryService country;
    @Resource
    private UiNavigationProvider navigationProvider;
    private static Long id;

    @Override
    public void init() {
    }

    @Override
    public void onViewEnter() {

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
    public void updateStyle(final Style style) {

        UpdatePresenter.id = style.getId();
        final String styleNo = style.getStyleNo();
        final String styleDesc = style.getDesc();
        final Country country = style.getCountry();
        getView().setCountryBeans(this.country.findAll());
        getView().setFields(styleNo, styleDesc, country);

    }

    @Override
    public void saveUpdatedStyle(final String styleNo, final String styleDesc, final Country country) {

        final Style styleDto = this.style.findById(UpdatePresenter.id);
        styleDto.setStyleNo(styleNo);
        styleDto.setDesc(styleDesc);
        styleDto.setCountry(country);
        this.style.saveStyle(styleDto);
        this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME);
    }

}