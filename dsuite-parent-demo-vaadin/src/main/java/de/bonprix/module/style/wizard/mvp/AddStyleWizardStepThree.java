package de.bonprix.module.style.wizard.mvp;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.wizard.AbstractWizardStep;
import de.bonprix.vaadin.mvp.wizard.OnStepEnter;
import de.bonprix.vaadin.mvp.wizard.OnStepLeave;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("rawtypes")
public class AddStyleWizardStepThree extends AbstractWizardStep {

    private static final long serialVersionUID = -6715731124972369245L;
    private BeanItemGrid<Style> styleGrid;
    protected static final String COUNTRY = "country";
    protected static final String CLIENT = "client";
    protected static final String SEASON = "season";
    protected static final String STYLE_NO = "styleNo";
    protected static final String DESCRIPTION = "desc";
    protected static final String DATE = "date";

    private final StyleWizardPojo styleWizardPojo;

    @SuppressWarnings("unchecked")
    public AddStyleWizardStepThree(final StyleWizardPojo pojo, final OnStepEnter onStepEnter, final OnStepLeave onStepLeave) {
        super("Second Step", "Exit", onStepEnter, onStepLeave);
        this.styleWizardPojo = pojo;
        setAllStyleData(this.styleWizardPojo);

    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public Component layout() {
        this.styleGrid = new BeanItemGrid<>(Style.class);
        this.styleGrid.setColumns("id", AddStyleWizardStepThree.DESCRIPTION, AddStyleWizardStepThree.STYLE_NO, AddStyleWizardStepThree.COUNTRY,
                                  AddStyleWizardStepThree.DATE, AddStyleWizardStepThree.CLIENT, AddStyleWizardStepThree.SEASON);
        this.styleGrid.setHeight(20, Unit.CM);
        this.styleGrid.setWidth(25, Unit.CM);
        final FormLayout layout = FluentUI.form()
            .add(this.styleGrid)
            .get();

        final VerticalLayout verticalLayout = FluentUI.vertical()
            .add(layout)
            .get();

        final Panel panel = new Panel(verticalLayout);
        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();

        return panel;
    }

    public void setAllStyleData(final StyleWizardPojo pojo) {
        this.styleGrid.replaceAllBeans(pojo.getStyles());
    }

}
