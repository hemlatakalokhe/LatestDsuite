package de.bonprix.module.style.wizard.mvp;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.wizard.AbstractWizardStep;
import de.bonprix.vaadin.mvp.wizard.OnStepEnter;
import de.bonprix.vaadin.mvp.wizard.OnStepLeave;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("rawtypes")
public class AddStyleWizardStepOne extends AbstractWizardStep {

    private static final long serialVersionUID = 3037701501973130503L;
    private TextField styleNo;
    private TextField styleDesc;
    private BeanItemComboBox<Country> countryComboBox;
    private BeanItemComboBox<Client> clientComboBox;
    private BeanItemComboBox<Season> seasonComboBox;
    private final StyleWizardPojo styleWizardPojo;

    @SuppressWarnings("unchecked")
    public AddStyleWizardStepOne(final StyleWizardPojo pojo, final OnStepEnter onStepEnter, final OnStepLeave onStepLeave) {
        super("First Step", "Second Step", onStepEnter, onStepLeave);
        this.styleWizardPojo = pojo;
        setAllData(this.styleWizardPojo);
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

    public Style getStyleData() {
        return new StyleBuilder().withDesc(this.styleDesc.getValue())
            .withStyleNo(this.styleNo.getValue())
            .withCountry(this.countryComboBox.getSelectedItem())
            .withClient(this.clientComboBox.getSelectedItem())
            .withSeason(this.seasonComboBox.getSelectedItem())
            .build();
    }

    public void setAllData(final StyleWizardPojo pojo) {
        this.countryComboBox.replaceAllBeans(pojo.getCountries());
        this.clientComboBox.replaceAllBeans(pojo.getClients());
        this.seasonComboBox.replaceAllBeans(pojo.getSeasons());

    }

}
