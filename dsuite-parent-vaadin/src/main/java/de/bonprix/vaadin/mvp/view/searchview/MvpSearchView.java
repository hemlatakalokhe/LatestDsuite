package de.bonprix.vaadin.mvp.view.searchview;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.searchfilter.SearchViewFilter;

/**
 * Interface for {@link AbstractMvpSearchView} to be called by
 * {@link MvpSearchViewPresenter}
 * 
 * @author thacht
 */
public interface MvpSearchView extends MvpView {
	void setSearchViewFilter(SearchViewFilter searchViewFilter);
}
