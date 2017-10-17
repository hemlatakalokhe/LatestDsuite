package de.bonprix.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Ivan Slavchev
 */

public class PropertyDescriptor {

	private String propertyName;

	@JsonCreator
	public static PropertyDescriptor of(final String propertyName) {
		final PropertyDescriptor propertyDescriptor = new PropertyDescriptor();
		propertyDescriptor.setPropertyName(propertyName);
		return propertyDescriptor;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final PropertyDescriptor that = (PropertyDescriptor) o;
		return Objects.equals(getPropertyName(), that.getPropertyName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPropertyName());
	}
}
