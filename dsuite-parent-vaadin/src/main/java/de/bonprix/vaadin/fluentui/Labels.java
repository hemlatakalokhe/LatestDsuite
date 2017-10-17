package de.bonprix.vaadin.fluentui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

import de.bonprix.I18N;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class Labels<CONFIG extends Labels<?>> extends AbstractComponents<Label, CONFIG> {

	protected Labels(final Label component) {
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
	public CONFIG valueKey(final String valueKey, Object... objects) {
		get().setValue(I18N.get(valueKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG value(final String value) {
		get().setValue(value);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG contentMode(final ContentMode contentMode) {
		get().setContentMode(contentMode);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG descriptionKey(final String descriptionKey, Object... objects) {
		get().setDescription(I18N.get(descriptionKey, objects));
		return (CONFIG) this;
	}
}
