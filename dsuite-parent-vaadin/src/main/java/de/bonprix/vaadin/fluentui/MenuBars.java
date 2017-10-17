package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class MenuBars<CONFIG extends MenuBars<?>> extends Components<MenuBar, CONFIG> {

	protected MenuBars(final MenuBar component) {
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
	public CONFIG description(final String description) {
		get().setDescription(description);
		return (CONFIG) this;
	}

	public CONFIG autoOpen() {
		return autoOpen(true);
	}

	@SuppressWarnings("unchecked")
	public CONFIG autoOpen(final boolean bool) {
		get().setAutoOpen(bool);
		return (CONFIG) this;
	}
}
