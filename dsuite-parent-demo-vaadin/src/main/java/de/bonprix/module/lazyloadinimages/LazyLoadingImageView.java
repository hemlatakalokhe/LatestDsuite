package de.bonprix.module.lazyloadinimages;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

public interface LazyLoadingImageView extends MvpView {

    interface Presenter extends MvpViewPresenter<LazyLoadingImageView> {

        void proceedCheckBox(Boolean value, NavigationRequest request);

    }

    void checkCheckBox(NavigationRequest request);

}
