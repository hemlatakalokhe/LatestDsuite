package de.bonprix.vaadin.data.filter;

/**
 * Interface for checking if the current value of the property can affect the
 * filtering.
 * 
 * @author thacht
 *
 */
public interface AppliesToProperty {

	/**
	 * Check if a change in the value of a property can affect the filtering
	 * result. May always return true, at the cost of performance.
	 *
	 * If the filter cannot determine whether it may depend on the property or
	 * not, should return true.
	 *
	 * @param propertyId
	 * @return true if the filtering result may/does change based on changes to
	 *         the property identified by propertyId
	 */
	boolean appliesToProperty(Object propertyId);

}
