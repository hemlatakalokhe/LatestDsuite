package de.bonprix.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author Ivan Slavchev
 */
@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StringPropertyFilter implements Serializable, PropertyFilter<String, StringFilterOperation> {

	@JsonUnwrapped
	private PropertyDescriptor property;

	private StringFilterOperation operation;

	private String value;

	public static StringPropertyFilter of(final PropertyDescriptor property, final StringFilterOperation operation, final String value) {
		final StringPropertyFilter filter = new StringPropertyFilter();
		filter.setProperty(property);
		filter.setOperation(operation);
		filter.setValue(value);
		return filter;
	}

	@Override
	public PropertyDescriptor getProperty() {
		return this.property;
	}

	public void setProperty(final PropertyDescriptor property) {
		this.property = property;
	}

	@Override
	public StringFilterOperation getOperation() {
		return this.operation;
	}

	@Override
	public void setOperation(final StringFilterOperation operation) {
		this.operation = operation;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final StringPropertyFilter that = (StringPropertyFilter) o;
		return Objects.equals(getProperty(), that.getProperty()) && getOperation() == that.getOperation() && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProperty(), getOperation(), getValue());
	}
}
