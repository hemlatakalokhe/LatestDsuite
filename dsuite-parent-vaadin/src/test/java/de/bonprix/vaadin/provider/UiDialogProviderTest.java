package de.bonprix.vaadin.provider;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.bonprix.I18N;
import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.i18n.localizer.I18NLocalizer;
import de.bonprix.vaadin.error.BPErrorMessageBoxImpl;
import de.bonprix.vaadin.messagebox.MessageBox;
import de.bonprix.vaadin.messagebox.MessageBoxConfiguration;
import de.bonprix.vaadin.provider.UiDialogProvider.BlockingDialog;
import de.bonprix.vaadin.provider.UiDialogProvider.Dialog;
import de.bonprix.vaadin.provider.ui.BlockingWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest({ UI.class })
public class UiDialogProviderTest extends StaticMethodAwareUnitTest {

	@InjectMocks
	private UiDialogProviderImpl uiDialogProviderImpl;

	@Mock
	private ApplicationContext applicationContext;

	private UI mockUI;

	private class StubBlockingDialog extends Window implements BlockingDialog {

	}

	private class StubDialog extends Window implements Dialog {

	}

	private class NotAWindow implements Dialog {

	}

	@BeforeMethod
	public void init() {
		this.mockUI = PowerMockito.mock(UI.class);

		mockStatic(UI.class);
		when(UI.getCurrent()).thenReturn(this.mockUI);
	}

	@Test
	public void testOpenDialogOk() {
		final BPErrorMessageBoxImpl dialog = Mockito.mock(BPErrorMessageBoxImpl.class);

		this.uiDialogProviderImpl.openDialog(dialog);

		verify(this.mockUI).addWindow(dialog);
	}

	@Test
	public void testOpenDialogCloseOther() {
		final BPErrorMessageBoxImpl dialog1 = Mockito.mock(BPErrorMessageBoxImpl.class);
		final BPErrorMessageBoxImpl dialog2 = Mockito.mock(BPErrorMessageBoxImpl.class);

		when(dialog1.isAttached()).thenReturn(true);

		this.uiDialogProviderImpl.openDialog(dialog1);
		verify(this.mockUI).addWindow(dialog1);

		this.uiDialogProviderImpl.openDialog(dialog2);
		verify(dialog1).close();
		verify(this.mockUI).addWindow(dialog2);
	}

	@Test
	public void testOpenDialogWhenDialogOpen() {
		// given
		final StubDialog currentDialog = Mockito.mock(StubDialog.class);
		final Dialog newDialog = Mockito.mock(StubDialog.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);

		// then
		verify(currentDialog).close();
		verify(this.mockUI).addWindow((Window) newDialog);
	}

	@Test
	public void testOpenBlockingDialogWhenDialogOpen() {
		// given
		final StubDialog currentDialog = Mockito.mock(StubDialog.class);
		final StubBlockingDialog newDialog = Mockito.mock(StubBlockingDialog.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);

		// then
		verify(currentDialog).close();
		verify(this.mockUI).addWindow(newDialog);
	}

	@Test
	public void testOpenErrorDialogWhenDialogOpen() {
		// given
		final StubDialog currentDialog = Mockito.mock(StubDialog.class);
		final BPErrorMessageBoxImpl newDialog = Mockito.mock(BPErrorMessageBoxImpl.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);

		// then
		verify(currentDialog).close();
		verify(this.mockUI).addWindow(newDialog);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testOpenDialogWhenBlockingDialogOpen() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		final Dialog newDialog = Mockito.mock(StubDialog.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testOpenBlockingDialogWhenBlockingDialogOpen() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		final Dialog newDialog = Mockito.mock(StubBlockingDialog.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);
	}

	@Test
	public void testOpenErrorDialogWhenBlockingDialogOpen() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		final BPErrorMessageBoxImpl newDialog = Mockito.mock(BPErrorMessageBoxImpl.class);

		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.openDialog(newDialog);

		// then
		verify(currentDialog).close();
		verify(this.mockUI).addWindow(newDialog);
	}

	@Test
	public void testUiIsBlockedNotNullAttached() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when & then
		assertThat(this.uiDialogProviderImpl.uiIsBlocked(), equalTo(true));
	}

	@Test
	public void testUiIsNotBlockedNotNullNotAttached() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		when(currentDialog.isAttached()).thenReturn(false);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when & then
		assertThat(this.uiDialogProviderImpl.uiIsBlocked(), equalTo(false));
	}

	@Test
	public void testUiIsNotBlockedNull() {
		// when & then
		assertThat(this.uiDialogProviderImpl.uiIsBlocked(), equalTo(false));
	}

	@Test
	public void testBlockUi() {
		// given
		final BlockingWindow blockingWindow = Mockito.mock(BlockingWindow.class);
		when(this.applicationContext.getBean(BlockingWindow.class)).thenReturn(blockingWindow);

		// when
		this.uiDialogProviderImpl.blockUi();

		verify(blockingWindow).setMessageKey("PLEASE_WAIT");
		verify(this.mockUI).addWindow(blockingWindow);
	}

	@Test
	public void testBlockUiMessageKey() {
		// given
		final String messageKey = "KEY";
		final BlockingWindow blockingWindow = Mockito.mock(BlockingWindow.class);
		when(this.applicationContext.getBean(BlockingWindow.class)).thenReturn(blockingWindow);

		// when
		this.uiDialogProviderImpl.blockUi(messageKey);

		verify(blockingWindow).setMessageKey(messageKey);
		verify(this.mockUI).addWindow(blockingWindow);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testBlockUiWhenAlreadyBlocked() {
		// given
		final BlockingWindow blockingWindow = Mockito.mock(BlockingWindow.class);
		when(this.applicationContext.getBean(BlockingWindow.class)).thenReturn(blockingWindow);

		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.blockUi();

		verify(this.mockUI).addWindow(blockingWindow);
	}

	@Test
	public void testUnblockUiBlocked() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.unblockUi();

		// then
		verify(currentDialog).close();
		assertThat(this.uiDialogProviderImpl.uiIsBlocked(), equalTo(false));
	}

	@Test
	public void testUnblockUiNotBlocked() {
		// when
		this.uiDialogProviderImpl.unblockUi();

		// just check that no exceptions are thrown
	}

	@Test
	public void testCloseCurrentDialogNoDialog() {
		// when
		this.uiDialogProviderImpl.closeCurrentDialog();

		// then
		// just check that no exceptions are thrown
	}

	@Test
	public void testCloseCurrentDialogDialog() {
		// given
		final StubDialog currentDialog = Mockito.mock(StubDialog.class);
		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.closeCurrentDialog();

		// then
		verify(currentDialog).close();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testCloseCurrentDialogBlockingDialog() {
		// given
		final StubBlockingDialog currentDialog = Mockito.mock(StubBlockingDialog.class);
		when(currentDialog.isAttached()).thenReturn(true);

		ReflectionTestUtils.setField(this.uiDialogProviderImpl, "currentDialog", currentDialog);

		// when
		this.uiDialogProviderImpl.closeCurrentDialog();

		// then
		verify(currentDialog, Mockito.never()).close();
	}

	@Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "The given view does not inherit from com.vaadin.ui.Window")
	public void testOpenDialogNotAWindowInstance() {
		this.uiDialogProviderImpl.openDialog(new NotAWindow());
	}

	@Test
	public void testOpenDialogNotAWindowInstance1() {
		final BPErrorMessageBoxImpl dialog = Mockito.mock(BPErrorMessageBoxImpl.class);
		this.uiDialogProviderImpl.openDialog(dialog);
	}

	@Test
	public void testDialogMessageIsLocalized() throws Exception {

		//----Set up----
		final I18NLocalizer mockI18NLocalizer = Mockito.mock(I18NLocalizer.class);
		when(mockI18NLocalizer.get("ERROR_DIALOG_MESSAGE")).thenReturn("translated");
		I18N.setI18NLocalizerFactory(() -> mockI18NLocalizer);

		final MessageBox mockMessageBox = Mockito.mock(MessageBox.class);

		PowerMockito.whenNew(MessageBox.class)
		            .withArguments(MessageBoxConfiguration.class)
		            .thenReturn(mockMessageBox);

		//----Call tested method----
		new BPErrorMessageBoxImpl();

		//----Asserts----
		verify(mockI18NLocalizer, times(1)).get("ERROR_DIALOG_MESSAGE");
	}

}
