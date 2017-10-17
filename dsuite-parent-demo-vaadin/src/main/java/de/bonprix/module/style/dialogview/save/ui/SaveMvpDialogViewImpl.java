package de.bonprix.module.style.dialogview.save.ui;

import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.module.style.dialogview.save.SaveMvpDialogPresenter;
import de.bonprix.module.style.dialogview.save.SaveMvpDialogView;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringViewComponent
public class SaveMvpDialogViewImpl extends AbstractMvpDialogView<SaveMvpDialogPresenter>
implements SaveMvpDialogView<SaveMvpDialogPresenter>{

    /**
     * 
     */
    private static final long serialVersionUID = 6860500500781461610L;
    private TextField styleNo;
    private TextField styleDesc;
    private BeanItemComboBox<Country> countryComboBox;
    private BeanItemComboBox<Client> clientComboBox;
    private BeanItemComboBox<Season> seasonComboBox;

    public SaveMvpDialogViewImpl() {
        super(new DialogConfigurationBuilder()
              .withButton(DialogButton.CANCEL)
              .withPrimaryButton(DialogButton.SAVE)
              .withCloseOnButton(DialogButton.CANCEL)
              .withHeadline("UPDATE")
              .withWidth(500)
              .withHeight(500).build());
        addButtonListener(DialogButton.SAVE, event->getPresenter()
                          .saveStyleData(this.styleNo.getValue(), this.styleDesc.getValue(),
                                         this.countryComboBox.getSelectedItem()
                                         , this.seasonComboBox.getSelectedItem()
                                         , this.clientComboBox.getSelectedItem()));
        addButtonListener(DialogButton.SAVE, event->close());
    }

    @Override
    protected Component layout() {
        this.styleNo=FluentUI.textField().caption("Style Number").required(true).get();
        this.styleDesc=FluentUI.textField().caption("Style Description").required(true).get();
        this.countryComboBox=FluentUI.beanItemComboBox(Country.class).caption("Country").required(true).get();
        this.clientComboBox=FluentUI.beanItemComboBox(Client.class).caption("Client").required(true).get();
        this.seasonComboBox=FluentUI.beanItemComboBox(Season.class).caption("Season").required(true).get();

        final FormLayout layout=FluentUI.form().add(this.styleDesc,this.styleNo,this.countryComboBox,this.clientComboBox,this.seasonComboBox).get();
        final VerticalLayout verticalLayout=FluentUI.vertical().add(layout).get();

        final Panel panel=new Panel(verticalLayout);
        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();
        return panel;
    }

    @Override
    public void setData(final List<Country> countries, final List<Season> seasons, final List<Client> clients) {
        this.countryComboBox.replaceAllBeans(countries);
        this.seasonComboBox.replaceAllBeans(seasons);
        this.clientComboBox.replaceAllBeans(clients);
    }

}
