package de.bonprix.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ivan Slavchev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JunctionFilter implements Serializable, Filter<Set<Filter>, JunctionFilterOperation> {

	private JunctionFilterOperation operation;

	private Set<Filter> value = new HashSet<>();

	public static JunctionFilter of(final JunctionFilterOperation operation) {
		final JunctionFilter filter = new JunctionFilter();
		filter.setOperation(operation);
		return filter;
	}

	public void add(final Filter filer) {
		this.value.add(filer);
	}

	public static JunctionFilter andOf(final Filter... filters) {

		final JunctionFilter and = JunctionFilter.of(JunctionFilterOperation.AND);
		and.getValue()
		   .addAll(Arrays.asList(filters));
		return and;
	}

	public static JunctionFilter orOf(final Filter... filters) {

		final JunctionFilter or = JunctionFilter.of(JunctionFilterOperation.OR);
		or.getValue()
		  .addAll(Arrays.asList(filters));
		return or;
	}

	@Override
	public JunctionFilterOperation getOperation() {
		return this.operation;
	}

	@Override
	public void setOperation(final JunctionFilterOperation operation) {
		this.operation = operation;
	}

	@Override
	public Set<Filter> getValue() {
		return this.value;
	}

	@Override
	public void setValue(final Set<Filter> value) {
		this.value = value;
	}
}
