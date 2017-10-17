package de.bonprix.vaadin.fluentui;

import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.TabSheet.Tab;
import de.bonprix.I18N;

/**
 * Provides a fluent API to configure a Vaadin {@link Component}.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class Tabs<CONFIG extends Tabs<CONFIG>> {

	private final Tab tab;

	protected Tabs(final Tab tab) {
		this.tab = tab;
	}

	/**
	 * Call this method to apply all default configurations for a Vaadin
	 * {@link Component}.
	 */
	@SuppressWarnings("unchecked")
	public CONFIG defaults() {
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG id(final String id) {
		get().setId(id);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG captionKey(final String captionKey, final Object... objects) {
		get().setCaption(captionKey == null || "".equals(captionKey) ? captionKey : I18N.get(captionKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG descriptionKey(final String descriptionKey, final Object... objects) {
		get().setDescription(descriptionKey == null || "".equals(descriptionKey) ? descriptionKey
				: I18N.get(descriptionKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG icon(final Resource resource) {
		get().setIcon(resource);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG enabled(final boolean enabled) {
		get().setEnabled(enabled);
		return (CONFIG) this;
	}

	public CONFIG disabled() {
		return enabled(false);
	}

	@SuppressWarnings("unchecked")
	public CONFIG visible(final boolean visible) {
		get().setVisible(visible);
		return (CONFIG) this;
	}

	public CONFIG invisible() {
		return visible(false);
	}

	@SuppressWarnings("unchecked")
	public CONFIG iconAlternateText(String iconAltTextKey, Object... objects) {
		get().setIconAlternateText(I18N.get(iconAltTextKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG setDefaultFocusComponent(Focusable defaultFocus) {
		get().setDefaultFocusComponent(defaultFocus);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG closable(boolean closable) {
		get().setClosable(closable);
		return (CONFIG) this;
	}

	public CONFIG closable() {
		return closable(true);
	}

	@SuppressWarnings("unchecked")
	public CONFIG componentError(ErrorMessage componentError) {
		get().setComponentError(componentError);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG style(final String style) {
		get().setStyleName(style);
		return (CONFIG) this;
	}

	public Tab get() {
		return this.tab;
	}
}
