package de.bonprix.vaadin.mvp.base.template;

import de.bonprix.vaadin.mvp.base.MvpBasePresenter;
import de.bonprix.vaadin.mvp.base.MvpBaseView;

/**
 * This is just a template for every {@link AbstractMvpBaseView} and it provides
 * the basic functionality that is normally used with this model-view-presenter
 * framework.
 * 
 * @author thacht
 *
 * @param <PRESENTER>
 *            presenter interface
 */
public abstract class AbstractMvpBaseView<PRESENTER extends MvpBasePresenter>
		/* extends SomeVaadinClassForMvp */ implements MvpBaseView<PRESENTER> {

	PRESENTER presenter;

	/**
	 * returns the corresponding presenter of mvp
	 *
	 * @return presenter
	 */
	protected PRESENTER getPresenter() {
		return this.presenter;
	}

}
