package de.bonprix.vaadin.mvp.view.searchview;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;

import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;
import de.bonprix.vaadin.mvp.view.searchfilter.MvpSearchFilterPresenter;
import de.bonprix.vaadin.searchfilter.SearchViewFilter;

/**
 * Presenter part of mvp for {@link View}
 * 
 * @author thacht
 *
 * @param <VIEW>
 *            view interface
 */
public abstract class AbstractMvpSearchViewPresenter<VIEW extends MvpSearchView, SEARCHFILTERPRESENTER extends MvpSearchFilterPresenter<BEANTYPE>, BEANTYPE>
		extends AbstractMvpViewPresenter<VIEW> implements MvpSearchViewPresenter<VIEW> {

	@Autowired
	SEARCHFILTERPRESENTER searchFilterPresenter;

	protected SEARCHFILTERPRESENTER getSearchFilterPresenter() {
		return this.searchFilterPresenter;
	}

	@Override
	public void initSearchViewFilter() {
		this.searchFilterPresenter.init(this);
		this.searchFilterPresenter.addSubmitListener(bean -> onSubmitForm(bean));
	}

	@Override
	public void setSearchViewFilter(SearchViewFilter searchViewFilter) {
		this.getView()
			.setSearchViewFilter(searchViewFilter);
	}

	public abstract void onSubmitForm(BEANTYPE bean);

}
