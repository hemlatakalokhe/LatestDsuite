package de.bonprix.vaadin.mvp.dialog;

import de.bonprix.vaadin.mvp.base.MvpBaseView;
import de.bonprix.vaadin.provider.UiDialogProvider.Dialog;

/**
 * Interface for {@link AbstractMvpDialogView} to be called by {@link MvpDialogPresenter}
 *
 * @author thacht
 */
public interface MvpDialogView<PRESENTER extends MvpDialogPresenter> extends MvpBaseView<PRESENTER>, Dialog {

    void close();

    void setHeadline(String headlineKey);

    void setSubline(String sublineKey);

}
