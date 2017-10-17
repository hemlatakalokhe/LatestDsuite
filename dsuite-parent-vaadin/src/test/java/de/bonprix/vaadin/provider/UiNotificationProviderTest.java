package de.bonprix.vaadin.provider;

import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogButtonAction;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.messagebox.MessageBoxConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@PrepareForTest({ UI.class, MessageBox.class })
public class UiNotificationProviderTest extends StaticMethodAwareUnitTest {

	private final UiNotificationProvider uiNotificationProvider = new UiNotificationProviderImpl();

	private UI mockUI;

	private MessageBox mockMessageBox;

	private Page mockPage;

	private DialogConfiguration mockConfiguration;

	@BeforeMethod
	public void init() throws Exception {
		this.mockUI = PowerMockito.mock(UI.class);
		this.mockMessageBox = PowerMockito.mock(MessageBox.class);
		this.mockPage = PowerMockito.mock(Page.class);
		this.mockConfiguration = PowerMockito.mock(DialogConfiguration.class);

		PowerMockito.mockStatic(UI.class);
		Mockito.when(UI.getCurrent())
			.thenReturn(this.mockUI);
		Mockito.when(this.mockUI.getPage())
			.thenReturn(this.mockPage);

		PowerMockito.whenNew(MessageBox.class)
			.withAnyArguments()
			.thenReturn(this.mockMessageBox);

		Mockito.when(this.mockMessageBox.getConfiguration())
			.thenReturn(this.mockConfiguration);
	}

	@Test
	public void testShowInfoMessageBox() throws Exception {
		this.uiNotificationProvider.showInfoMessageBox("dummy");

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowQuestionMessageBox() throws Exception {
		final DialogButtonAction mockDialogButtonAction = Mockito.mock(DialogButtonAction.class);
		this.uiNotificationProvider.showQuestionMessageBox("dummy", mockDialogButtonAction);

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowQuestionMessageBoxYes() throws Exception {
		DialogButtonAction mockDialogButtonAction = Mockito.mock(DialogButtonAction.class);
		this.uiNotificationProvider.showQuestionMessageBox("dummy", mockDialogButtonAction, DialogButton.YES);

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowQuestionMessageBoxNo() throws Exception {
		DialogButtonAction mockDialogButtonAction = Mockito.mock(DialogButtonAction.class);
		this.uiNotificationProvider.showQuestionMessageBox("dummy", mockDialogButtonAction, DialogButton.NO);

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowWarningMessageBox() throws Exception {
		this.uiNotificationProvider.showWarningMessageBox("dummy");

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowErrorMessageBox() throws Exception {
		this.uiNotificationProvider.showErrorMessageBox("dummy");

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowMessageBox() throws Exception {
		final MessageBoxConfiguration mockMessageBoxConfiguration = Mockito.mock(MessageBoxConfiguration.class);
		this.uiNotificationProvider.showMessageBox(mockMessageBoxConfiguration);

		PowerMockito.verifyPrivate(this.mockMessageBox)
			.invoke("open");
	}

	@Test
	public void testShowInfoNotification1() {
		this.uiNotificationProvider.showInfoNotification("dummy");
	}

	@Test
	public void testShowInfoNotification2() {
		this.uiNotificationProvider.showInfoNotification("dummy", "dummy");
	}

	@Test
	public void testShowWarningNotification() {
		this.uiNotificationProvider.showWarningNotification("dummy", "dummy");
	}

	@Test
	public void testShowErrorNotification() {
		this.uiNotificationProvider.showErrorNotification("dummy", "dummy");
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testNotificationWithCustomDelay() {

		// ----Set up----
		doNothing().when(this.mockPage)
			.showNotification(any());

		// ----Call tested method----
		this.uiNotificationProvider.showInfoNotification("dummy", "dummy", 1000);

		// ----Asserts----
		final ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

		verify(this.mockPage, times(1)).showNotification(notificationArgumentCaptor.capture());

		final Notification resultNotification = notificationArgumentCaptor.getValue();
		assertThat(resultNotification.getDelayMsec(), is(1000));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testNotificationWithDefaultDelay() {

		// ----Set up----
		doNothing().when(this.mockPage)
			.showNotification(any());

		// ----Call tested method----
		this.uiNotificationProvider.showInfoNotification("dummy", "dummy");

		// ----Asserts----
		final ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

		verify(this.mockPage, times(1)).showNotification(notificationArgumentCaptor.capture());

		final Notification resultNotification = notificationArgumentCaptor.getValue();
		assertThat(resultNotification.getDelayMsec(), is(2000));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testAllNotificationTypesWithCustomDelay() {

		// ----Set up----
		doNothing().when(this.mockPage)
			.showNotification(any());

		// ----Call tested method----
		this.uiNotificationProvider.showInfoNotification("dummy", "dummy", 1000);
		this.uiNotificationProvider.showWarningNotification("dummy", "dummy", 1000);
		this.uiNotificationProvider.showErrorNotification("dummy", "dummy", 1000);

		// ----Asserts----
		final ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

		verify(this.mockPage, times(3)).showNotification(notificationArgumentCaptor.capture());

		final List<Notification> resultNotification = notificationArgumentCaptor.getAllValues();
		assertThat(resultNotification.get(0)
			.getDelayMsec(), is(1000));
		assertThat(resultNotification.get(1)
			.getDelayMsec(), is(1000));
		assertThat(resultNotification.get(2)
			.getDelayMsec(), is(1000));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testCaptionOnlyNotificationWithCustomDelay() {

		// ----Set up----
		doNothing().when(this.mockPage)
			.showNotification(any());

		// ----Call tested method----
		this.uiNotificationProvider.showInfoNotification("dummy", 1000);

		// ----Asserts----
		final ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);

		verify(this.mockPage, times(1)).showNotification(notificationArgumentCaptor.capture());

		final Notification resultNotification = notificationArgumentCaptor.getValue();
		assertThat(resultNotification.getDelayMsec(), is(1000));
	}
}
