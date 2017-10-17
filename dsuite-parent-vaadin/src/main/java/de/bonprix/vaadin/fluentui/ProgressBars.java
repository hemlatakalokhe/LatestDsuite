package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class ProgressBars<CONFIG extends ProgressBars<CONFIG>> extends AbstractFields<ProgressBar, Float, CONFIG> {

	protected ProgressBars(final ProgressBar component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG max(boolean indeterminate) {
		get().setIndeterminate(indeterminate);
		return (CONFIG) this;
	}

}
