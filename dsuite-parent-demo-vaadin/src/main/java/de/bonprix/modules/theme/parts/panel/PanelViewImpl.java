package de.bonprix.modules.theme.parts.panel;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = PanelViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class PanelViewImpl extends AbstractMvpView<PanelPresenter> implements PanelView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "PANEL";
	private static final String NORMAL = "NORMAL";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.margin()
			.add(FluentUI.label()
				.value("Panel")
				.style(DSuiteTheme.LABEL_H1)
				.get())
			.add(FluentUI.label()
				.valueKey(NORMAL)
				.style(DSuiteTheme.LABEL_H2)
				.get())
			.add(FluentUI.horizontal()
				.margin()
				.spacing()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.panel()
					.captionKey(NORMAL)
					.content(panelContent())
					.get())
				.add(FluentUI.panel()
					.style(DSuiteTheme.PANEL_SCROLL_INDICATOR)
					.height(18, Unit.EM)
					.captionKey("SCROLL_DIVIDER")
					.content(panelContentScroll())
					.get())
				.get())
			.add(FluentUI.label()
				.value("Inverted Panel")
				.style(DSuiteTheme.LABEL_H2)
				.get())
			.add(FluentUI.horizontal()
				.margin()
				.spacing()
				.style(	DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING,
						DSuiteTheme.BACKGROUND_COLOR__SECONDARY_COLOR_ULTRA_LIGHT)
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.panel()
					.captionKey(NORMAL)
					.style(DSuiteTheme.PANEL_INVERTED)
					.content(panelContent())
					.get())
				.add(FluentUI.panel()
					.style(DSuiteTheme.PANEL_SCROLL_INDICATOR)
					.height(18, Unit.EM)
					.captionKey("SCROLL_DIVIDER")
					.style(DSuiteTheme.PANEL_INVERTED)
					.content(panelContentScroll())
					.get())
				.get())
			.get());
	}

	Component panelContent() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label content = new Label(
				"Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio.");
		content.setWidth("10em");
		layout.addComponent(content);
		Button button = new Button("Button");
		button.setSizeFull();
		layout.addComponent(button);
		return layout;
	}

	Component panelContentScroll() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label content = new Label(
				"Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio. Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio.");
		content.setWidth("10em");
		layout.addComponent(content);
		Button button = new Button("Button");
		layout.addComponent(button);
		return layout;
	}

}
