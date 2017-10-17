package de.bonprix.module.registerform;

import javax.annotation.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import de.bonprix.VaadinUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;

@SpringView(name = RegisterViewImpl.VIEW_NAME, ui = { VaadinUI.class }, isDefault = false, order = 30)
public class RegisterViewImpl extends AbstractMvpView<RegisterPresenter> implements RegisterView {

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Register";
    private CheckBox checkBox;
    private PasswordField password;
    private TextField username;
    @Resource
    private UiNotificationProvider notificatonProvider;
    private Button login;
    //    private BeanItemFieldGroup<Style> fieldGroup;
    private Button info;
    private Button error;
    private Button warning;
    private Button question;
    private Button customized;

    private static String message="Welcome To Notification View";

    @Override
    protected void initializeUI() {
        //		this.username=FluentUI.textField().caption("Name").get();
        //		this.password=FluentUI.passwordField().caption("Password").get();
        //		this.login=FluentUI.button().caption("Login").get();
        //
        //
        //		this.info=FluentUI.button().caption("info")
        //				.onClick(event -> notificatonProvider.showInfoMessageBox(message)
        //				).get();
        //
        //		this.error=FluentUI.button().caption("Error")
        //				.onClick(event -> notificatonProvider.showErrorMessageBox(message)
        //				).get();
        //
        //		this.warning=FluentUI.button().caption("Warning")
        //				.onClick(event -> notificatonProvider.showWarningMessageBox(message)
        //				).get();
        //
        //		this.question=FluentUI.button().caption("Question")
        //				.onClick(event -> notificatonProvider.showQuestionMessageBox(message, null)
        //				).get();
        //
        //		this.customized=FluentUI.button().caption("Customized")
        //				.onClick(event-> notificatonProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
        //						.withHtmlMessage(message)
        //						.withButton(DialogButton.YES,()->Notification.show("U selected Yes"))
        //						.withPrimaryButton(DialogButton.CANCEL,()->Notification.show("U canceled"))
        //						.withButton(DialogButton.NO,()->Notification.show("U selected No"))
        //						.build()))
        //				.get();
        //
        //		this.fieldGroup=new BeanItemFieldGroup<>(Style.class);
        //
        //		fieldGroup.bind(username,"styleNo");
        //		fieldGroup.bind(password, "desc");
        //
        //		login.addClickListener(event -> {
        //			this.username.addValidator(new StringLengthValidator("Error", 2, 10, true));
        //			this.password.addValidator(new RegexpValidator("^(?=.*[a-z])(?=.*[A-Z])(?=.*/d)(?=.*[$@$!%*?&])[A-Za-z/d$@$!%*?&]{8,}", true, "Inappropriate Passwprd"));
        //			if(fieldGroup.isValid())
        //			{
        //				System.out.println("Valid fieldGroup");
        //			}
        //			else
        //			{
        //				Notification.show("Passwor must contain Minimum eight characters, at least one uppercase letter,"
        //						+ " one lowercase letter, one number and one special character");
        //			}
        //		});
        //
        //		setCompositionRoot(FluentUI.horizontal().add(username,password,login).spacing(true).get());
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

}
