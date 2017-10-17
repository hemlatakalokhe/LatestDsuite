package de.bonprix.model;

/**
 * @author Ivan Slavchev
 */

public interface PropertyFilter<T, O extends Operation> extends Filter<T, O> {

	PropertyDescriptor getProperty();

}
