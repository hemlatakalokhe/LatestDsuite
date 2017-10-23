package de.bonprix.module.lazyloadinimages;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringPresenter
public class LazyLoadingImagePresenter extends AbstractMvpViewPresenter<LazyLoadingImageView> implements LazyLoadingImageView.Presenter {

    @Override
    public void init() {
        //
    }

    @Override
    public void onViewEnter() {
        //
    }

    @Override
    public void proceedCheckBox(final Boolean value, final NavigationRequest request) {
    }

}
