package de.bonprix.module.style.dialogview.ratingstar;

import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

public interface RatingStarDialogView <PRESENTER extends RatingStarDialogView.Presenter>
extends MvpDialogView<PRESENTER>{
    interface Presenter extends MvpDialogPresenter {

    }

}
