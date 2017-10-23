package de.bonprix.module.samples;

import java.util.List;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringView(
    name = SamplesViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 85)
public class SamplesViewImpl extends AbstractMvpView<SamplesPresenter> implements SamplesView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "Samplesd";

    @SuppressWarnings("unused")
    private CheckBox checkBox;

    private BeanItemGrid<Planperiod> planperiodGrid;

    private BeanItemComboBox<Planperiod> planperiodCombo;

    private BeanItemComboBoxMultiselect<Planperiod> planperiodComboMulti;

    //
    @Override
    protected void initializeUI() {
        this.planperiodGrid = new BeanItemGrid<>(Planperiod.class);
        this.planperiodCombo = new BeanItemComboBox<>(Planperiod.class);
        this.planperiodComboMulti = new BeanItemComboBoxMultiselect<>(Planperiod.class);

        setCompositionRoot(FluentUI.horizontal()
            .add(FluentUI.vertical()
                .add(this.planperiodGrid)
                .size(50, 50, Unit.PERCENTAGE)
                .get())
            .add(FluentUI.vertical()
                .add(this.planperiodCombo)
                .get())
            .add(this.planperiodComboMulti)
            .get());
        setSizeFull();
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        //
    }

    @Override
    public void setAllBean(final List<Planperiod> beans) {
        this.planperiodCombo.addAllBeans(beans);
        this.planperiodGrid.addAllBeans(beans);
        this.planperiodComboMulti.addAllBeans(beans);

    }

}