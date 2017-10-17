package de.bonprix.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author Ivan Slavchev
 */
@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionPropertyFilter implements Serializable, PropertyFilter<Collection<Long>, CollectionFilterOperation> {

	@JsonUnwrapped
	private PropertyDescriptor property;

	private CollectionFilterOperation operation;

	private Collection<Long> value;

	public static CollectionPropertyFilter of(final PropertyDescriptor property, final CollectionFilterOperation operation, final Collection<Long> value) {
		final CollectionPropertyFilter filter = new CollectionPropertyFilter();
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
	public CollectionFilterOperation getOperation() {
		return this.operation;
	}

	@Override
	public void setOperation(final CollectionFilterOperation operation) {
		this.operation = operation;
	}

	@Override
	public Collection<Long> getValue() {
		return this.value;
	}

	@Override
	public void setValue(final Collection<Long> value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final CollectionPropertyFilter that = (CollectionPropertyFilter) o;
		return Objects.equals(getProperty(), that.getProperty()) && getOperation() == that.getOperation() && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProperty(), getOperation(), getValue());
	}
}
