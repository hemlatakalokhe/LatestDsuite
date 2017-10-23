package de.bonprix.showcase.cases.wizard.mvp;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.wizard.AbstractWizardStep;
import de.bonprix.vaadin.mvp.wizard.OnStepEnter;
import de.bonprix.vaadin.mvp.wizard.OnStepLeave;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("rawtypes")
public class WizardStepTwo extends AbstractWizardStep {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Label name;
    private Label street;
    private Label city;

    @SuppressWarnings({ "unchecked" })
    public WizardStepTwo(final OnStepEnter onStepEnter, final OnStepLeave onStepLeave) {
        super("(Seite 2)", "Eine Beschreibung", onStepEnter, onStepLeave);
    }

    @Override
    public Component layout() {

        this.name = new Label();
        this.street = new Label();
        this.city = new Label();

        // The Layout you want to show in the first Dialog-Step
        final VerticalLayout layout = new VerticalLayout(this.name, this.street, this.city);

        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setMargin(false);

        final VerticalLayout mainLayout = FluentUI.vertical()
            .add(getHeader())
            .add(layout)
            .get();

        final Panel wizardStep1Panel = new Panel(mainLayout);
        wizardStep1Panel.setSizeFull();
        wizardStep1Panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);

        return wizardStep1Panel;
    }

    public void setFromPojo(final ShowCasePojo pojo) {
        this.name.setValue(pojo.getName());
        this.street.setValue(pojo.getStreet());
        this.city.setValue(pojo.getCity());
    }
}
