package de.bonprix.vaadin.mvp.view.searchfilter;

import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.searchfilter.AbstractBaseSearchFilter;

/**
 * View part of mvp for {@link AbstractBaseDialog}
 * 
 * @author thacht
 *
 * @param <PRESENTER>
 *            presenter interface
 */
public abstract class AbstractMvpSearchFilterView<PRESENTER extends MvpSearchFilterPresenter<BEANTYPE>, BEANTYPE>
		extends AbstractBaseSearchFilter<BEANTYPE> implements MvpSearchFilterView<PRESENTER, BEANTYPE> {

	PRESENTER presenter;

	public AbstractMvpSearchFilterView(BEANTYPE filterBean) {
		super(filterBean);
	}

	@Override
	public void setPresenter(PRESENTER presenter) {
		this.presenter = presenter;
	}

	/**
	 * returns the corresponding presenter of mvp
	 *
	 * @return presenter
	 */
	protected PRESENTER getPresenter() {
		return this.presenter;
	}

}
