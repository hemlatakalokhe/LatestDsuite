package de.bonprix.vaadin.searchfilter.configurable;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterLayout {

	private List<SearchFilterElementLayout> elements = new ArrayList<SearchFilterElementLayout>();

	private int numCols = 7;

	public SearchFilterLayout() {
	}

	public SearchFilterLayout(final SearchFilterLayout other) {
		this();

		this.numCols = other.numCols;

		for (final SearchFilterElementLayout otherElement : other.elements) {
			this.elements.add(new SearchFilterElementLayout(otherElement));
		}
	}

	public int getNumRows() {
		int maxRow = Integer.MIN_VALUE;
		for (final SearchFilterElementLayout layout : this.elements) {
			if (maxRow < layout.getPosY() + layout.getHeight()) {
				maxRow = layout.getPosY() + layout.getHeight();
			}
		}

		return maxRow;
	}

	public SearchFilterElementLayout getElement(final String id) {
		for (final SearchFilterElementLayout layout : this.elements) {
			if (layout	.getId()
						.equals(id)) {
				return layout;
			}
		}

		return null;
	}

	public List<SearchFilterElementLayout> getElements() {
		return this.elements;
	}

	public void setElements(final List<SearchFilterElementLayout> elements) {
		this.elements = elements;
	}

	public SearchFilterLayout withElementLayout(final SearchFilterElementLayout layout) {
		this.elements.add(layout);

		return this;
	}

	public SearchFilterLayout withNumColumns(final int numColumns) {
		this.numCols = numColumns;

		return this;
	}

	public int getNumCols() {
		return this.numCols;
	}

	public void setNumColumns(final int numCols) {
		this.numCols = numCols;
	}

}
