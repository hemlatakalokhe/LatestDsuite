package de.bonprix.vaadin.fluentui;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;

/**
 * Provides a fluent API to configure a Vaadin {@link TextField} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class AbstractFields<FIELD extends AbstractField<T>, T, CONFIG extends AbstractFields<FIELD, T, CONFIG>>
		extends AbstractComponents<FIELD, CONFIG> {

	protected AbstractFields(final FIELD component) {
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
		return (CONFIG) super.defaults();
	}

	@SuppressWarnings("unchecked")
	public CONFIG descriptionKey(String descriptionKey, Object... objects) {
		get().setDescription(I18N.get(descriptionKey, objects));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG tabIndex(final int tabIndex) {
		get().setTabIndex(tabIndex);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG required(final boolean required) {
		get().setRequired(required);
		return (CONFIG) this;
	}

	public CONFIG required() {
		return required(true);
	}

	@SuppressWarnings("unchecked")
	public CONFIG bind(final FieldGroup fieldGroup, final Object propertyId) {
		fieldGroup.bind(get(), propertyId);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG dataSource(final Property<T> dataSource) {
		get().setPropertyDataSource(dataSource);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG value(final T value) {
		get().setValue(value);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG validator(final Validator validator) {
		get().addValidator(validator);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG onValueChange(final ValueChangeListener listener) {
		get().addValueChangeListener(listener);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG converter(Converter<T, ?> converter) {
		get().setConverter(converter);
		return (CONFIG) this;
	}

}
