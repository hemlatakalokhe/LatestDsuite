package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.Button;

import de.bonprix.I18N;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class Buttons<CONFIG extends Buttons<?>> extends Components<Button, CONFIG> {

	protected Buttons(final Button component) {
		super(component);
	}

	/**
	 * Call the method to apply all default configurations for a Vaadin
	 * {@link Button}.
	 */
	@Override
	public CONFIG defaults() {
		return super.defaults();
	}

	@SuppressWarnings("unchecked")
	public CONFIG htmlContentAllowed(final boolean allowed) {
		get().setHtmlContentAllowed(allowed);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG descriptionKey(final String descriptionKey, final Object... objects) {
		get().setDescription(descriptionKey == null || "".equals(descriptionKey) ? descriptionKey
				: I18N.get(descriptionKey, objects));
		return (CONFIG) this;
	}

	public CONFIG htmlContentAllowed() {
		return htmlContentAllowed(true);
	}

	@SuppressWarnings("unchecked")
	public CONFIG onClick(final Button.ClickListener listener) {
		get().addClickListener(listener);
		return (CONFIG) this;
	}

}
