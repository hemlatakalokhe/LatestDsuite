package de.bonprix.showcase.cases.panel;

import org.vaadin.sliderpanel.SliderPanelStyles;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TabSheet;

import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.SliderPanelNoLayover;

@SpringViewComponent
public class SlidingPanelNoOverlayDemo extends ShowcaseWrapper {

	private static final long serialVersionUID = 1L;

	private static final String TEXT_BELOW = "<h1>SlidingPanel</h1><h3>Some content below the Slider...</h3><p>Lorem ipsum dolor sit amet,consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempor invidunt ut laborebe et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos et accusam et justo duo dolorest et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempor invidunte ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
			+ "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. "
			+ "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, "
			+ "no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>";

	private static final String TEXT_IN = "<h1>SlidingPanel</h1><h3>Some content in the Slider...</h3><p>Some Content</p>";

	private static final String SLIDER = "Slider";

	public SlidingPanelNoOverlayDemo() {
		super("PANEL", "SLIDINGPANELNOOVERLAYDEMO");
	}

	@Override
	protected com.vaadin.ui.Component createLayout() {
		CheckBox gridContentCheckBox = new CheckBox("Display grid in slider");

		Slider slider = new Slider("pixel", 100, 400);
		Slider sliderPercentage = new Slider("percentage", 20, 80);
		slider.setValue(350d);
		sliderPercentage.setValue(45d);

		SliderPanelNoLayover rightSlider = new SliderPanelNoLayover(getMainContent(),
				getExampleLayout(gridContentCheckBox.getValue()), SliderMode.RIGHT, SliderTabPosition.MIDDLE,
				SlidingPanelNoOverlayDemo.SLIDER, SliderPanelStyles.COLOR_WHITE, slider.getValue()
					.floatValue(),
				sliderPercentage.getValue()
					.floatValue(),
				Unit.PIXELS);
		SliderPanelNoLayover leftSlider = new SliderPanelNoLayover(getMainContent(),
				getExampleLayout(gridContentCheckBox.getValue()), SliderMode.LEFT, SliderTabPosition.MIDDLE,
				SlidingPanelNoOverlayDemo.SLIDER, SliderPanelStyles.COLOR_WHITE, slider.getValue()
					.floatValue(),
				sliderPercentage.getValue()
					.floatValue(),
				Unit.PIXELS);

		SliderPanelNoLayover topSlider = new SliderPanelNoLayover(getMainContent(),
				getExampleLayout(gridContentCheckBox.getValue()), SliderMode.TOP, SliderTabPosition.MIDDLE,
				SlidingPanelNoOverlayDemo.SLIDER, SliderPanelStyles.COLOR_WHITE, slider.getValue()
					.floatValue(),
				sliderPercentage.getValue()
					.floatValue(),
				Unit.PIXELS);
		SliderPanelNoLayover bottomSlider = new SliderPanelNoLayover(getMainContent(),
				getExampleLayout(gridContentCheckBox.getValue()), SliderMode.BOTTOM, SliderTabPosition.MIDDLE,
				SlidingPanelNoOverlayDemo.SLIDER, SliderPanelStyles.COLOR_WHITE, slider.getValue()
					.floatValue(),
				sliderPercentage.getValue()
					.floatValue(),
				Unit.PIXELS);

		slider.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				rightSlider.setFixedContentSize(slider.getValue()
					.intValue(), Unit.PIXELS);
				leftSlider.setFixedContentSize(slider.getValue()
					.intValue(), Unit.PIXELS);
				topSlider.setFixedContentSize(slider.getValue()
					.intValue(), Unit.PIXELS);
				bottomSlider.setFixedContentSize(slider.getValue()
					.intValue(), Unit.PIXELS);
			}
		});

		sliderPercentage.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				rightSlider.setFixedContentSize(sliderPercentage.getValue()
					.intValue(), Unit.PERCENTAGE);
				leftSlider.setFixedContentSize(sliderPercentage.getValue()
					.intValue(), Unit.PERCENTAGE);
				topSlider.setFixedContentSize(sliderPercentage.getValue()
					.intValue(), Unit.PERCENTAGE);
				bottomSlider.setFixedContentSize(sliderPercentage.getValue()
					.intValue(), Unit.PERCENTAGE);
			}
		});

		gridContentCheckBox.addValueChangeListener(event -> {
			rightSlider.getSliderPanel()
				.setContent(this.getExampleLayout(gridContentCheckBox.getValue()));
			leftSlider.getSliderPanel()
				.setContent(this.getExampleLayout(gridContentCheckBox.getValue()));
			topSlider.getSliderPanel()
				.setContent(this.getExampleLayout(gridContentCheckBox.getValue()));
			bottomSlider.getSliderPanel()
				.setContent(this.getExampleLayout(gridContentCheckBox.getValue()));
		});

		Component sliderRightSheet = FluentUI.horizontal()
			.sizeFull()
			.captionKey("SLIDER_RIGHT")
			.add(rightSlider)
			.get();

		Component sliderBottomSheet = FluentUI.vertical()
			.sizeFull()
			.captionKey("SLIDER_BOTTUM")
			.add(bottomSlider)
			.get();

		Component sliderLeftSheet = FluentUI.horizontal()
			.sizeFull()
			.captionKey("SLIDER_LEFT")
			.add(leftSlider)
			.get();

		Component sliderTopSheet = FluentUI.vertical()
			.sizeFull()
			.captionKey("SLIDER_TOP")
			.add(topSlider)
			.get();

		TabSheet tabSheet = new TabSheet(sliderRightSheet, sliderTopSheet, sliderBottomSheet, sliderLeftSheet);
		tabSheet.addStyleName(DSuiteTheme.TABSHEET_INVERTED_TABCONTENT);
		tabSheet.setSizeFull();

		return FluentUI.panel()
			.style(DSuiteTheme.PANEL_INVERTED)
			.sizeFull()
			.content(FluentUI.vertical()
				.sizeFull()
				.add(FluentUI.horizontal()
					.sizeFull()
					.add(slider)
					.add(sliderPercentage)
					.add(gridContentCheckBox)
					.get(), 0.1f)
				.add(tabSheet, 0.9f)
				.get())
			.get();

	}

	private Component getExampleLayout(Boolean gridLayout) {
		if (gridLayout) {
			BeanItemGrid<Inhabitants> grid = new BeanItemGrid<>(Inhabitants.class);
			grid.addAllBeans(GridDummyData.genInhabitants(20));
			grid.setSizeFull();

			return FluentUI.vertical()
				.margin()
				.sizeFull()
				.add(grid)
				.get();
		}

		return FluentUI.vertical()
			.sizeFull()
			.margin()
			.add(FluentUI.label()
				.sizeFull()
				.contentMode(ContentMode.HTML)
				.value(SlidingPanelNoOverlayDemo.TEXT_IN)
				.get())
			.get();
	}

	private Component getMainContent() {
		return FluentUI.horizontal()
			.sizeFull()
			.margin()
			.add(FluentUI.label()
				.sizeFull()
				.contentMode(ContentMode.HTML)
				.value(SlidingPanelNoOverlayDemo.TEXT_BELOW)
				.get(), Alignment.MIDDLE_CENTER)
			.get();
	}

}
