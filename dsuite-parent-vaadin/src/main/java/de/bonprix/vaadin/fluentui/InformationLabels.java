package de.bonprix.vaadin.fluentui;

import com.vaadin.shared.ui.label.ContentMode;

import de.bonprix.vaadin.fluentui.Layouts.AbstractOrderedLayouts;
import de.bonprix.vaadin.information.InformationLabel;
import de.bonprix.vaadin.information.InformationLabel.Type;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class InformationLabels<CONFIG extends InformationLabels<CONFIG>>
		extends AbstractOrderedLayouts<InformationLabel, CONFIG> {

	protected InformationLabels(final InformationLabel component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG messageKey(String messageKey, Object... objects) {
		get().setMessageKey(messageKey, objects);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG type(Type type) {
		get().setType(type);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG contentMode(ContentMode contentMode) {
		get().setContentMode(contentMode);
		return (CONFIG) this;
	}

}
