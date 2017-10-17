package de.bonprix.vaadin.fluentui;

import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class Images<CONFIG extends Images<?>> extends AbstractEmbeddeds<Image, CONFIG> {

	protected Images(final Image component) {
		super(component);
	}

	/**
	 * Call the method to apply all default configurations for a Vaadin
	 * {@link Label}.
	 */
	@Override
	public CONFIG defaults() {
		return super.defaults();
	}

	@SuppressWarnings("unchecked")
	public CONFIG onClick(ClickListener listener) {
		get().addClickListener(listener);
		return (CONFIG) this;
	}

}
