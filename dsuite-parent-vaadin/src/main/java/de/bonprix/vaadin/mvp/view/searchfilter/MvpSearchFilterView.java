package de.bonprix.vaadin.mvp.view.searchfilter;

import de.bonprix.vaadin.mvp.base.MvpBaseView;

/**
 * Interface for {@link AbstractMvpSearchFilterView} to be called by
 * {@link MvpSearchFilterPresenter}
 * 
 * @author thacht
 */
public interface MvpSearchFilterView<PRESENTER extends MvpSearchFilterPresenter<BEANTYPE>, BEANTYPE>
		extends MvpBaseView<PRESENTER> {

}
