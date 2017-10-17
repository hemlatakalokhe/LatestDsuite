package de.bonprix.vaadin.spring;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.idle.Idle;
import org.vaadin.kim.countdownclock.CountdownClock;

import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.error.BPErrorHandler;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.layout.BpMenuLayout;
import de.bonprix.vaadin.messagebox.MessageBoxConfiguration;
import de.bonprix.vaadin.messagebox.MessageBoxConfigurationBuilder;
import de.bonprix.vaadin.messagebox.MessageBoxIcon;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * Subclass of the Vaadin {@link UI}. Helps us to connect the
 * {@link BpNavigator} with the content of our Layout {@link BpMenuLayout}
 */
@UIScope
@Widgetset("de.bonprix.vaadin.ui.BonprixPrecompiledWidgetset")
public class BpUI extends UI {
	private static final long serialVersionUID = 1L;
	// 10 Minutes Countdown
	private static final Integer COUNTDOWN_TO_SESSION_TIMEOUT_IN_SECONDS = 600;

	@Autowired
	protected BpMenuLayout bpMenuLayout;

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private BPErrorHandler bpErrorHandler;

	@Autowired
	private PrincipalProvider principalProvider;

	@Autowired
	private UiNotificationProvider uiNotificationProvider;

	@Autowired
	private ApplicationProvider applicationProvider;

	@Override
	protected void init(final VaadinRequest request) {
		// connect bpLayout with Navigator and UI
		new BpNavigator(this, this.viewProvider, this.bpMenuLayout, this.principalProvider, this.uiNotificationProvider,
				this.applicationProvider);
		setContent(this.bpMenuLayout);
		setErrorHandler(this.bpErrorHandler);

		SessionTimeOutMessageBox sessionTimeOutMessageBox = new SessionTimeOutMessageBox(
				new MessageBoxConfigurationBuilder().withMessageBoxIcon(MessageBoxIcon.ERROR)
					.withHtmlMessage(I18N.get("SESSION_TIMEOUT_MESSAGE"))
					.withPrimaryButton(DialogButton.CONTINUE)
					.withCloseOnAnyButton(true)
					.withModal(true)
					.build());

		/**
		 * Set time until Session-Timeout to 30 sec:
		 * UI.getCurrent().getSession().getSession().setMaxInactiveInterval(30);
		 * 10 minutes/600 seconds before Session-Timeout we throw a warn-message
		 * Because Idle-AddOn needs milliseconds we have to multiplie with 1000
		 * getMaxInactiveInterval() - Time to next Session-Timeout in seconds
		 * COUNTDOWN_TO_SESSION_TIMEOUT_IN_SECONDS - Time when warning will be
		 * shown (10 minutes before Session-Timeout)
		 */
		int timeForIdleMessage = (this.getSession()
			.getSession()
			.getMaxInactiveInterval() - BpUI.COUNTDOWN_TO_SESSION_TIMEOUT_IN_SECONDS) * 1000;

		// Notify, that you get idle after 10 minutes = 600000 millisec
		Idle.track(this, timeForIdleMessage, new Idle.Listener() {

			@Override
			public void userInactive() {
				if (getUI().getWindows()
					.isEmpty()) {
					getUI().addWindow(sessionTimeOutMessageBox);
				}
			}

			@Override
			public void userActive() {
			}
		});
	}

	public class SessionTimeOutMessageBox extends AbstractBaseDialog {

		private Embedded icon;
		private final Label message;

		/**
		 * The constructor to initialize the messageBox.
		 *
		 * @param configuration
		 *            the message box configuration.
		 */
		public SessionTimeOutMessageBox(final MessageBoxConfiguration configuration) {
			super(configuration.getAbstractBaseDialogConfiguration());

			setClosable(false);
			setResizable(false);

			addStyleName(DSuiteTheme.MESSAGE_BOX);

			// Adding default close click listener to Continue button
			getButton(DialogButton.CONTINUE).addClickListener(event -> UI.getCurrent()
				.removeWindow(this));

			this.message = new Label(configuration.getHtmlMessage(), ContentMode.HTML);

			if (configuration.getMessageBoxIcon() != null) {
				this.icon = new Embedded(null, configuration.getMessageBoxIcon()
					.getIcon());
				this.icon.setWidth(configuration.getIconWidth() + "px");
				this.icon.setHeight(configuration.getIconHeight() + "px");
			}

		}

		@Override
		protected Component layout() {

			final HorizontalLayout contentLayout = new HorizontalLayout();
			if (this.icon != null) {
				contentLayout.addComponent(this.icon);
				contentLayout.setComponentAlignment(this.icon, Alignment.MIDDLE_CENTER);
			}
			if (this.message != null) {
				VerticalLayout messageLyout = new VerticalLayout();
				messageLyout.setSizeFull();

				messageLyout.addComponent(this.message);
				messageLyout.setComponentAlignment(this.message, Alignment.MIDDLE_LEFT);

				// Add Countdown, who counts back 10 minutes till
				// Session-Timeout
				CountdownClock clock = new CountdownClock();
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, BpUI.COUNTDOWN_TO_SESSION_TIMEOUT_IN_SECONDS);
				clock.setDate(c.getTime());
				clock.setFormat("<span><strong>" + I18N.get("COUNTDOWN_SESSION_TIMEOUT_TEXT", "%m", "%s")
						+ "</strong></span>");

				messageLyout.addComponent(clock);
				messageLyout.setComponentAlignment(clock, Alignment.MIDDLE_LEFT);

				messageLyout.setSpacing(true);

				contentLayout.addComponent(messageLyout);
			}
			contentLayout.setSpacing(true);
			contentLayout.setMargin(true);

			return FluentUI.vertical()
				.add(contentLayout)
				.sizeFull()
				.get();

		}
	}

}