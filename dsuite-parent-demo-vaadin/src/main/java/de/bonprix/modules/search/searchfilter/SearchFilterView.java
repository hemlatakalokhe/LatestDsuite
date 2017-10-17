package de.bonprix.modules.search.searchfilter;

import de.bonprix.vaadin.mvp.view.searchfilter.MvpSearchFilterPresenter;
import de.bonprix.vaadin.mvp.view.searchfilter.MvpSearchFilterView;

public interface SearchFilterView<PRESENTER extends SearchFilterView.Presenter<BEANTYPE>, BEANTYPE extends SearchFilterBean>
		extends MvpSearchFilterView<PRESENTER, BEANTYPE> {

	interface Presenter<BEANTYPE> extends MvpSearchFilterPresenter<BEANTYPE> {

	}

}
