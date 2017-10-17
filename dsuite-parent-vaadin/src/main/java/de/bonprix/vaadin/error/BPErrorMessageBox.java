/**
 *
 */
package de.bonprix.vaadin.error;

import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

/**
 * @author cthiel
 * @date 17.11.2016
 *
 */
public interface BPErrorMessageBox extends MvpDialogView<BPErrorMessageBoxPresenter> {

    /**
     * Set the error to visualize
     *
     * @param error the error
     */
    void setError(Throwable error);

}
