package de.bonprix.modules.theme;

import com.vaadin.spring.annotation.SpringView;

import de.bonprix.VaadinUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SpringView(name = ThemeViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class ThemeViewImpl extends AbstractMvpView<ThemePresenter> implements ThemeView {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "THEME";

	@Override
	protected void initializeUI() {
		// empty
	}

}
