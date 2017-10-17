package de.bonprix.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author Ivan Slavchev
 */
@GeneratePojoBuilder(intoPackage = "*.builder")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplexFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	private JunctionFilter baseJunctionFilter;

	public void addStringFilter(final PropertyDescriptor property, final StringFilterOperation operation, final String value) {
		if (this.baseJunctionFilter == null) {
			this.baseJunctionFilter = JunctionFilter.of(JunctionFilterOperation.AND);
		}
		this.baseJunctionFilter.add(StringPropertyFilter.of(property, operation, value));
	}

	public void addNumberFilter(final PropertyDescriptor property, final NumberFilterOperation operation, final Long value) {
		if (this.baseJunctionFilter == null) {
			this.baseJunctionFilter = JunctionFilter.of(JunctionFilterOperation.AND);
		}
		this.baseJunctionFilter.add(NumberPropertyFilter.of(property, operation, value));
	}

	public void addCollectionFilter(final PropertyDescriptor property, final CollectionFilterOperation operation, final Collection<Long> value) {
		if (this.baseJunctionFilter == null) {
			this.baseJunctionFilter = JunctionFilter.of(JunctionFilterOperation.AND);
		}
		this.baseJunctionFilter.add(CollectionPropertyFilter.of(property, operation, value));
	}

	public void addDateFilter(final PropertyDescriptor property, final DateFilterOperation operation, final ZonedDateTime value) {
		if (this.baseJunctionFilter == null) {
			this.baseJunctionFilter = JunctionFilter.of(JunctionFilterOperation.AND);
		}
		this.baseJunctionFilter.add(DatePropertyFilter.of(property, operation, value));
	}

	public JunctionFilter getBaseJunctionFilter() {
		return this.baseJunctionFilter;
	}

	public void setBaseJunctionFilter(final JunctionFilter baseJunctionFilter) {
		this.baseJunctionFilter = baseJunctionFilter;
	}

}
