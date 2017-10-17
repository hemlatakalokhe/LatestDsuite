package de.bonprix.vaadin.mvp.view.regular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;

import de.bonprix.vaadin.mvp.base.MvpBasePresenter;
import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * Presenter part of mvp for {@link View}
 * 
 * @author thacht
 *
 * @param <VIEW>
 *            view interface
 */
public abstract class AbstractMvpViewPresenter<VIEW extends MvpView> implements MvpViewPresenter<VIEW> {

	private VIEW view;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * sets the view into the presenter
	 */
	@Override
	public void setView(VIEW view) {
		this.view = view;
	}

	/**
	 * Returns corresponding view of mvp
	 * 
	 * @return view
	 */
	protected VIEW getView() {
		return this.view;
	}

	@Override
	public void tryNavigateTo(NavigationRequest request) {
		request.proceed();
	}

	/**
	 * Creates a new instance of the given presenter class.
	 *
	 * @param clazz
	 *            the presenter class
	 * @return the new instance of the presenter
	 */
	protected <E extends MvpBasePresenter> E createPresenter(final Class<E> clazz) {
		return this.applicationContext.getBean(clazz);
	}
}
