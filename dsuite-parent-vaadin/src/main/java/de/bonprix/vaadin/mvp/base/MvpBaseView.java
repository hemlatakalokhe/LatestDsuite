package de.bonprix.vaadin.mvp.base;

import de.bonprix.vaadin.mvp.base.template.AbstractMvpBaseView;

/**
 * Interface for {@link AbstractMvpBaseView} to be called by
 * {@link MvpBasePresenter}
 * 
 * @author thacht
 *
 * @param <PRESENTER>
 */
public interface MvpBaseView<PRESENTER extends MvpBasePresenter> {

	/**
	 * sets the presenter into the view and then calls the view to initialize
	 * (for example adding data from the model)
	 */
	void setPresenter(PRESENTER presenter);

}
