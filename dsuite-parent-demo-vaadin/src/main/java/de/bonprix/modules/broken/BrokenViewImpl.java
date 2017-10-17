package de.bonprix.modules.broken;

import com.vaadin.spring.annotation.SpringView;

import de.bonprix.VaadinUI;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SpringView(name = BrokenViewImpl.VIEW_NAME, ui = { VaadinUI.class }, order = 99)
public class BrokenViewImpl extends AbstractMvpView<BrokenPresenter> implements BrokenView {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "BROKEN";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.add(FluentUI.label()
				.value("This screens raises an error when navigated to, this is showing what would happen if a service call would fail.")
				.get())
			.get());
	}

}
