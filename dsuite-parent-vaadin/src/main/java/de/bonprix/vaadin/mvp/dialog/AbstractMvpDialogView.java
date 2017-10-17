package de.bonprix.vaadin.mvp.dialog;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogConfiguration;

/**
 * View part of mvp for {@link AbstractBaseDialog}
 *
 * @author thacht
 *
 * @param <PRESENTER> presenter interface
 */
public abstract class AbstractMvpDialogView<PRESENTER extends MvpDialogPresenter> extends AbstractBaseDialog implements MvpDialogView<PRESENTER> {
    private static final long serialVersionUID = 1L;

    PRESENTER presenter;

    public AbstractMvpDialogView(final DialogConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void setPresenter(final PRESENTER presenter) {
        this.presenter = presenter;
    }

    /**
     * returns the corresponding presenter of mvp
     *
     * @return presenter
     */
    protected PRESENTER getPresenter() {
        return this.presenter;
    }

}
