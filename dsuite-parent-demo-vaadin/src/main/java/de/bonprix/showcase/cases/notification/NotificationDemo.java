package de.bonprix.showcase.cases.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.data.BeanItemFieldGroup;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * Shows any kind of Notifications.
 *
 * @author thacht
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class NotificationDemo extends ShowcaseWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDemo.class);

    @Autowired
    private UiNotificationProvider notificationProvider;

    private static final String CAPTION = "Notification Title";
    private static final String MESSAGE = "A more informative message about what has happened. Nihil hic munitissimus habendi senatus locus, nihil horum? Inmensae subtilitatis, obscuris et malesuada fames. Hi omnes lingua, institutis, legibus inter se differunt.";

    public NotificationDemo() {
        super("INTERACTION", "NOTIFICATIONDEMO");
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
        final NotificationFieldGroupBean fieldGroupBean = new NotificationFieldGroupBean();
        fieldGroupBean.setDelay(10);
        fieldGroupBean.setMessage(NotificationDemo.CAPTION);
        fieldGroupBean.setCaption(NotificationDemo.MESSAGE);

        final BeanItemFieldGroup<NotificationFieldGroupBean> fieldGroup = new BeanItemFieldGroup<>(NotificationFieldGroupBean.class, fieldGroupBean);
        fieldGroup.setBuffered(false);

        final TextField delayFiled = fieldGroup.buildAndBind("CUSTOM_NOTIFICATION_DELAY_MS", "delay", TextField.class);
        delayFiled.setRequired(true);
        final TextField captionField = fieldGroup.buildAndBind("CUSTOM_NOTIFICATION_CAPTION", "caption", TextField.class);
        final TextArea messageField = fieldGroup.buildAndBind("CUSTOM_NOTIFICATION_MESSAGE", "message", TextArea.class);
        messageField.setHeight(100, Unit.PIXELS);
        messageField.setWidth(500, Unit.PIXELS);

        final Layout fieldGroupLayout = FluentUI.horizontal()
            .add(delayFiled)
            .add(captionField)
            .add(messageField)
            .spacing()
            .get();

        return FluentUI.vertical()
            .margin()
            .spacing()
            .add(fieldGroupLayout)
            .add(FluentUI.button()
                .captionKey("SHOW_ERROR_MESSAGE")
                .onClick(event -> this.notificationProvider.showErrorNotification(NotificationDemo.CAPTION, NotificationDemo.MESSAGE))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_ERROR_MESSAGE_CUSTOM")
                .onClick(event -> this.notificationProvider.showErrorNotification(fieldGroupBean.getCaption(), fieldGroupBean.getMessage(),
                                                                                  fieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_INFO_MESSAGE_CAPTION_ONLY")
                .onClick(event -> this.notificationProvider.showInfoNotification(NotificationDemo.CAPTION))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_INFO_MESSAGE_CAPTION_ONLY_CUSTOM")
                .onClick(event -> this.notificationProvider.showInfoNotification(fieldGroupBean.getCaption(), fieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_INFO_MESSAGE")
                .onClick(event -> this.notificationProvider.showInfoNotification(NotificationDemo.CAPTION, NotificationDemo.MESSAGE))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_INFO_MESSAGE_CUSTOM")
                .onClick(event -> this.notificationProvider.showInfoNotification(fieldGroupBean.getCaption(), fieldGroupBean.getMessage(),
                                                                                 fieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_WARNING_MESSAGE")
                .onClick(event -> this.notificationProvider.showWarningNotification(NotificationDemo.CAPTION, NotificationDemo.MESSAGE))
                .get())
            .add(FluentUI.button()
                .captionKey("SHOW_WARNING_MESSAGE_CUSTOM")
                .onClick(event -> this.notificationProvider.showWarningNotification(fieldGroupBean.getCaption(), fieldGroupBean.getMessage(),
                                                                                    fieldGroupBean.getDelay()))
                .get())
            .get();
    }

}
