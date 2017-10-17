package de.bonprix.module.style.wizard.mvp;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.wizard.AbstractWizardStep;
import de.bonprix.vaadin.mvp.wizard.OnStepEnter;
import de.bonprix.vaadin.mvp.wizard.OnStepLeave;
import de.bonprix.vaadin.theme.DSuiteTheme;

public class StyleWizardStepTwo extends AbstractWizardStep{

    private static final long serialVersionUID = 5112472614211407968L;
    private BeanItemComboBox<Client> clientComboBox;
    private BeanItemComboBox<Season> seasonComboBox;

    public StyleWizardStepTwo(final StyleWizardPojo pojo,final OnStepEnter onStepEnter, final OnStepLeave onStepLeave) {
        super("Second Step", "finish", onStepEnter, onStepLeave);
        this.clientComboBox.replaceAllBeans(pojo.getClients());
        this.seasonComboBox.replaceAllBeans(pojo.getSeasons());
    }

    @Override
    public Component layout() {
        this.clientComboBox=new BeanItemComboBox<>(Client.class);
        this.seasonComboBox=new BeanItemComboBox<>(Season.class);

        final FormLayout layout=FluentUI.form().add(this.clientComboBox,this.seasonComboBox).get();

        final VerticalLayout verticalLayout=FluentUI.vertical().add(layout).get();

        final Panel panel=new Panel(verticalLayout);
        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();

        return panel;
    }

    public Style setClientSeasonData(){
        return new StyleBuilder().withClient(this.clientComboBox.getSelectedItem()).withSeason(this.seasonComboBox.getSelectedItem()).build();
    }


}
