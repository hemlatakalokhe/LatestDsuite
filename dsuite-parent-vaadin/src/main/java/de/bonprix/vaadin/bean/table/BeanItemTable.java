/**
 *
 */
package de.bonprix.vaadin.bean.table;

import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.ui.Table;

import de.bonprix.I18N;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;
import de.bonprix.vaadin.bean.table.columngenerator.BeanItemColumnGenerator;
import de.bonprix.vaadin.bean.util.BeanItemSelect;
import de.bonprix.vaadin.bean.util.BeanItemSelectUtil;

/**
 * The {@link BeanItemTable} is an simple table to display bean types in a
 * table. It also provides some additional utilities to easily retrieve selected
 * items or selection event listeners.
 *
 * @author cthiel
 *
 */
public class BeanItemTable<BEANTYPE> extends Table implements BeanItemSelect<BEANTYPE> {
	private static final long serialVersionUID = -1215095679769824462L;

	private String captionKey;

	/**
	 * Creates the bean item table with the default item container
	 * {@link BeanItemContainer}.<br/>
	 * <br/>
	 * By default the table is in selected mode, but not in multiselect.
	 *
	 * @param type
	 *            the type of beans that will be added to the container
	 */
	public BeanItemTable(final Class<? super BEANTYPE> type) {
		final BeanItemContainer<BEANTYPE> container = new BeanItemContainer<BEANTYPE>(type);

		setSelectable(true);
		setMultiSelect(false);
		setImmediate(true);

		setContainerDataSource(container);
		addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 9033668986615451342L;

			@Override
			public void valueChange(final Property.ValueChangeEvent event) {
				fireEvent(new SelectionChangeEvent<BEANTYPE>(BeanItemTable.this, getSelectedItems()));
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanItemContainer<BEANTYPE> getContainerDataSource() {
		return (BeanItemContainer<BEANTYPE>) super.getContainerDataSource();
	}

	/**
	 * Sets the Container that serves as the data source of the viewer. As a
	 * side-effect the table's selection value is set to null as the old
	 * selection might not exist in new Container.<br>
	 * <br>
	 * All rows and columns are generated as visible using this method. If the
	 * new container contains properties that are not meant to be shown you
	 * should use {@link Table#setContainerDataSource(Container, Collection)}
	 * instead, especially if the table is editable.
	 *
	 * @param container
	 *            the new data source.
	 */
	public void setContainerDataSource(final BeanItemContainer<BEANTYPE> container) {
		super.setContainerDataSource(container);
	}

	/**
	 * Adds a nested container property for the container, e.g.
	 * "manager.address.street".
	 *
	 * All intermediate getters must exist and must return non-null values when
	 * the property value is accessed.
	 *
	 * @see NestedMethodProperty
	 *
	 * @param propertyId
	 * @return true if the property was added
	 */
	public boolean addNestedContainerProperty(final String propertyId) {
		return getContainerDataSource().addNestedContainerProperty(propertyId);
	}

	/**
	 * Sets the component's caption <code>String</code> with an {@link I18N}
	 * captionKey. Caption is the visible name of the component. This method
	 * will trigger a repaint.<br/>
	 * <br/>
	 * If the captionKey is null or empty, this value will be set to the caption
	 * directly (Note: There is a difference between a <code>null</code> caption
	 * and an empty caption string.
	 *
	 * @param captionKey
	 *            the new captionKey for the component.
	 */
	public void setCaptionKey(final String captionKey) {
		this.captionKey = captionKey;

		if (captionKey == null || captionKey.equals("")) {
			super.setCaption(captionKey);
		} else {
			super.setCaption(I18N.get(captionKey));
		}
	}

	/**
	 * Returns the current caption key.
	 * 
	 * @return the caption key
	 */
	public String getCaptionKey() {
		return this.captionKey;
	}

	/**
	 * Sets the I18N header keys of the columns.
	 *
	 * <p>
	 * The headers match the property id:s given my the set visible column
	 * headers. In the defaults mode any nulls in the headers array are replaced
	 * with id.toString() outputs when rendering.
	 * </p>
	 *
	 * @param columnHeaderKeys
	 *            the Array of column headers that match the
	 *            {@link #getVisibleColumns()} method.
	 */
	public void setColumnHeaderKeys(final String... columnHeaderKeys) {
		final String[] headers = new String[columnHeaderKeys.length];

		for (int i = 0; i < columnHeaderKeys.length; i++) {
			headers[i] = columnHeaderKeys[i] == null || columnHeaderKeys[i].equals("") ? ""
					: I18N.get(columnHeaderKeys[i]);
		}

		super.setColumnHeaders(headers);
	}

	/**
	 * Adds a generated column to this bean item table. This column generator
	 * behaves exactly like the usual ColumnGenerator but uses the BEANTYPE of
	 * the beanItemTable to cast the itemId directly to the given type.
	 * 
	 * @param propertyId
	 *            the property id
	 * @param columnGenerator
	 *            the column generator
	 */
	public void addGeneratedBeanColumn(final String propertyId,
			final BeanItemColumnGenerator<BEANTYPE> columnGenerator) {
		addGeneratedColumn(propertyId, columnGenerator);
	}

	@Override
	public void addBean(final BEANTYPE bean) {
		getContainerDataSource().addBean(bean);
		sort();
	}

	@Override
	public void addAllBeans(final Collection<BEANTYPE> beanCollection) {
		getContainerDataSource().addAll(beanCollection);
		sort();
	}

	@Override
	public void replaceAllBeans(final Collection<BEANTYPE> beans) {
		getContainerDataSource().removeAllItems();
		unselectAll();
		addAllBeans(beans);
	}

	@Override
	public void replaceBean(BEANTYPE bean) {
		BEANTYPE itemId = getContainerDataSource()	.getBeanIdResolver()
													.getIdForBean(bean);
		int indexOfOldBean = getContainerDataSource().indexOfId(itemId);
		removeBean(bean);
		getContainerDataSource().addItemAt(indexOfOldBean, bean);
	}

	@Override
	public void replaceBeans(Collection<BEANTYPE> beans) {
		for (BEANTYPE bean : beans) {
			replaceBean(bean);
		}
	}

	@Override
	public List<BEANTYPE> getAllBeans() {
		return getContainerDataSource().getItemIds();
	}

	@Override
	public boolean removeBean(final BEANTYPE bean) {
		unselect(bean);
		return getContainerDataSource().removeItem(bean);
	}

	@Override
	public void addSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener) {
		addListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void removeSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener) {
		removeListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void selectAll() {
		if (isMultiSelect()) {
			setValue(getItemIds());
		}
	}

	@Override
	public void unselectAll() {
		setValue(null);
	}

	@Override
	public BEANTYPE getSelectedItem() {
		return BeanItemSelectUtil.getSelectedItem(this);
	}

	@Override
	public Collection<BEANTYPE> getSelectedItems() {
		return BeanItemSelectUtil.getSelectedItems(this);
	}

	@Override
	public void setItemCaptionGenerator(ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		// TODO
	}

	@Override
	public void setItemIconGenerator(ItemIconGenerator<BEANTYPE> itemIconGenerator) {
		// TODO
	}
}
