package de.bonprix.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jdk.nashorn.internal.objects.DataPropertyDescriptor;

/**
 * @author Ivan Slavchev
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,
		include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = DataPropertyDescriptor.class), @JsonSubTypes.Type(value = CollectionPropertyFilter.class),
		@JsonSubTypes.Type(value = StringPropertyFilter.class), @JsonSubTypes.Type(value = NumberPropertyFilter.class) })
public interface Filter<T, O extends Operation> {

	O getOperation();

	void setOperation(final O operation);

	T getValue();

	void setValue(final T value);

	default JunctionFilter and(final Filter filter) {
		return JunctionFilter.andOf(this, filter);
	}

	default JunctionFilter or(final Filter filter) {
		return JunctionFilter.orOf(this, filter);
	}

}
