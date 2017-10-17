/**
 *
 */
package de.bonprix.vaadin.bean.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import de.bonprix.dto.NamedObject;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;
import de.bonprix.vaadin.bean.util.BeanItemSelect;
import de.bonprix.vaadin.bean.util.BeanItemSelectUtil;

/**
 * The {@link BeanItemComboBox} is an extension to the default vaadin
 * {@link ComboBox}. This implementation uses a bean item container as default
 * container and some useful methods to modify the container directly.
 *
 * @author cthiel
 *
 */
public class BeanItemComboBox<BEANTYPE> extends ComboBox implements BeanItemSelect<BEANTYPE> {
	private static final long serialVersionUID = 1L;

	private ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator = null;
	private ItemIconGenerator<BEANTYPE> itemIconGenerator = null;

	private Comparator<BEANTYPE> comparator = null;

	/**
	 *
	 */
	public BeanItemComboBox(final Class<BEANTYPE> clazz) {
		this(clazz, null);
	}

	/**
	 * @param caption
	 */
	public BeanItemComboBox(final Class<BEANTYPE> clazz, final String caption) {
		this(clazz, caption, new BeanItemContainer<BEANTYPE>(clazz));
	}

	/**
	 * @param caption
	 * @param beans
	 */
	public BeanItemComboBox(final Class<BEANTYPE> clazz, final String caption, final Collection<BEANTYPE> beans) {
		this(clazz, caption, new BeanItemContainer<BEANTYPE>(clazz));

		addValueChangeListener(event -> fireEvent(new SelectionChangeEvent<BEANTYPE>(BeanItemComboBox.this,
				getSelectedItems())));
		addAllBeans(beans);
	}

	/**
	 * @param clazz
	 *            the bean type
	 * @param caption
	 * @param container
	 */
	public BeanItemComboBox(final Class<BEANTYPE> clazz, final String caption,
			final BeanItemContainer<BEANTYPE> container) {
		super(caption, container);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanItemContainer<BEANTYPE> getContainerDataSource() {
		return (BeanItemContainer<BEANTYPE>) super.getContainerDataSource();
	}

	@Override
	public void setItemCaptionGenerator(final ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		this.itemCaptionGenerator = itemCaptionGenerator;
	}

	@Override
	public void setItemIconGenerator(final ItemIconGenerator<BEANTYPE> itemIconGenerator) {
		this.itemIconGenerator = itemIconGenerator;
	}

	/**
	 * implements a custom generator of caption
	 * 
	 * @param itemCaptionGenerator
	 * @return itself
	 */
	public BeanItemComboBox<BEANTYPE> withItemCaptionGenerator(
			final ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		this.itemCaptionGenerator = itemCaptionGenerator;
		return this;
	}

	/**
	 * implements a custom generator of icon
	 * 
	 * @param itemIconGenerator
	 * @return itself
	 */
	public BeanItemComboBox<BEANTYPE> withItemIconGenerator(final ItemIconGenerator<BEANTYPE> itemIconGenerator) {
		this.itemIconGenerator = itemIconGenerator;
		return this;
	}

	@Override
	public void addAllBeans(final Collection<BEANTYPE> beans) {
		if (beans != null) {
			for (final BEANTYPE bean : beans) {
				addBeanToContainer(bean);
			}
		}

		checkNamedObjectSort();
		checkAutoSelect();
	}

	/**
	 * add's each bean one by one to container
	 * 
	 * @param item
	 */
	protected void addBeanToContainer(final BEANTYPE item) {
		getContainerDataSource().addItem(item);
		if (this.itemCaptionGenerator != null) {
			if (getItemCaptionMode() == null || getItemCaptionMode() != ItemCaptionMode.EXPLICIT) {
				setItemCaptionMode(ItemCaptionMode.EXPLICIT);
			}
			setItemCaption(item, this.itemCaptionGenerator.getCaption(item));
		}
		if (this.itemIconGenerator != null) {
			setItemIcon(item, this.itemIconGenerator.getIcon(item));
		}
	}

	@Override
	public void replaceAllBeans(final Collection<BEANTYPE> beans) {
		getContainerDataSource().removeAllItems();
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

	private void checkNamedObjectSort() {
		if (NamedObject.class.isAssignableFrom(getContainerDataSource().getBeanType())) {
			sort(new Object[] { "name" }, new boolean[] { true });
		}
	}

	private void checkAutoSelect() {
		// autoselect first bean if nullselection is not allowed and only one
		// bean is there
		if (getContainerDataSource().getItemIds()
									.size() == 1
				&& !isNullSelectionAllowed()) {
			setValue(getContainerDataSource()	.getItemIds()
												.get(0));
		}
	}

	@Override
	public List<BEANTYPE> getAllBeans() {
		return getContainerDataSource().getItemIds();
	}

	@Override
	public boolean removeBean(final BEANTYPE bean) {
		return getContainerDataSource().removeItem(bean);
	}

	@Override
	public void sort(final Object[] propertyId, final boolean[] ascending) {
		getContainerDataSource().sort(propertyId, ascending);
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
	public void addSelectionChangeListener(
			final de.bonprix.vaadin.bean.util.BeanItemSelect.SelectionChangeListener<BEANTYPE> listener) {
		addListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void removeSelectionChangeListener(
			final de.bonprix.vaadin.bean.util.BeanItemSelect.SelectionChangeListener<BEANTYPE> listener) {
		removeListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void addBean(final BEANTYPE bean) {
		addBeanToContainer(bean);

		checkNamedObjectSort();
		checkAutoSelect();
	}

	@Override
	public void selectAll() {
		throw new RuntimeErrorException(null,
				"Not possible to call selectAll on a single select BeanItemComboBox, maybe you wanted to use a BeanItemComboBoxMultiselect?");
	}

	@Override
	public void unselectAll() {
		setValue(null);
	}

	@Override
	protected List<?> getOptionsWithFilter(final boolean needNullSelectOption) {
		return sortListWithComparator(super.getOptionsWithFilter(needNullSelectOption));
	}

	@Override
	protected List<?> getFilteredOptions() {
		return sortListWithComparator(super.getFilteredOptions());
	}

	protected List<?> sortListWithComparator(final List<?> list) {
		if (list == null) {
			return list;
		}

		List<?> res = new ArrayList<>(list);
		if (getBeanComparator() != null) {
			Collections.sort(res, new Comparator<Object>() {

				@SuppressWarnings("unchecked")
				@Override
				public int compare(final Object o1, final Object o2) {
					return BeanItemComboBox.this.comparator.compare((BEANTYPE) o1, (BEANTYPE) o2);
				}
			});
		}

		return Collections.unmodifiableList(res);
	}

	private Comparator<BEANTYPE> getBeanComparator() {
		return this.comparator;
	}

	public void setBeanComparator(final Comparator<BEANTYPE> comparator) {
		this.comparator = comparator;
	}

}
