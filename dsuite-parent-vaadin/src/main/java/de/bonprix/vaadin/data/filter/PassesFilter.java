package de.bonprix.vaadin.data.filter;

/**
 * Check if an item passes the filter
 * 
 * @author thacht
 *
 * @param <BEANTYPE>
 *            beantype of the grid
 */
public interface PassesFilter<FILTERTYPE, BEANTYPE> {

	/**
	 * Check if an item passes the filter (in-memory filtering).
	 *
	 * @param filter
	 *            entered value of filter component
	 * @param bean
	 *            current bean of the row to be filtered
	 * @return true if the item is accepted by this filter
	 * @throws UnsupportedOperationException
	 *             if the filter cannot be used for in-memory filtering
	 */
	boolean passesFilter(FILTERTYPE filter, BEANTYPE bean);

}
