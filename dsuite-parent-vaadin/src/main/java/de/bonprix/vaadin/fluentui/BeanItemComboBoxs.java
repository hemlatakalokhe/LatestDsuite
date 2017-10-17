package de.bonprix.vaadin.fluentui;

import com.vaadin.shared.ui.combobox.FilteringMode;

import de.bonprix.I18N;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;

/**
 * Provides a fluent API to configure a bonprix {@link BeanItemComboBox}
 * component, including all configuration possibilities that the
 * {@link BeanItemSelects}, {@link AbstractSelects}, {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class BeanItemComboBoxs<CONFIG extends BeanItemComboBoxs<CONFIG, BEANTYPE>, BEANTYPE>
		extends BeanItemSelects<BeanItemComboBox<BEANTYPE>, BEANTYPE, CONFIG> {

	protected BeanItemComboBoxs(final BeanItemComboBox<BEANTYPE> beanItemComboBox) {
		super(beanItemComboBox);
	}

	@SuppressWarnings("unchecked")
	public CONFIG inputPromptKey(String inputPromptKey, Object... objects) {
		get().setInputPrompt(I18N.get(inputPromptKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG filteringMode(FilteringMode filteringMode) {
		get().setFilteringMode(filteringMode);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG nullSelectionAllowed(boolean nullSelectionAllowed) {
		get().setNullSelectionAllowed(nullSelectionAllowed);
		return (CONFIG) this;
	}

}
