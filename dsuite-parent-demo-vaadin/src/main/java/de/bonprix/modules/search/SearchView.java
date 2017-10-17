package de.bonprix.modules.search;

import de.bonprix.vaadin.mvp.view.searchview.MvpSearchView;
import de.bonprix.vaadin.mvp.view.searchview.MvpSearchViewPresenter;

public interface SearchView extends MvpSearchView {

	interface InterceptorPresenter extends MvpSearchViewPresenter<SearchViewImpl> {

	}

}
