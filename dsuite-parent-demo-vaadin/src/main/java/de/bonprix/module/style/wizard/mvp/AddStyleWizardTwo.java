package de.bonprix.module.style.wizard.mvp;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.wizard.AbstractWizardStep;
import de.bonprix.vaadin.mvp.wizard.OnStepEnter;
import de.bonprix.vaadin.mvp.wizard.OnStepLeave;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("rawtypes")
public class AddStyleWizardTwo extends AbstractWizardStep {

    /**
     * 
     */
    private static final long serialVersionUID = 5690949762492030761L;
    private TextField styleNo;
    private TextField styleDesc;
    private BeanItemComboBox<Country> countryComboBox;
    private BeanItemComboBox<Client> clientComboBox;
    private BeanItemComboBox<Season> seasonComboBox;
    private BeanFieldGroup<Style> fieldGroup;
    @SuppressWarnings("unused")
    private final StyleWizardPojo styleWizardPojo;

    @SuppressWarnings("unchecked")
    public AddStyleWizardTwo(final StyleWizardPojo pojo, final OnStepEnter onStepEnter, final OnStepLeave onStepLeave) {
        super("", "", onStepEnter, onStepLeave);
        this.styleWizardPojo = pojo;
    }

    @Override
    public Component layout() {
        this.styleNo = FluentUI.textField()
            .caption("Style Number")
            .required(true)
            .get();
        this.styleDesc = FluentUI.textField()
            .caption("Style Description")
            .required(true)
            .get();
        this.countryComboBox = FluentUI.beanItemComboBox(Country.class)
            .caption("Country")
            .required(true)
            .get();
        this.clientComboBox = FluentUI.beanItemComboBox(Client.class)
            .caption("Client")
            .required(true)
            .get();
        this.seasonComboBox = FluentUI.beanItemComboBox(Season.class)
            .caption("Season")
            .required(true)
            .get();

        final FormLayout layout = FluentUI.form()
            .add(this.styleDesc, this.styleNo, this.countryComboBox, this.clientComboBox, this.seasonComboBox)
            .get();
        final VerticalLayout verticalLayout = FluentUI.vertical()
            .add(layout)
            .get();

        final Panel panel = new Panel(verticalLayout);
        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();
        return panel;
    }

}
