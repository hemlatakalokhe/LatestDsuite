package de.bonprix.modules.search.searchfilter;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.searchfilter.AbstractMvpSearchFilterPresenter;

@SpringPresenter
public class SearchFilterPresenter extends AbstractMvpSearchFilterPresenter<SearchFilterViewImpl, SearchFilterBean>
		implements SearchFilterView.Presenter<SearchFilterBean> {

}