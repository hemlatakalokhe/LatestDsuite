package de.bonprix.vaadin.mvp.dialog;

import org.springframework.beans.factory.annotation.Autowired;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.base.AbstractMvpBasePresenter;
import de.bonprix.vaadin.provider.UiDialogProvider;

/**
 * Presenter part of mvp for {@link AbstractBaseDialog}
 *
 * @author thacht
 *
 * @param <VIEW> view interface
 */
@SuppressWarnings("rawtypes")
@SpringPresenter
public abstract class AbstractMvpDialogPresenter<VIEW extends MvpDialogView> extends AbstractMvpBasePresenter<VIEW> implements MvpDialogPresenter {

    @Autowired
    private UiDialogProvider uiDialogProvider;

    /**
     * opens the dialog
     */
    public void open() {
        this.uiDialogProvider.openDialog(getView());
    }

    /**
     * closes the dialog
     */
    public void close() {
        this.uiDialogProvider.closeCurrentDialog();
    }

}
