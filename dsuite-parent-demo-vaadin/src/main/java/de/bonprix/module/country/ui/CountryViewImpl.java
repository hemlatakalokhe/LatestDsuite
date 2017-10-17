package de.bonprix.module.country.ui;

import java.util.List;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;
import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.module.country.CountryPresenter;
import de.bonprix.module.country.CountryView;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * @author h.kalokhe
 *
 */
@SpringView(
    name = CountryViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class CountryViewImpl extends AbstractMvpView<CountryPresenter> implements CountryView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "Country";

    private CheckBox checkBox;

    private BeanItemGrid<Country> styleGrid;

    @Override
    protected void initializeUI() {
        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        this.styleGrid = new BeanItemGrid<>(Country.class);

        setCompositionRoot(FluentUI.vertical()
            .add(this.styleGrid)
            .get());
        setSizeFull();
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void setAllBeans(final List<Planperiod> beans) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAllStyleBeans(final List<Style> beans) {
        // this.styleGrid.addAllBeans(beans);
    }

    @Override
    public void setAllCountryBeans(final List<Country> beans) {
        this.styleGrid.addAllBeans(beans);
    }

}
