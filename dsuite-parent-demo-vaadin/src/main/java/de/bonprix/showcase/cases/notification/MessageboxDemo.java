package de.bonprix.showcase.cases.notification;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.messagebox.MessageBoxConfigurationBuilder;
import de.bonprix.vaadin.messagebox.MessageBoxIcon;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * Shows any kind of Messageboxes.
 *
 * @author thacht
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class MessageboxDemo extends ShowcaseWrapper {

    @Autowired
    private UiNotificationProvider notificationProvider;

    private static final String MESSAGE = "A more informative message about what has happened. Nihil hic munitissimus habendi senatus locus, nihil horum? Inmensae subtilitatis, obscuris et malesuada fames. Hi omnes lingua, institutis, legibus inter se differunt.";

    public MessageboxDemo() {
        super("INTERACTION", "MESSAGEBOXDEMO");
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
    protected Component createLayout() {

        return FluentUI.horizontal()
            .margin()
            .spacing()
            // Displays a Info - Messagebox with a message formatted as HTML.
            // The displayed buttons will be selected over the buttonId (CANCEL)
            // With event listener, that is triggered on clicking a button.
            .add(FluentUI.button()
                .captionKey("INFO")
                .onClick(event -> this.notificationProvider.showInfoMessageBox(MessageboxDemo.MESSAGE))
                .get())
            // Displays a Question - Messagebox with a
            // message/question
            // formatted as plain text.
            // Integrates two buttons. Yes and No.
            // With event listener, that is triggered on clicking a
            // button and a primary button.
            .add(FluentUI.button()
                .captionKey("QUESTION")
                .onClick(event -> this.notificationProvider.showQuestionMessageBox(MessageboxDemo.MESSAGE, () -> Notification.show("You clicked YES"),
                                                                                   DialogButton.NO))
                .get())
            // Displays a Warn - Messagebox with a message formatted
            // as
            // plain text.
            // The displayed buttons will be selected over the
            // buttonId
            // (Close)
            .add(FluentUI.button()
                .captionKey("WARNING")
                .onClick(event -> this.notificationProvider.showWarningMessageBox(MessageboxDemo.MESSAGE))
                .get())
            // Displays a Error - Messagebox with a custom component
            // for
            // message.
            // The displayed buttons will be selected over the
            // buttonId (OK)
            .add(FluentUI.button()
                .captionKey("ERROR")
                .onClick(event -> this.notificationProvider.showErrorMessageBox(MessageboxDemo.MESSAGE))
                .get())
            // This is Displays a customize MessageBox.
            .add(FluentUI.button()
                .captionKey("CUSTOMIZED")
                // To customize a Messagebox you have to
                // create a
                // MessageBoxConfiguration.
                // You can use the
                // MessageBoxConfigurationBuilder to do
                // so.
                // Here we create a MessageBox with a
                // Question-Icon, a custom Message
                // and three Buttons (Cancel, No and
                // Yes)
                .onClick(event -> this.notificationProvider.showMessageBox(new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.QUESTION)
                    .withHtmlMessage("This is a customized MessageBox")
                    .withPrimaryButton(DialogButton.CANCEL, () -> Notification.show("You clicked CANCEL"))
                    .withButton(DialogButton.NO, () -> Notification.show("You clicked NO"))
                    .withButton(DialogButton.YES, () -> Notification.show("You clicked YES"))
                    .build()))
                .get())
            .get();
    }

}
