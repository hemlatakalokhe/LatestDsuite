package de.bonprix.vaadin.mvp.view.searchview;

import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;
import de.bonprix.vaadin.searchfilter.SearchViewFilter;

/**
 * Interface for {@link AbstractMvpSearchViewPresenter} to be called by
 * {@link AbstractMvpSearchView}
 * 
 * @author thacht
 *
 * @param <VIEW>
 *            view type
 */
public interface MvpSearchViewPresenter<VIEW extends MvpSearchView> extends MvpViewPresenter<VIEW> {

	void initSearchViewFilter();

	void setSearchViewFilter(SearchViewFilter searchViewFilter);
}
