package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

public class Panels<PANEL extends Panel, CONFIG extends Panels<PANEL, ?>> extends Components<PANEL, CONFIG> {

	protected Panels(final PANEL component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG content(final Component component) {
		get().setContent(component);

		return (CONFIG) this;
	}
}
