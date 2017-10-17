/**
 *
 */
package de.bonprix.vaadin.bean.field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.vaadin.addons.tokenfilter.ExtendedOptionGroup;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ListSelect;

import de.bonprix.dto.NamedObject;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;
import de.bonprix.vaadin.bean.util.BeanItemSelect;
import de.bonprix.vaadin.bean.util.BeanItemSelectUtil;

/**
 * The {@link BeanItemExtendedOptionGroup} is an extension to the default vaadin
 * {@link ListSelect}. This implementation uses a bean item container as default
 * container and some useful methods to modify the container directly.
 * 
 * @author cthiel
 *
 */
public class BeanItemExtendedOptionGroup<BEANTYPE> extends ExtendedOptionGroup<BEANTYPE>
		implements BeanItemSelect<BEANTYPE> {
	private static final long serialVersionUID = 1L;

	private ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator = null;
	private ItemIconGenerator<BEANTYPE> itemIconGenerator = null;

	/**
	 *
	 */
	public BeanItemExtendedOptionGroup(final Class<BEANTYPE> clazz) {
		this(clazz, new ArrayList<BEANTYPE>());
	}

	/**
	 * @param caption
	 * @param beans
	 */
	public BeanItemExtendedOptionGroup(final Class<BEANTYPE> clazz, final Collection<BEANTYPE> beans) {
		super(new BeanItemContainer<BEANTYPE>(clazz));

		addValueChangeListener(event -> fireEvent(new SelectionChangeEvent<BEANTYPE>(BeanItemExtendedOptionGroup.this,
				getSelectedItems())));
		addAllBeans(beans);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanItemContainer<BEANTYPE> getContainerDataSource() {
		return (BeanItemContainer<BEANTYPE>) super.getContainerDataSource();
	}

	/**
	 * implements a custom generator of caption
	 * 
	 * @param itemCaptionGenerator
	 */
	@Override
	public void setItemCaptionGenerator(final ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		this.itemCaptionGenerator = itemCaptionGenerator;
	}

	/**
	 * implements a custom generator of icon
	 * 
	 * @param itemIconGenerator
	 */
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
	public BeanItemExtendedOptionGroup<BEANTYPE> withItemCaptionGenerator(
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
	public BeanItemExtendedOptionGroup<BEANTYPE> withItemIconGenerator(
			final ItemIconGenerator<BEANTYPE> itemIconGenerator) {
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
	public List<BEANTYPE> getSelectedItems() {
		return new ArrayList<BEANTYPE>(BeanItemSelectUtil.getSelectedItems(this));
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
}
