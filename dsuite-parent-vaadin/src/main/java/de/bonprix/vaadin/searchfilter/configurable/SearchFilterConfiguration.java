package de.bonprix.vaadin.searchfilter.configurable;

import java.util.HashMap;
import java.util.Map;

public class SearchFilterConfiguration<E> {

	private static final int DEFAULT_NUM_COLS = 7;

	private final Map<String, SearchFilterElement> filterElements = new HashMap<>();
	private int numCols = DEFAULT_NUM_COLS;
	private Class<E> beantype = null;
	private SearchFilterLayout defaultLayout;
	private String persistencePropertyKey;
	private boolean configurable = true;

	public SearchFilterConfiguration<E> withBeantype(final Class<E> beantype) {
		this.beantype = beantype;

		return this;
	}

	public SearchFilterConfiguration<E> withElement(final SearchFilterElement element) {
		this.filterElements.put(element.getId(), element);

		return this;
	}

	public SearchFilterConfiguration<E> withNumCols(final int numCols) {
		this.numCols = numCols;

		return this;
	}

	public SearchFilterConfiguration<E> withDefaultLayout(final SearchFilterLayout layout) {
		this.defaultLayout = layout;

		return this;
	}

	public SearchFilterConfiguration<E> withConfigurable(final boolean isConfigurable) {
		this.configurable = isConfigurable;

		return this;
	}

	public SearchFilterConfiguration<E> withPersistencePropertyKey(final String persistencePropertyKey) {
		this.persistencePropertyKey = persistencePropertyKey;

		return this;
	}

	public Map<String, SearchFilterElement> getFilterElements() {
		return this.filterElements;
	}

	public int getNumCols() {
		return this.numCols;
	}

	public Class<E> getBeantype() {
		return this.beantype;
	}

	public SearchFilterLayout getDefaultLayout() {
		return this.defaultLayout;
	}

	public static int getDefaultNumCols() {
		return DEFAULT_NUM_COLS;
	}

	public String getPersistencePropertyKey() {
		return this.persistencePropertyKey;
	}

	public boolean isConfigurable() {
		return this.configurable;
	}

}
