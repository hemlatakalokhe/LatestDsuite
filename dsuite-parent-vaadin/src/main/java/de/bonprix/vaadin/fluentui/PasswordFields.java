package de.bonprix.vaadin.fluentui;

import com.vaadin.ui.PasswordField;

import de.bonprix.I18N;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public class PasswordFields<CONFIG extends PasswordFields<CONFIG>>
		extends AbstractFields<PasswordField, String, CONFIG> {

	protected PasswordFields(final PasswordField passwordField) {
		super(passwordField);
	}

	@SuppressWarnings("unchecked")
	public CONFIG inputPromptKey(String inputPromptKey, Object... objects) {
		get().setInputPrompt(I18N.get(inputPromptKey, objects));
		return (CONFIG) this;
	}

}
