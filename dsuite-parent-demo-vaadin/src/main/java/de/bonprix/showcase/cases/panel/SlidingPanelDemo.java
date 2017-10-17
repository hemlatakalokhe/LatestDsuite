package de.bonprix.showcase.cases.panel;

import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class SlidingPanelDemo extends ShowcaseWrapper {

	private static final String SLIDER = "Slider";

	private static final String TEXT_BELOW = "<h1>SlidingPanel</h1><h3>Some content below the Slider...</h3><p>Lorem ipsum dolor sit amet,consetetur sadipscing elitr, "
			+ "sedi diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos eto accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus este Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempore invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos eta accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimatas sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod temporu invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos et accusama et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>";

	private static final String TEXT_IN = "<h1>SlidingPanel</h1><h3>Some content in the Slider...</h3><p>Lorem ipsum dolor sit amet,consetetur sadipscing elitr, "
			+ "sed diamo nonumy eirmod tempori invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At veru eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vere eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>";

	public SlidingPanelDemo() {
		super("PANEL", "SLIDINGPANELDEMO");
	}

	@Override
	protected com.vaadin.ui.Component createLayout() {
		Component sliderTopSheet = FluentUI.vertical()
			.spacing()
			.captionKey("SLIDER_TOP")
			.add(new SliderPanelBuilder(FluentUI.vertical()
				.margin()
				.add(new Label(SlidingPanelDemo.TEXT_IN, ContentMode.HTML))
				.get()).caption(SlidingPanelDemo.SLIDER)
					.mode(SliderMode.TOP)
					.tabPosition(SliderTabPosition.MIDDLE)
					.style(SliderPanelStyles.COLOR_WHITE)
					.build())
			.add(FluentUI.vertical()
				.margin()
				.sizeFull()
				.add(new Label(SlidingPanelDemo.TEXT_BELOW, ContentMode.HTML))
				.get(), 1f, Alignment.MIDDLE_CENTER)
			.get();

		Component sliderRightSheet = FluentUI.horizontal()
			.spacing()
			.sizeFull()
			.captionKey("SLIDER_RIGHT")
			.add(FluentUI.vertical()
				.margin()
				.sizeFull()
				.add(new Label(SlidingPanelDemo.TEXT_BELOW, ContentMode.HTML))
				.get(), 1f, Alignment.MIDDLE_CENTER)
			.add(new SliderPanelBuilder(FluentUI.vertical()
				.margin()
				.add(new Label(SlidingPanelDemo.TEXT_IN, ContentMode.HTML))
				.get()).caption(SlidingPanelDemo.SLIDER)
					.mode(SliderMode.RIGHT)
					.tabPosition(SliderTabPosition.MIDDLE)
					.style(SliderPanelStyles.COLOR_WHITE)
					.build())
			.get();

		Component sliderBottomSheet = FluentUI.vertical()
			.spacing()
			.sizeFull()
			.captionKey("SLIDER_BOTTUM")
			.add(FluentUI.vertical()
				.margin()
				.sizeFull()
				.add(new Label(SlidingPanelDemo.TEXT_BELOW, ContentMode.HTML))
				.get(), 1f, Alignment.MIDDLE_CENTER)
			.add(new SliderPanelBuilder(FluentUI.vertical()
				.margin()
				.add(new Label(SlidingPanelDemo.TEXT_IN, ContentMode.HTML))
				.get()).caption(SlidingPanelDemo.SLIDER)
					.mode(SliderMode.BOTTOM)
					.tabPosition(SliderTabPosition.MIDDLE)
					.style(SliderPanelStyles.COLOR_WHITE)
					.build())
			.get();

		Component sliderLeftSheet = FluentUI.horizontal()
			.spacing()
			.sizeFull()
			.captionKey("SLIDER_LEFT")
			.add(new SliderPanelBuilder(FluentUI.vertical()
				.margin()
				.add(new Label(SlidingPanelDemo.TEXT_IN, ContentMode.HTML))
				.get()).caption(SlidingPanelDemo.SLIDER)
					.mode(SliderMode.LEFT)
					.tabPosition(SliderTabPosition.MIDDLE)
					.style(SliderPanelStyles.COLOR_WHITE)
					.build())
			.add(FluentUI.vertical()
				.margin()
				.sizeFull()
				.add(new Label(SlidingPanelDemo.TEXT_BELOW, ContentMode.HTML))
				.get(), 1f, Alignment.MIDDLE_CENTER)
			.get();

		TabSheet tabSheet = new TabSheet(sliderTopSheet, sliderRightSheet, sliderBottomSheet, sliderLeftSheet);
		tabSheet.addStyleName(DSuiteTheme.TABSHEET_INVERTED_TABCONTENT);
		tabSheet.setSizeFull();

		return FluentUI.panel()
			.style(DSuiteTheme.PANEL_INVERTED)
			.sizeFull()
			.content(tabSheet)
			.get();
	}
}