package de.bonprix.modules.search;

import com.vaadin.spring.annotation.SpringView;

import de.bonprix.VaadinUI;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.searchview.AbstractMvpSearchView;

@SpringView(name = SearchViewImpl.VIEW_NAME, ui = { VaadinUI.class }, isDefault = false, order = 30)
public class SearchViewImpl extends AbstractMvpSearchView<SearchViewPresenter> {

	private static final long serialVersionUID = -5286904757886705075L;
	public static final String VIEW_NAME = "SEARCHFILTER";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI	.vertical()
									.add(FluentUI	.label()
													.valueKey("some text")
													.get())
									.get());
	}

}
