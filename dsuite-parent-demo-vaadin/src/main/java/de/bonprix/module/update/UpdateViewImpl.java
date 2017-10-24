package de.bonprix.module.update;

import java.util.List;

import javax.annotation.Resource;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.module.style.ui.StyleViewImpl;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNavigationProvider;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SpringView(
    name = UpdateViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class UpdateViewImpl extends AbstractMvpView<UpdateView.Presenter> implements UpdateView {

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Update";
    private CheckBox checkBox;
    private TextField styleDesc;
    private BeanItemComboBox<Country> countryComboBox;

    @Resource
    private transient UiNotificationProvider notificatonProvider;

    private TextField styleNumber;

    @Resource
    private transient UiNavigationProvider navigationProvider;

    @Override
    protected void initializeUI() {
        Button update;
        Button cancel;
        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        this.styleNumber = FluentUI.textField()
            .caption("Style Number")
            .get();
        this.styleDesc = FluentUI.textField()
            .caption("Style Description")
            .get();
        this.countryComboBox = FluentUI.beanItemComboBox(Country.class)
            .caption("Country")
            .get();
        update = FluentUI.button()
            .caption("Update")
            .get();
        cancel = FluentUI.button()
            .caption("Cancel")
            .get();

        cancel.addClickListener(e2 -> this.navigationProvider.navigateTo(StyleViewImpl.VIEW_NAME));

        update.addClickListener(e -> getPresenter().saveUpdatedStyle(this.styleNumber.getValue(), this.styleDesc.getValue(),
                                                                     this.countryComboBox.getSelectedItem()));

        getPresenter().updateStyle((Style) VaadinSession.getCurrent()
            .getAttribute("styleNo"));
        setCompositionRoot(FluentUI.form()
            .add(this.styleNumber, this.styleDesc, this.countryComboBox, FluentUI.horizontal()
                .add(update, cancel)
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
    public void setFields(final String styleNo, final String styleDesc, final Country country) {
        this.styleNumber.setValue(styleNo);
        this.styleDesc.setValue(styleDesc);
        this.countryComboBox.setValue(country);
    }

    @Override
    public void setCountryBeans(final List<Country> beans) {
        this.countryComboBox.addAllBeans(beans);
    }

}
