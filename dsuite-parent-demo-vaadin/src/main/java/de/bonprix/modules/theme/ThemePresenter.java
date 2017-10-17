package de.bonprix.modules.theme;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class ThemePresenter extends AbstractMvpViewPresenter<ThemeViewImpl> implements ThemeView.Presenter {

	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		// empty
	}

}