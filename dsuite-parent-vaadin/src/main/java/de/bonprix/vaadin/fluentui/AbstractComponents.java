package de.bonprix.vaadin.fluentui;

import com.vaadin.server.ErrorMessage;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * Provides a fluent API to configure a Vaadin {@link Component}.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class AbstractComponents<COMPONENT extends AbstractComponent, CONFIG extends AbstractComponents<?, ?>>
		extends Components<COMPONENT, CONFIG> {

	protected AbstractComponents(final COMPONENT component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG componentError(ErrorMessage componentError) {
		get().setComponentError(componentError);
		return (CONFIG) this;
	}

}
