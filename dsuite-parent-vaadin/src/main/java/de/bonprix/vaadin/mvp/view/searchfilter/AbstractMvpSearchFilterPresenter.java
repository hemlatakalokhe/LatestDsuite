package de.bonprix.vaadin.mvp.view.searchfilter;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.base.AbstractMvpBasePresenter;
import de.bonprix.vaadin.mvp.view.searchview.MvpSearchViewPresenter;
import de.bonprix.vaadin.searchfilter.SearchViewFilter.SubmitListener;

/**
 * Presenter part of mvp for {@link AbstractBaseDialog}
 * 
 * @author thacht
 *
 * @param <VIEW>
 *            view interface
 */
@SpringPresenter
public abstract class AbstractMvpSearchFilterPresenter<VIEW extends AbstractMvpSearchFilterView, BEANTYPE>
		extends AbstractMvpBasePresenter<VIEW> implements MvpSearchFilterPresenter<BEANTYPE> {

	MvpSearchViewPresenter searchViewPresenter;

	@Override
	public void init(MvpSearchViewPresenter searchViewPresenter) {
		this.searchViewPresenter = searchViewPresenter;
		searchViewPresenter.setSearchViewFilter(getView());
	}

	@Override
	public void addSubmitListener(SubmitListener<BEANTYPE> submitListener) {
		getView().addSubmitListener(submitListener);
	}

}
