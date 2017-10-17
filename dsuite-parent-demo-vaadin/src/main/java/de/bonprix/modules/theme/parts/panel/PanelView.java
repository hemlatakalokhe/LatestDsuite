package de.bonprix.modules.theme.parts.panel;

import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpViewPresenter;

public interface PanelView extends MvpView {

	interface Presenter extends MvpViewPresenter<PanelViewImpl> {

	}

}
