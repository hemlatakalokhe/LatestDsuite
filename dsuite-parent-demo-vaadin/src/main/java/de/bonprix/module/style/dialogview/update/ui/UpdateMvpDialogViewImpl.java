package de.bonprix.module.style.dialogview.update.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Login;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.model.enums.Mode;
import de.bonprix.module.style.dialogview.update.UpdateMvpDialogView;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringViewComponent
public class UpdateMvpDialogViewImpl extends AbstractMvpDialogView<UpdateMvpDialogView.Presenter>
        implements UpdateMvpDialogView<UpdateMvpDialogView.Presenter> {

    private static final long serialVersionUID = 8409832419813379861L;
    @Resource
    private transient UiNotificationProvider notificationProvider;
    private TextField desc;
    private BeanItemComboBox<Country> countryComboBox;
    private BeanItemComboBox<Client> clientComboBox;
    private BeanItemComboBox<Season> seasonComboBox;
    private Mode mode = Mode.EDIT;
    private TextField name;
    private TextField lname;
    private TextField username;
    private PasswordField password;
    private PasswordField cpassword;
    private final BeanItemFieldGroup<Login> loginFieldGroup;
    private TextField styleNumber;

    public UpdateMvpDialogViewImpl() {
        super(new DialogConfigurationBuilder().withButton(DialogButton.CANCEL)
            .withPrimaryButton(DialogButton.OK)
            .withCloseOnButton(DialogButton.CANCEL)
            .withWidth(500)
            .withHeight(500)
            .build());
        this.loginFieldGroup = new BeanItemFieldGroup<>(Login.class);
        this.loginFieldGroup.setBuffered(false);
        addButtonListener(DialogButton.OK, event -> {

            if (this.mode.equals(Mode.ADD) || this.mode.equals(Mode.EDIT)) {
                if (isValid()) {
                    getPresenter().updateData(this.styleNumber.getValue(), this.desc.getValue(), this.countryComboBox.getSelectedItem(),
                                              this.clientComboBox.getSelectedItem(), this.seasonComboBox.getSelectedItem(), this.mode);
                    close();
                }
            }
            else if (this.loginFieldGroup.isValid()) {
                if (passwordCheck()) {
                    getPresenter().registerUser(this.username.getValue(), this.password.getValue(), this.name.getValue(), this.lname.getValue());
                    close();
                }
                else {
                    this.notificationProvider.showErrorNotification("", "Enter correct Password");
                    this.cpassword.focus();
                }
            }
            else {
                this.notificationProvider.showErrorNotification("", "Enter Mandatory fields");
            }

        });
    }

    @Override
    @PostConstruct
    protected Component layout() {
        if (this.mode.equals(Mode.ADD) || this.mode.equals(Mode.EDIT)) {
            return addAndUpdateLayout();
        }
        return registerLayout();
    }

    @Override
    public void setData(final Style style, final List<Country> countries, final List<Client> clients, final List<Season> seasons, final Mode modes) {
        this.styleNumber.setValue(style.getStyleNo());
        this.desc.setValue(style.getDesc());
        this.countryComboBox.replaceAllBeans(countries);
        this.countryComboBox.setValue(style.getCountry());
        this.clientComboBox.replaceAllBeans(clients);
        this.clientComboBox.setValue(style.getClient());
        this.seasonComboBox.replaceAllBeans(seasons);
        this.seasonComboBox.setValue(style.getSeason());
        this.mode = modes;
    }

    @Override
    public void setHeadLine(final Mode mode) {
        if (mode.equals(Mode.ADD) || mode.equals(Mode.EDIT)) {
            getConfiguration().setHeadline("STYLE_" + mode.toString());
        }
        else if (mode.equals(Mode.REGISTER)) {
            getConfiguration().setHeadline(mode.toString());
            this.mode = mode;
        }
    }

    @Override
    public void setData(final List<Country> countries, final List<Client> clients, final List<Season> seasons, final Mode modes) {
        this.countryComboBox.replaceAllBeans(countries);
        this.clientComboBox.replaceAllBeans(clients);
        this.seasonComboBox.replaceAllBeans(seasons);
        this.mode = modes;
    }

    public Component addAndUpdateLayout() {
        this.styleNumber = FluentUI.textField()
            .caption("Style Number")
            .required()
            .get();
        this.desc = FluentUI.textField()
            .caption("Style Description")
            .required()
            .get();
        this.countryComboBox = FluentUI.beanItemComboBox(Country.class)
            .caption("Country")
            .required()
            .nullSelectionAllowed(false)
            .visible(true)
            .get();
        this.clientComboBox = FluentUI.beanItemComboBox(Client.class)
            .required()
            .nullSelectionAllowed(false)
            .caption("Client")
            .get();
        this.seasonComboBox = FluentUI.beanItemComboBox(Season.class)
            .caption("Season")
            .nullSelectionAllowed(false)
            .required()
            .get();
        final FormLayout layout = FluentUI.form()
            .add(this.styleNumber, this.desc, this.countryComboBox, this.clientComboBox, this.seasonComboBox)
            .get();

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final VerticalLayout verticalLayout = FluentUI.vertical()
            .margin()
            .add(layout)
            .get();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final Panel panel = new Panel(verticalLayout);

        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();

        return panel;
    }

    public Component registerLayout() {
        final CheckBox showPassword = FluentUI.checkBox()
            .caption("Show Password")
            .get();
        final TextField passwordField = FluentUI.textField()
            .caption("Passwprd")
            .get();
        this.name = FluentUI.textField()
            .caption("First Name")
            .bind(this.loginFieldGroup, "firstname")
            .required()
            .get();
        this.lname = FluentUI.textField()
            .caption("Last Name")
            .bind(this.loginFieldGroup, "lastname")
            .required()
            .get();
        this.username = FluentUI.textField()
            .required()
            .bind(this.loginFieldGroup, "username")
            .caption("User Name")
            .get();
        this.cpassword = FluentUI.passwordField()
            .required()
            .bind(this.loginFieldGroup, "password")
            .caption("Confirm Password")
            .get();
        this.password = FluentUI.passwordField()
            .required()
            .caption("Password")
            .get();

        showPassword.addValueChangeListener(event -> {
            if (showPassword.isEmpty()) {
                passwordField.setValue(null);
            }
            else {
                passwordField.setValue(this.password.getValue());
            }
        });
        final FormLayout layout = FluentUI.form()
            .add(this.name, this.lname, this.username, this.password, passwordField, this.cpassword, showPassword)
            .get();

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final VerticalLayout verticalLayout = FluentUI.vertical()
            .add(layout)
            .get();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final Panel panel = new Panel(verticalLayout);

        panel.setStyleName(DSuiteTheme.DIALOG_PANEL_WHITE);
        panel.setSizeFull();

        return panel;
    }

    public boolean passwordCheck() {
        return this.password.getValue()
            .equals(this.cpassword.getValue());
    }

    private boolean isValid() {
        if (this.styleNumber.getValue()
            .isEmpty() && this.styleNumber.getValue() == ""
                && this.desc.getValue()
                    .isEmpty()
                && this.desc.getValue() == "") {
            this.notificationProvider.showErrorNotification("", "Enter Mandatory fields");
            return false;
        }
        if (CollectionUtils.isEmpty(this.countryComboBox.getSelectedItems())) {
            this.notificationProvider.showErrorNotification("", "Select at least one country");
            return false;
        }
        if (CollectionUtils.isEmpty(this.clientComboBox.getSelectedItems())) {
            this.notificationProvider.showErrorNotification("", "Select at least one client");
            return false;
        }
        if (CollectionUtils.isEmpty(this.seasonComboBox.getSelectedItems())) {
            this.notificationProvider.showErrorNotification("", "Select at least one season");
            return false;
        }

        return true;
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
