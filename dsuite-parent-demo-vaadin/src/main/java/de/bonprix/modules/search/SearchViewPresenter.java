package de.bonprix.modules.search;

import de.bonprix.modules.search.searchfilter.SearchFilterBean;
import de.bonprix.modules.search.searchfilter.SearchFilterPresenter;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.searchview.AbstractMvpSearchViewPresenter;

@SpringPresenter
public class SearchViewPresenter
		extends AbstractMvpSearchViewPresenter<SearchViewImpl, SearchFilterPresenter, SearchFilterBean>
		implements SearchView.InterceptorPresenter {

	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		// empty
	}

	@Override
	public void onSubmitForm(SearchFilterBean bean) {
		// empty
	}

}