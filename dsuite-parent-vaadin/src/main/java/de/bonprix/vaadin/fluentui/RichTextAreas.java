package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextField;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class RichTextAreas<CONFIG extends RichTextAreas<CONFIG>> extends AbstractFields<RichTextArea, String, CONFIG> {

	protected RichTextAreas(final RichTextArea richTextArea) {
		super(richTextArea);
	}

	/**
	 * Call this method to apply all default configurations for a Vaadin
	 * {@link TextField}. This includes<br>
	 * <br>
	 * <ul>
	 * <li>Empty string as {@code null} representation</li>
	 * </ul>
	 */
	@Override
	public CONFIG defaults() {
		return (CONFIG) super.defaults().nullRepresentation("");
	}

	@SuppressWarnings("unchecked")
	public CONFIG nullRepresentation(final String representation) {
		get().setNullRepresentation(representation);
		return (CONFIG) this;
	}

}
