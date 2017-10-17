package de.bonprix.vaadin.fluentui;

import de.bonprix.vaadin.ui.ComponentBar;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarElement;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class ComponentBars<CONFIG extends ComponentBars<?>> extends Components<ComponentBar, CONFIG> {

	protected ComponentBars(final ComponentBar component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(ComponentBarElement element) {
		get().addElement(element);
		return (CONFIG) this;
	}

}
