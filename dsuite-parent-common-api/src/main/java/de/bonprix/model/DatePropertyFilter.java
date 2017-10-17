package de.bonprix.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author Ivan Slavchev
 */
@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatePropertyFilter implements Serializable, PropertyFilter<ZonedDateTime, DateFilterOperation> {

	@JsonUnwrapped
	private PropertyDescriptor property;

	private DateFilterOperation operation;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	private ZonedDateTime value;

	public static DatePropertyFilter of(final PropertyDescriptor property, final DateFilterOperation operation, final ZonedDateTime value) {
		final DatePropertyFilter filter = new DatePropertyFilter();
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
	public DateFilterOperation getOperation() {
		return this.operation;
	}

	@Override
	public void setOperation(final DateFilterOperation operation) {
		this.operation = operation;
	}

	@Override
	public ZonedDateTime getValue() {
		return this.value;
	}

	@Override
	public void setValue(final ZonedDateTime value) {
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final DatePropertyFilter that = (DatePropertyFilter) o;
		return Objects.equals(getProperty(), that.getProperty()) && getOperation() == that.getOperation() && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProperty(), getOperation(), getValue());
	}
}
