package de.bonprix.vaadin.mvp.view.searchfilter;

import de.bonprix.vaadin.mvp.base.MvpBasePresenter;
import de.bonprix.vaadin.mvp.view.searchview.MvpSearchViewPresenter;
import de.bonprix.vaadin.searchfilter.SearchViewFilter.SubmitListener;

/**
 * Interface for {@link AbstractMvpSearchFilterPresenter} to be called by
 * {@link MvpSearchFilterView}
 * 
 * @author thacht
 */
public interface MvpSearchFilterPresenter<BEANTYPE> extends MvpBasePresenter {

	void init(MvpSearchViewPresenter searchViewPresenter);

	void addSubmitListener(SubmitListener<BEANTYPE> submitListener);
}
