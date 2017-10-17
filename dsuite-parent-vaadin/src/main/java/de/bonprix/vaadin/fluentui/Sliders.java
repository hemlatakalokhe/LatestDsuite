package de.bonprix.vaadin.fluentui;

import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class Sliders<CONFIG extends Sliders<CONFIG>> extends AbstractFields<Slider, Double, CONFIG> {

	protected Sliders(final Slider component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG max(final Double max) {
		get().setMax(max);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG orientation(SliderOrientation orientation) {
		get().setOrientation(orientation);
		return (CONFIG) this;
	}

}
