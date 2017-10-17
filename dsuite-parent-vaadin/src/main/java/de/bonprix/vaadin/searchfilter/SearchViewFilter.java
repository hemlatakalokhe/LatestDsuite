package de.bonprix.vaadin.searchfilter;

public interface SearchViewFilter {

	/**
	 * Returns a component which contains the filter elements which are always
	 * visible to the user.
	 *
	 * @param clazz
	 *            the type the returned object should be an instance of.
	 * @return the primary filter component
	 */
	<T> T getPrimaryFilterElements(final Class<T> clazz);

	/**
	 * Returns a component which contains the filter elements which are only
	 * visible to the user, if he clicks the extend button of the search filter.
	 *
	 * @param clazz
	 *            the type the returned object should be an instance of.
	 * @return the secondary filter component
	 */
	<T> T getSecondaryFilterElements(final Class<T> clazz);

	/**
	 * control if the secondary filter is expanded during initial screen
	 * "drawing"
	 * 
	 * @return if the secondary filter is expanded at start
	 */
	boolean isSecondaryFilterInitiallyExpanded();

	/**
	 * Adds a {@link SubmitListener} to the search filter. This listener gets
	 * called, when the search filter gets submitted.
	 *
	 * @param submitListener
	 */
	void addSubmitListener(final SubmitListener submitListener);

	/**
	 * Removes the given {@link SubmitListener} from the filter.
	 *
	 * @param submitListener
	 */
	void removeSubmitListener(final SubmitListener submitListener);

	/**
	 * Returns if filter collapses on commit
	 * 
	 * @return is collapse on commit
	 */
	boolean isCollapseOnSubmit();

	/**
	 * Returns if filter expands on commit
	 * 
	 * @return is expand on commit
	 */
	boolean isExpandOnSubmit();

	/**
	 * A listener for the subit action of a search filter.
	 *
	 * @author cthiel
	 *
	 */
	public static interface SubmitListener<BEANTYPE> {
		/**
		 * Gets called every time the submit button of the search filter gets
		 * clicked.
		 */
		void onSubmit(BEANTYPE bean);
	}
}
