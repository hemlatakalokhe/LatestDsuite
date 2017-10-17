package de.bonprix.vaadin.data.form;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.RichTextArea;

/**
 * This field factory extends the default vaadin field factory with additional
 * field types like the date field.
 * 
 * @author cthiel
 * 
 */
public class EnhancedFieldGroupFieldFactory implements FieldGroupFieldFactory {
	private static final long serialVersionUID = -7791858379132991879L;

	private final FieldGroupFieldFactory fieldFactory = DefaultFieldGroupFieldFactory.get();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends Field> T createField(final Class<?> dataType, final Class<T> fieldType) {

		T field = this.fieldFactory.createField(dataType, fieldType);

		/*
		 * By default all text fields would show a "null" string in the field,
		 * when the value is null. Replace it with an empty string..
		 */
		if (field instanceof AbstractTextField) {
			((AbstractTextField) field).setNullRepresentation("");
		}
		if (field instanceof RichTextArea) {
			((RichTextArea) field).setNullRepresentation("");
		}

		return field;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends Field> T createDateField() {
		final DateField field = new DateField();
		field.setImmediate(true);
		field.setShowISOWeekNumbers(true);

		return (T) field;
	}
}
