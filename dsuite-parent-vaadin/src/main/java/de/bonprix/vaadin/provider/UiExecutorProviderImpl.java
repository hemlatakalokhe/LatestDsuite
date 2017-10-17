/**
 *
 */
package de.bonprix.vaadin.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;

import de.bonprix.vaadin.dialog.ProgressWindow;
import de.bonprix.vaadin.error.BPErrorMessageBoxPresenter;

/**
 * @author cthiel
 * @date 22.11.2016
 *
 */
@UIScope
@Component
public class UiExecutorProviderImpl implements UiExecutorProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiExecutorProviderImpl.class);

    @Autowired
    private UiDialogProvider uiDialogProvider;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void executeBlocking(final String messageKey, final Runnable runnable) {
        if (this.uiDialogProvider.uiIsBlocked()) {
            throw new IllegalStateException("Only one blocking task can be active");
        }

        this.uiDialogProvider.blockUi(messageKey);

        final Runnable wrapper = () -> {
            try {
                runnable.run();
            }
            catch (final Exception e) {
                final BPErrorMessageBoxPresenter presenter = this.applicationContext.getBean(BPErrorMessageBoxPresenter.class);
                presenter.setError(e);
                presenter.open();

                UiExecutorProviderImpl.LOGGER.error("Uncaught error occurred while executing background request", e);
            }
            finally {
                this.uiDialogProvider.unblockUi();
            }
        };

        final Thread t = new Thread(wrapper);
        t.start();
    }

    @Override
    public void executeBlocking(final Runnable runnable) {
        executeBlocking("PLEASE_WAIT", runnable);
    }

    @Override
    public void executeBlocking(final BackgroundTask backgroundTask) {
        if (this.uiDialogProvider.uiIsBlocked()) {
            throw new IllegalStateException("Only one blocking task can be active");
        }

        final ProgressWindow window = this.applicationContext.getBean(ProgressWindow.class);
        window.setTask(backgroundTask);
        this.uiDialogProvider.blockUi(window);

        final Runnable wrapper = () -> {
            try {
                backgroundTask.run(window);
            }
            catch (final Exception e) {
                final BPErrorMessageBoxPresenter presenter = this.applicationContext.getBean(BPErrorMessageBoxPresenter.class);
                presenter.setError(e);
                presenter.open();

                UiExecutorProviderImpl.LOGGER.error("Uncaught error occurred while executing background request", e);
            }
        };

        final Thread t = new Thread(wrapper);
        t.start();
    }

    @Override
    public void executeInUiContext(final Runnable runnable) {
        UI.getCurrent()
            .access(runnable);
    }

}
