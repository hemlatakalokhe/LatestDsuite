package de.bonprix.module.style;

import de.bonprix.module.style.ui.LanguageGridViewImpl;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface LanguageGridView extends MvpView {

    interface LanguageGridPresenter extends MvpViewPresenter<LanguageGridViewImpl> {

    }

}
