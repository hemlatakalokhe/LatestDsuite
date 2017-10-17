package de.bonprix.modules.theme.parts.slider;

import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.spring.annotation.SpringView;
import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = SliderViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class SliderViewImpl extends AbstractMvpView<SliderPresenter> implements SliderView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "SLIDERS";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI	.vertical()
									.margin()
									.add(FluentUI	.label()
													.value("Slider")
													.style(DSuiteTheme.LABEL_H1)
													.get())
									.add(FluentUI	.horizontal()
													.spacing()
													.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
													.add(FluentUI	.slider()
																	.captionKey("HORIZONTAL")
																	.width(200, Unit.PIXELS)
																	.value(50d)
																	.get())
													.add(FluentUI	.slider()
																	.captionKey("VERTICAL")
																	.height(200, Unit.PIXELS)
																	.value(50d)
																	.orientation(SliderOrientation.VERTICAL)
																	.get())
													.add(FluentUI	.slider()
																	.captionKey("WITH_TICKS")
																	.width(200, Unit.PIXELS)
																	.value(3d)
																	.max(4d)
																	.get())
													.get())
									.add(FluentUI	.label()
													.value("ProgressBar")
													.style(DSuiteTheme.LABEL_H1)
													.get())
									.add(FluentUI	.horizontal()
													.spacing()
													.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
													.add(FluentUI	.progressBar()
																	.value(0.3f)
																	.width(300, Unit.PIXELS)
																	.get())
													.get())
									.get());
	}

}
