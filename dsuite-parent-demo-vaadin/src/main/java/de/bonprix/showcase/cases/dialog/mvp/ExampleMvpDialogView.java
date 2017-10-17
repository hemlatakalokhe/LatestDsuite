package de.bonprix.showcase.cases.dialog.mvp;

import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

public interface ExampleMvpDialogView<PRESENTER extends ExampleMvpDialogView.Presenter>
		extends MvpDialogView<PRESENTER> {

	interface Presenter extends MvpDialogPresenter {

	}

}
