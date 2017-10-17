package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class AbstractTextFields<FIELD extends AbstractTextField, CONFIG extends AbstractTextFields<FIELD, CONFIG>>
		extends AbstractFields<FIELD, String, CONFIG> {

	protected AbstractTextFields(final FIELD component) {
		super(component);
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

	@SuppressWarnings("unchecked")
	public CONFIG inputPromptKey(String inputPromptKey, Object... objects) {
		get().setInputPrompt(I18N.get(inputPromptKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG upperCase() {
		get().addStyleName(DSuiteTheme.TEXTFIELD_UPPERCASE);
		return (CONFIG) this;
	}

}
