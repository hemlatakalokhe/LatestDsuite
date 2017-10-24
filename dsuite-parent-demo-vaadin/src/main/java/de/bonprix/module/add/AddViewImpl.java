package de.bonprix.module.add;

import java.util.List;

import javax.annotation.Resource;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.module.style.ui.StyleViewImpl;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * @author h.kalokhe
 *
 */
@SpringView(
    name = AddViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class AddViewImpl extends AbstractMvpView<AddView.Presenter> implements AddView {

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Add";
    private CheckBox checkBox;
    private BeanItemComboBox<Country> countryComboBox;
    @Resource
    private transient UiNotificationProvider notificatonProvider;

    @Override
    protected void initializeUI() {
        final Button save;
        final Button cancel;
        TextField styleNo;
        TextField styleDesc;

        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        styleNo = FluentUI.textField()
            .caption("Style Number")
            .get();
        styleDesc = FluentUI.textField()
            .caption("Style Description")
            .get();
        this.countryComboBox = FluentUI.beanItemComboBox(Country.class)
            .caption("Country")
            .get();
        cancel = FluentUI.button()
            .caption("Cancel")
            .onClick(e1 -> tryNavigateTo(new NavigationRequest(StyleViewImpl.VIEW_NAME)))
            .get();
        save = FluentUI.button()
            .caption("Save")
            .onClick(e -> getPresenter().saveStyle(styleNo.getValue(), styleDesc.getValue(), this.countryComboBox.getSelectedItem()))
            .get();

        setCompositionRoot(FluentUI.form()
            .add(styleNo, styleDesc, this.countryComboBox, FluentUI.horizontal()
                .add(save, cancel)
                .spacing(true)
                .get())
            .spacing(true)
            .get());

    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void setAllBeans(final List<Planperiod> beans) {
        //
    }

    @Override
    public void setAllStyleBeans(final List<Style> beans) {
        //
    }

    @Override
    public void showNotification(final String message) {
        this.notificatonProvider.showInfoMessageBox(message);

    }

    @Override
    public void setAllCountryBeans(final List<Country> beans) {
        this.countryComboBox.addAllBeans(beans);
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
