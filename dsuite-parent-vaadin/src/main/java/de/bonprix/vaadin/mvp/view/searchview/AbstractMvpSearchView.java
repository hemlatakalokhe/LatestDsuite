package de.bonprix.vaadin.mvp.view.searchview;

import org.vaadin.sliderpanel.SliderPanel;
import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.searchfilter.SearchViewFilter;

/**
 * View part of mvp for {@link View}
 * 
 * @author thacht
 *
 * @param <PRESENTER>
 *            presenter interface
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMvpSearchView<PRESENTER extends MvpSearchViewPresenter> extends AbstractMvpView<PRESENTER>
		implements MvpSearchView {
	private static final long serialVersionUID = 1L;

	SearchViewFilter searchViewFilter;

	private SliderPanel filterSlider;

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
	}

	@Override
	protected void setCompositionRoot(Component compositionRoot) {
		super.setCompositionRoot(compositionRoot);
	}

	@Override
	public void setSearchViewFilter(SearchViewFilter searchViewFilter) {
		this.searchViewFilter = searchViewFilter;
	}

	@Override
	protected void initSurroundingLayout() {
		super.initSurroundingLayout();

		getPresenter().initSearchViewFilter();

		final VerticalLayout filterLayout = FluentUI.vertical()
													.style("filter-layout")
													.get();

		final Component primaryFilterComponents = this.searchViewFilter.getPrimaryFilterElements(Component.class);
		final Component secondaryFilterComponents = this.searchViewFilter.getSecondaryFilterElements(Component.class);

		if (primaryFilterComponents != null) {
			filterLayout.addComponent(primaryFilterComponents);
		}
		if (secondaryFilterComponents != null) {
			this.filterSlider = new SliderPanelBuilder(secondaryFilterComponents)	.mode(SliderMode.TOP)
																					.tabPosition(SliderTabPosition.END)
																					.style("filter-slider")
																					.caption("Filter")
																					.tabSize(35)
																					.expanded(this.searchViewFilter.isSecondaryFilterInitiallyExpanded())
																					.build();

			filterLayout.addComponent(this.filterSlider);
			this.searchViewFilter.addSubmitListener((bean) -> {
				if (this.searchViewFilter.isCollapseOnSubmit()) {
					AbstractMvpSearchView.this.filterSlider.setExpanded(false, true);
				}
			});
		}

		getModuleLayout().addComponent(filterLayout, 0);

	};

	protected SliderPanel getFilterSlidingPanel() {
		return this.filterSlider;
	}

}
