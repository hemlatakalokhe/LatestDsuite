package de.bonprix.vaadin.bean.util;

import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container.Ordered;
import com.vaadin.data.Container.Sortable;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;

import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;

/**
 * Interface used for identical behaviour of vaadin bean extensions.
 *
 * @param <BEANTYPE>
 *            BEANTYPE
 */
public interface BeanItemSelect<BEANTYPE> extends Property<Object> {
	public static interface SelectionChangeListener<BEANTYPE> {

		public void selectionChange(final SelectionChangeEvent<BEANTYPE> event);
	}

	@SuppressWarnings("serial")
	public static class SelectionChangeEvent<BEANTYPE> extends Component.Event {

		private final Collection<BEANTYPE> selection;

		/**
		 * Constructs a new event object with the specified source field object.
		 *
		 * @param source
		 *            the field that caused the event.
		 */
		public SelectionChangeEvent(final Component source, final Collection<BEANTYPE> selection) {
			super(source);
			this.selection = selection;
		}

		@SuppressWarnings("unchecked")
		@Override
		public BeanItemSelect<BEANTYPE> getSource() {
			return (BeanItemSelect<BEANTYPE>) super.getSource();
		}

		public BEANTYPE getSelectedItem() {
			return this.selection.isEmpty() ? null : this.selection	.iterator()
																	.next();
		}

		public Collection<BEANTYPE> getSelectedItems() {
			return this.selection;
		}
	}

	/**
	 * Adds the bean to the container. If the bean is already in the container,
	 * nothing happens.
	 * 
	 * @param bean
	 */
	void addBean(final BEANTYPE bean);

	/**
	 * Adds all items to the listselect. If an item already exists in the
	 * backing container, the new item will be ignored.
	 *
	 * If the BEANTYPE is implementing the {@link NamedObject} interface, the
	 * container will be sorted by name in post process.
	 *
	 * @param beans
	 *            the beans to add.
	 */
	void addAllBeans(final Collection<BEANTYPE> beans);

	/**
	 * Replaces all items to the listselect. Removes all existing items in the
	 * backing container first.
	 *
	 * If the BEANTYPE is implementing the {@link NamedObject} interface, the
	 * container will be sorted by name in post process.
	 *
	 * @param beans
	 *            the beans to add.
	 */
	void replaceAllBeans(final Collection<BEANTYPE> beans);

	/**
	 * Replaces only the given bean.
	 * 
	 * @param bean
	 *            the bean to replace.
	 */
	void replaceBean(BEANTYPE bean);

	/**
	 * Replaces only the given beans.
	 * 
	 * @param beans
	 *            the beans to replace.
	 */
	void replaceBeans(Collection<BEANTYPE> beans);

	/**
	 * Gets the ID's of all visible (after filtering and sorting) Items stored
	 * in the Container. The ID's cannot be modified through the returned
	 * collection.
	 * <p>
	 * If the container is {@link Ordered}, the collection returned by this
	 * method should follow that order. If the container is {@link Sortable},
	 * the items should be in the sorted order.
	 * <p>
	 * Calling this method for large lazy containers can be an expensive
	 * operation and should be avoided when practical.
	 *
	 * @return unmodifiable collection of Item IDs
	 */
	List<BEANTYPE> getAllBeans();

	/**
	 * Removes the bean from this container if existent.
	 * 
	 * @param bean
	 *            the bean to remove
	 * @return true if a bean has been removed
	 */
	boolean removeBean(final BEANTYPE bean);

	/**
	 * Selects all items in this table.
	 */
	void selectAll();

	/**
	 * Unselects all items in this table.
	 */
	void unselectAll();

	/**
	 * Returns the currently selected item in this table. If no item is
	 * selected, the return value is <code>null</code>. In case the table is in
	 * multi-select-mode, only the first item of the underlying collection will
	 * be returned.
	 *
	 * @return the selected item
	 */
	BEANTYPE getSelectedItem();

	/**
	 * Returns the currently selected items. If no item is selected, an empty
	 * list will be returned.
	 *
	 * @return the selected items
	 */
	Collection<BEANTYPE> getSelectedItems();

	/**
	 * Sorts the container items.
	 * <p>
	 * Sorting a container can irreversibly change the order of its items or
	 * only change the order temporarily, depending on the container.
	 *
	 * @param propertyId
	 *            Array of container property IDs, whose values are used to sort
	 *            the items in container as primary, secondary, ... sorting
	 *            criterion. All of the item IDs must be in the collection
	 *            returned by {@link Sortable#getSortableContainerPropertyIds()}
	 * @param ascending
	 *            Array of sorting order flags corresponding to each property ID
	 *            used in sorting. If this array is shorter than propertyId
	 *            array, ascending order is assumed for items where the order is
	 *            not specified. Use <code>true</code> to sort in ascending
	 *            order, <code>false</code> to use descending order.
	 */
	void sort(final Object[] propertyId, final boolean[] ascending);

	/**
	 * Registers a listener to the select, which fires every time, the item
	 * selection changes
	 *
	 * @param listener
	 */
	void addSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener);

	/**
	 * Removes a previously registered listener.
	 *
	 * @param listener
	 */
	void removeSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener);

	/**
	 * implements a custom generator of caption
	 * 
	 * @param itemCaptionGenerator
	 */
	void setItemCaptionGenerator(ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator);

	/**
	 * implements a custom generator of icon
	 * 
	 * @param itemIconGenerator
	 */
	void setItemIconGenerator(ItemIconGenerator<BEANTYPE> itemIconGenerator);
}
