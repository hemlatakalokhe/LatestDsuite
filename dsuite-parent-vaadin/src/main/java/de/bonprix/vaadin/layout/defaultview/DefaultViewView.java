package de.bonprix.vaadin.layout.defaultview;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface DefaultViewView extends MvpView {

    interface Presenter extends MvpViewPresenter<DefaultViewViewImpl> {

    }
}
