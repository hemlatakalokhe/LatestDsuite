package de.bonprix.vaadin.mvp.view.regular;

import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * Interface for {@link AbstractMvpViewPresenter} to be called by
 * {@link AbstractMvpView}
 * 
 * @author thacht
 *
 * @param <VIEW>
 *            view type
 */
public interface MvpViewPresenter<VIEW extends MvpView> {
	/**
	 * sets the view into the presenter and then calls the presenter to
	 * initialize
	 */
	void setView(VIEW view);

	/**
	 * gets called by the view after postconstruct of view is finished (for
	 * example now you can insert data into the view)
	 * 
	 * @throws InterruptedException
	 */
	void init();

	/**
	 * Gets called when the view is attached to the UI (for example adding data
	 * from the model).
	 */
	void onViewEnter();

	/**
	 * gets called from the view to check if the navigation should be proceeded.
	 * override this to change the behaviour of auto proceeding this request.
	 * 
	 * @param request
	 *            request to proceed with the navigation
	 */
	void tryNavigateTo(NavigationRequest request);
}
