package de.bonprix.vaadin.data.filter;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

/**
 * Custom bean filter for matching any text filter that you can think of
 * regarding the bean of the current row of the grid.
 */
@SuppressWarnings("serial")
public final class CustomBeanFilter<FILTERTYPE, BEANTYPE> implements Filter {

	protected final FILTERTYPE filter;
	protected final PassesFilter<FILTERTYPE, BEANTYPE> passesFilter;
	protected final AppliesToProperty appliesToProperty;

	public CustomBeanFilter(FILTERTYPE filter, PassesFilter<FILTERTYPE, BEANTYPE> passesFilter) {
		this(filter, passesFilter, null);
	}

	public CustomBeanFilter(FILTERTYPE filter, PassesFilter<FILTERTYPE, BEANTYPE> passesFilter,
			AppliesToProperty appliesToProperty) {
		this.filter = filter;
		this.passesFilter = passesFilter;
		this.appliesToProperty = appliesToProperty;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean passesFilter(Object itemId, Item item) {
		return this.passesFilter.passesFilter(this.filter, (BEANTYPE) itemId);
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		if (this.appliesToProperty == null) {
			return true;
		}

		return this.appliesToProperty.appliesToProperty(propertyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		// Only ones of the objects of the same class can be equal
		if (!(obj instanceof CustomBeanFilter)) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		final CustomBeanFilter o = (CustomBeanFilter) obj;

		// Checks the properties one by one
		if (!this.passesFilter.equals(o.passesFilter)) {
			return false;
		}
		if (!this.appliesToProperty.equals(o.appliesToProperty)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (this.passesFilter != null ? this.passesFilter.hashCode() : 0)
				^ (this.appliesToProperty != null ? this.appliesToProperty.hashCode() : 0);
	}

}