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
public class NumberPropertyFilter implements Serializable, PropertyFilter<Long, NumberFilterOperation> {

	@JsonUnwrapped
	private PropertyDescriptor property;

	private NumberFilterOperation operation;

	private Long value;

	public static NumberPropertyFilter of(final PropertyDescriptor property, final NumberFilterOperation operation, final Long value) {
		final NumberPropertyFilter filter = new NumberPropertyFilter();
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
	public NumberFilterOperation getOperation() {
		return this.operation;
	}

	@Override
	public void setOperation(final NumberFilterOperation operation) {
		this.operation = operation;
	}

	@Override
	public Long getValue() {
		return this.value;
	}

	@Override
	public void setValue(final Long value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final NumberPropertyFilter that = (NumberPropertyFilter) o;
		return Objects.equals(getProperty(), that.getProperty()) && getOperation() == that.getOperation() && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProperty(), getOperation(), getValue());
	}
}
