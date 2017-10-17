package de.bonprix.vaadin.mvp.view.regular;

import com.vaadin.navigator.View;

import de.bonprix.vaadin.navigator.NavigationRequest;

/**
 * Interface for {@link AbstractMvpView} to be called by
 * {@link MvpViewPresenter}
 * 
 * @author thacht
 */
public interface MvpView extends View {

	void tryNavigateTo(NavigationRequest request);
}
