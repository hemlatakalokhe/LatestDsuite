package de.bonprix.vaadin.fluentui;

import org.vaadin.addons.comboboxmultiselect.ComboBoxMultiselect.ShowButton;

import com.vaadin.shared.ui.combobox.FilteringMode;

import de.bonprix.I18N;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;

/**
 * Provides a fluent API to configure a bonprix
 * {@link BeanItemComboBoxMultiselect} component, including all configuration
 * possibilities that the {@link BeanItemSelects}, {@link AbstractSelects},
 * {@link Components} and {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class BeanItemComboBoxMultiselects<CONFIG extends BeanItemComboBoxMultiselects<CONFIG, BEANTYPE>, BEANTYPE>
		extends BeanItemSelects<BeanItemComboBoxMultiselect<BEANTYPE>, BEANTYPE, CONFIG> {

	protected BeanItemComboBoxMultiselects(final BeanItemComboBoxMultiselect<BEANTYPE> beanItemComboBoxMultiselect) {
		super(beanItemComboBoxMultiselect);
	}

	@Override
	public CONFIG defaults() {
		return super.defaults()	.selectAllButtonCaptionKey("SELECT_ALL")
								.clearButtonCaptionKey("CLEAR");
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

	public CONFIG selectAllButton() {
		return selectAllButton(true);
	}

	public CONFIG selectAllButton(boolean selectAllButton) {
		return selectAllButton((filter, page) -> selectAllButton);
	}

	@SuppressWarnings("unchecked")
	public CONFIG selectAllButton(ShowButton showSelectAllButton) {
		get().setShowSelectAllButton(showSelectAllButton);
		return (CONFIG) this;
	}

	public CONFIG selectAllButtonCaptionKey(String selectAllButtonCaptionKey, Object... objects) {
		return selectAllButtonCaption(I18N.get(selectAllButtonCaptionKey, objects));
	}

	@SuppressWarnings("unchecked")
	public CONFIG selectAllButtonCaption(String selectButtonAllCaption) {
		get().setSelectAllButtonCaption(selectButtonAllCaption);
		return (CONFIG) this;
	}

	public CONFIG clearButton() {
		return clearButton(true);
	}

	public CONFIG clearButton(boolean clearButton) {
		return clearButton((filter, page) -> clearButton);
	}

	@SuppressWarnings("unchecked")
	public CONFIG clearButton(ShowButton showClearButton) {
		get().setShowClearButton(showClearButton);
		return (CONFIG) this;
	}

	public CONFIG clearButtonCaptionKey(String clearButtonCaptionKey, Object... objects) {
		return clearButtonCaption(I18N.get(clearButtonCaptionKey, objects));
	}

	@SuppressWarnings("unchecked")
	public CONFIG clearButtonCaption(String clearButtonCaption) {
		get().setClearButtonCaption(clearButtonCaption);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG selectAll(String clearCaption) {
		get().setClearButtonCaption(clearCaption);
		return (CONFIG) this;
	}

}
