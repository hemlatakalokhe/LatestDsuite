package de.bonprix.vaadin.layout.defaultview;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class DefaultViewPresenter extends AbstractMvpViewPresenter<DefaultViewViewImpl>
		implements DefaultViewView.Presenter {

	@Override
	public void init() {

	}

	@Override
	public void onViewEnter() {
	}

}