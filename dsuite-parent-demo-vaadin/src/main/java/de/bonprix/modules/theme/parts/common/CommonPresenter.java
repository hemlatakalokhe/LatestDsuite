package de.bonprix.modules.theme.parts.common;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class CommonPresenter extends AbstractMvpViewPresenter<CommonViewImpl> implements CommonView.Presenter {

	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		// empty
	}

}