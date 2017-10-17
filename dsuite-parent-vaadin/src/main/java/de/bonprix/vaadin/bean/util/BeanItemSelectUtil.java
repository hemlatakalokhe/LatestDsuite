package de.bonprix.vaadin.bean.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import de.bonprix.exception.CustomNoSuchMethodException;
import de.bonprix.vaadin.bean.util.BeanItemSelect.SelectionChangeEvent;
import de.bonprix.vaadin.bean.util.BeanItemSelect.SelectionChangeListener;

/**
 * Util class for implementations of {@link BeanItemSelect} for getting
 * selectedItem(s) and selectionChanges.
 */
public final class BeanItemSelectUtil {

	public static final Method SELECTION_CHANGE_METHOD;

	static {
		try {
			SELECTION_CHANGE_METHOD = SelectionChangeListener.class
				.getDeclaredMethod("selectionChange", new Class[] { SelectionChangeEvent.class });
		} catch (final NoSuchMethodException e) {
			// This should never happen
			throw new CustomNoSuchMethodException("Internal error finding methods in AbstractField", e);
		}
	}

	private BeanItemSelectUtil() {
	}

	/**
	 * Returns the currently selected item in this table. If no item is
	 * selected, the return value is <code>null</code>. In case the table is in
	 * multi-select-mode, only the first item of the underlying collection will
	 * be returned.
	 *
	 * @return the selected item
	 */
	@SuppressWarnings("unchecked")
	public static <BEANTYPE> BEANTYPE getSelectedItem(final BeanItemSelect<BEANTYPE> select) {
		final Object value = select.getValue();

		if (value == null) {
			return null;
		}

		if (Collection.class.isAssignableFrom(value.getClass())) {
			final Collection<BEANTYPE> selection = (Collection<BEANTYPE>) value;

			if (selection.isEmpty()) {
				return null;
			} else {
				return selection.iterator()
					.next(); // return only first one
			}
		} else {
			return (BEANTYPE) value;
		}
	}

	/**
	 * Returns the currently selected items. If no item is selected, an empty
	 * list will be returned.
	 *
	 * @return the selected items
	 */
	@SuppressWarnings("unchecked")
	public static <BEANTYPE> Collection<BEANTYPE> getSelectedItems(final BeanItemSelect<BEANTYPE> select) {
		final Object value = select.getValue();

		if (value == null) {
			return Collections.emptyList();
		}

		if (Collection.class.isAssignableFrom(value.getClass())) {
			final Collection<BEANTYPE> selection = (Collection<BEANTYPE>) value;

			if (selection.isEmpty()) {
				return Collections.emptyList();
			} else {
				return selection;
			}
		} else {
			return Arrays.asList(((BEANTYPE) value));
		}
	}

}
