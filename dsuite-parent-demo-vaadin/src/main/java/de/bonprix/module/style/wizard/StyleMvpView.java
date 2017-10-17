package de.bonprix.module.style.wizard;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface StyleMvpView extends MvpView {

    interface Presenter extends MvpViewPresenter<StyleMvpViewImpl> {

        void openMvpDialog();

        void openMvpWizard();

    }

}
