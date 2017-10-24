package de.bonprix.showcase.cases.dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;

import de.bonprix.I18N;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiExecutorProvider;
import de.bonprix.vaadin.provider.UiExecutorProvider.BackgroundTask;
import de.bonprix.vaadin.provider.UiExecutorProvider.ProgressMonitor;
import de.bonprix.vaadin.provider.UiExecutorProvider.ProgressMonitor.LogMessageLevel;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class BackgroundTaskDemo extends ShowcaseWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackgroundTaskDemo.class);

    @Autowired
    private transient UiExecutorProvider uiExecutorProvider;

    @Autowired
    private transient UiNotificationProvider uiNotificationProvider;

    private static final String BACKGROUNDTASK_FINISHED = "BACKGROUNDTASK_FINISHED";
    private static final String FINISHED_STEP = "Finished step ";
    private static final String INTERRUPTED = "interrupted";

    public BackgroundTaskDemo() {
        super("STRUCTURE_HIERARCHY", "BACKGROUNDTASKDEMO");
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
        return FluentUI.vertical()
            .margin()
            .spacing()
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("START_ERROR_BACKGROUNDTASK")
                .onClick(event -> startErrorBackgroundTask())
                .get())
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("START_INTERRUPTIBLE_BACKGROUNDTASK")
                .onClick(event -> startInterruptibleBackgroundTask())
                .get())
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("START_AUTOCLOSE_BACKGROUNDTASK")
                .onClick(event -> startAutoCloseBackgroundTask())
                .get())
            .add(FluentUI.button()
                .style(DSuiteTheme.BUTTON_PRIMARY)
                .captionKey("START_BLOCKING_TASK")
                .onClick(event -> startBlockingTask())
                .get())
            .get();
    }

    private void startBlockingTask() {
        this.uiExecutorProvider.executeBlocking(() -> {
            try {
                Thread.sleep(2000);
                BackgroundTaskDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(BackgroundTaskDemo.BACKGROUNDTASK_FINISHED));
            }
            catch (final InterruptedException e) {
                BackgroundTaskDemo.LOGGER.error(BackgroundTaskDemo.INTERRUPTED, e);
                Thread.currentThread()
                    .interrupt();
            }
        });

    }

    private void startErrorBackgroundTask() {
        this.uiExecutorProvider.executeBlocking(new StartErrorTask());
    }

    private void startInterruptibleBackgroundTask() {
        this.uiExecutorProvider.executeBlocking(new StartInterruptibleTask());

    }

    private void startAutoCloseBackgroundTask() {
        this.uiExecutorProvider.executeBlocking(new StartAutoCloseTask());
    }

    private class StartErrorTask implements BackgroundTask {

        @Override
        public void run(final ProgressMonitor progressMonitor) {
            for (int i = 0; i < 100; i++) {
                progressMonitor.incrementProgress(1);
                if (i % 10 == 0) {
                    progressMonitor.addLog("Error in step " + i, LogMessageLevel.ERROR);
                }
                else if (i % 5 == 0) {
                    progressMonitor.addLog("Warning step " + i, LogMessageLevel.WARNING);
                }
                else {
                    progressMonitor.addLog(BackgroundTaskDemo.FINISHED_STEP + i, LogMessageLevel.SUCCESS);
                }
                try {
                    Thread.sleep(100);
                }
                catch (final InterruptedException e) {
                    BackgroundTaskDemo.LOGGER.error(BackgroundTaskDemo.INTERRUPTED, e);
                    Thread.currentThread()
                        .interrupt();
                }
            }

            progressMonitor.finish();
        }

        @Override
        public void onClose() {
            BackgroundTaskDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(BackgroundTaskDemo.BACKGROUNDTASK_FINISHED));
        }

        @Override
        public boolean isInterruptible() {
            return false;
        }
    }

    private class StartInterruptibleTask implements BackgroundTask {

        @Override
        public void run(final ProgressMonitor progressMonitor) {
            progressMonitor.setAutoClose(false);
            for (int i = 0; i < 100; i++) {
                if (progressMonitor.interruptionRequested()) {
                    break;
                }

                progressMonitor.incrementProgress(1);
                progressMonitor.addLog(BackgroundTaskDemo.FINISHED_STEP + i, LogMessageLevel.SUCCESS);
                try {
                    Thread.sleep(100);
                }
                catch (final InterruptedException e) {
                    BackgroundTaskDemo.LOGGER.error(BackgroundTaskDemo.INTERRUPTED, e);
                    Thread.currentThread()
                        .interrupt();
                }
            }

            progressMonitor.finish();
        }

        @Override
        public void onClose() {
            BackgroundTaskDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(BackgroundTaskDemo.BACKGROUNDTASK_FINISHED));
        }

        @Override
        public boolean isInterruptible() {
            return true;
        }
    }

    private class StartAutoCloseTask implements BackgroundTask {

        @Override
        public void run(final ProgressMonitor progressMonitor) {
            progressMonitor.setAutoClose(true);
            for (int i = 0; i < 100; i++) {
                progressMonitor.incrementProgress(1);
                progressMonitor.addLog(BackgroundTaskDemo.FINISHED_STEP + i, LogMessageLevel.SUCCESS);

                try {
                    Thread.sleep(100);
                }
                catch (final InterruptedException e) {
                    BackgroundTaskDemo.LOGGER.error(BackgroundTaskDemo.INTERRUPTED, e);
                    Thread.currentThread()
                        .interrupt();
                }
            }

            progressMonitor.finish();
        }

        @Override
        public void onClose() {
            BackgroundTaskDemo.this.uiNotificationProvider.showInfoNotification(I18N.get(BackgroundTaskDemo.BACKGROUNDTASK_FINISHED));
        }

        @Override
        public boolean isInterruptible() {
            return false;
        }
    }
}
