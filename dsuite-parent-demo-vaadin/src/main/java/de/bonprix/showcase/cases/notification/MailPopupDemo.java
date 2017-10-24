package de.bonprix.showcase.cases.notification;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mail.MailPopupConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiPageProvider;

/**
 * Shows any kind of Messageboxes.
 *
 * @author thacht
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class MailPopupDemo extends ShowcaseWrapper {

    @Autowired
    private UiPageProvider pageProvider;

    public MailPopupDemo() {
        super("INTERACTION", "MAILPOPUPDEMO");
    }

    @Override
    protected Component createLayout() {
        return FluentUI.horizontal()
            .margin()
            .spacing()
            // This is opens the default mail client.
            .add(FluentUI.button()
                .captionKey("MAIL")
                .onClick(event -> this.pageProvider.openDefaultMailClient(new MailPopupConfigurationBuilder().withTo("to@bonprix.net")
                    .withCc("cc@bonprix.net")
                    .withBcc("bcc@bonprix.net")
                    .withSubject("subject")
                    .withBody("body")
                    .build()))
                .get())
            .get();
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
