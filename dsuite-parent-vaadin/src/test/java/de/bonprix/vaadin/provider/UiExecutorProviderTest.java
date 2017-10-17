package de.bonprix.vaadin.provider;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.ui.UI;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bonprix.StaticMethodAwareUnitTest;
import de.bonprix.vaadin.dialog.ProgressWindow;
import de.bonprix.vaadin.error.BPErrorMessageBoxPresenter;
import de.bonprix.vaadin.provider.UiExecutorProvider.BackgroundTask;
import de.bonprix.vaadin.provider.UiExecutorProvider.ProgressMonitor;

@PrepareForTest({ UI.class })
public class UiExecutorProviderTest extends StaticMethodAwareUnitTest {

    @InjectMocks
    private UiExecutorProviderImpl uiExecutorProviderImpl;

    @Mock
    private UiDialogProvider uiDialogProvider;

    @Mock
    private ApplicationContext applicationContext;

    private UI mockUI;

    @BeforeMethod
    public void init() {
        this.mockUI = PowerMockito.mock(UI.class);

        mockStatic(UI.class);
        when(UI.getCurrent()).thenReturn(this.mockUI);
    }

    private class ThreadAwareRunnable implements Runnable {
        private String threadname;

        @Override
        public void run() {
            this.threadname = Thread.currentThread()
                .getName();
        }
    }

    private class ErrorThrowingRunnable implements Runnable {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }

    private class ThreadAwareTask implements BackgroundTask {
        private String threadname;

        @Override
        public void run(final ProgressMonitor progressMonitor) {
            this.threadname = Thread.currentThread()
                .getName();
        }

        @Override
        public boolean isInterruptible() {
            return false;
        }

        @Override
        public void onClose() {
        }
    }

    private class ErrorThrowingTask implements BackgroundTask {

        @Override
        public void run(final ProgressMonitor progressMonitor) {
            throw new RuntimeException();
        }

        @Override
        public boolean isInterruptible() {
            return false;
        }

        @Override
        public void onClose() {
        }
    }

    @Test
    public void testExecuteBlocking() throws Exception {
        final ThreadAwareRunnable run = new ThreadAwareRunnable();
        this.uiExecutorProviderImpl.executeBlocking(run);

        Thread.sleep(100);

        assertThat(run.threadname, not(equalTo(Thread.currentThread()
            .getName())));
        verify(this.uiDialogProvider).blockUi("PLEASE_WAIT");
        verify(this.uiDialogProvider).unblockUi();
    }

    @Test
    public void testExecuteBlockingMessageKey() throws Exception {
        final String messageKey = "KEY";
        final ThreadAwareRunnable run = new ThreadAwareRunnable();

        this.uiExecutorProviderImpl.executeBlocking(messageKey, run);

        Thread.sleep(100);

        assertThat(run.threadname, notNullValue());
        assertThat(run.threadname, not(equalTo(Thread.currentThread()
            .getName())));
        verify(this.uiDialogProvider).blockUi(messageKey);
        verify(this.uiDialogProvider).unblockUi();
    }

    @Test
    public void testExecuteBlockingError() throws Exception {
        // given
        final ErrorThrowingRunnable run = new ErrorThrowingRunnable();
        final BPErrorMessageBoxPresenter errorDialog = Mockito.mock(BPErrorMessageBoxPresenter.class);
        when(this.applicationContext.getBean(BPErrorMessageBoxPresenter.class)).thenReturn(errorDialog);

        // when
        this.uiExecutorProviderImpl.executeBlocking(run);

        Thread.sleep(100);

        // then
        verify(this.uiDialogProvider).blockUi("PLEASE_WAIT");
        verify(errorDialog).open();
    }

    @Test(
        expectedExceptions = IllegalStateException.class)
    public void testExecuteBlockingAlreadyBlocked() throws Exception {
        final ThreadAwareRunnable run = new ThreadAwareRunnable();
        when(this.uiDialogProvider.uiIsBlocked()).thenReturn(true);

        this.uiExecutorProviderImpl.executeBlocking(run);
    }

    @Test
    public void testExecuteBlockingTaskError() throws Exception {
        final ErrorThrowingTask run = new ErrorThrowingTask();

        // given
        final BPErrorMessageBoxPresenter errorDialog = Mockito.mock(BPErrorMessageBoxPresenter.class);
        final ProgressWindow progressWindow = mock(ProgressWindow.class);
        when(this.applicationContext.getBean(BPErrorMessageBoxPresenter.class)).thenReturn(errorDialog);
        when(this.applicationContext.getBean(ProgressWindow.class)).thenReturn(progressWindow);

        // when
        this.uiExecutorProviderImpl.executeBlocking(run);

        Thread.sleep(100);

        // then
        verify(this.uiDialogProvider).blockUi(progressWindow);
        verify(errorDialog).open();
    }

    @Test
    public void testExecuteBlockingTask() throws Exception {
        final ThreadAwareTask run = new ThreadAwareTask();
        final ProgressWindow progressWindow = mock(ProgressWindow.class);
        when(this.applicationContext.getBean(ProgressWindow.class)).thenReturn(progressWindow);

        this.uiExecutorProviderImpl.executeBlocking(run);

        Thread.sleep(100);

        assertThat(run.threadname, notNullValue());
        assertThat(run.threadname, not(equalTo(Thread.currentThread()
            .getName())));
        verify(this.uiDialogProvider).blockUi(progressWindow);
        verify(progressWindow).setTask(run);
    }

    @Test(
        expectedExceptions = IllegalStateException.class)
    public void testExecuteBlockingTaskAlreadyBlocked() throws Exception {
        // given
        final ThreadAwareTask run = new ThreadAwareTask();
        when(this.uiDialogProvider.uiIsBlocked()).thenReturn(true);

        // when
        this.uiExecutorProviderImpl.executeBlocking(run);
    }

    @Test
    public void textExecuteInUiContext() {
        final ThreadAwareRunnable run = new ThreadAwareRunnable();
        this.uiExecutorProviderImpl.executeInUiContext(run);

        verify(this.mockUI).access(run);

    }
}
