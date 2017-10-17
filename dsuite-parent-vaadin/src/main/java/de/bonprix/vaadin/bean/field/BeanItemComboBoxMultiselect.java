package de.bonprix.vaadin.bean.field;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.vaadin.addons.comboboxmultiselect.ComboBoxMultiselect;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import de.bonprix.I18N;
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
public class BeanItemComboBoxMultiselect<BEANTYPE> extends ComboBoxMultiselect implements BeanItemSelect<BEANTYPE> {
	private static final long serialVersionUID = 1L;

	private ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator = null;
	private ItemIconGenerator<BEANTYPE> itemIconGenerator = null;

	/**
	 *
	 */
	public BeanItemComboBoxMultiselect(final Class<BEANTYPE> clazz) {
		this(clazz, null);
	}

	/**
	 * @param caption
	 */
	public BeanItemComboBoxMultiselect(final Class<BEANTYPE> clazz, final String caption) {
		this(clazz, caption, new BeanItemContainer<BEANTYPE>(clazz));
	}

	/**
	 * @param caption
	 * @param beans
	 */
	public BeanItemComboBoxMultiselect(final Class<BEANTYPE> clazz, final String caption,
			final Collection<BEANTYPE> beans) {
		this(clazz, caption, new BeanItemContainer<BEANTYPE>(clazz));

		addValueChangeListener(event -> fireEvent(new SelectionChangeEvent<BEANTYPE>(BeanItemComboBoxMultiselect.this,
				getSelectedItems())));
		addAllBeans(beans);
	}

	/**
	 * @param clazz
	 *            the bean type
	 * @param caption
	 * @param container
	 */
	public BeanItemComboBoxMultiselect(final Class<BEANTYPE> clazz, final String caption,
			final BeanItemContainer<BEANTYPE> container) {
		super(caption, container);

		setClearButtonCaptionKey("CLEAR");
		setSelectAllButtonCaptionKey("SELECT_ALL");
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
	public BeanItemComboBoxMultiselect<BEANTYPE> withItemCaptionGenerator(
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
	public BeanItemComboBoxMultiselect<BEANTYPE> withItemIconGenerator(
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
		super.selectAll();
	}

	@Override
	public void unselectAll() {
		super.unselectAll();
	}

	public void setBeanComparator(final Comparator<BEANTYPE> comparator) {
		setComparator(new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(final Object o1, final Object o2) {
				return comparator.compare((BEANTYPE) o1, (BEANTYPE) o2);
			}
		});
	}

	public void setClearButtonCaptionKey(String captionKey, Object... objects) {
		setClearButtonCaption(I18N.get(captionKey, objects));
	}

	public void setSelectAllButtonCaptionKey(String captionKey, Object... objects) {
		setSelectAllButtonCaption(I18N.get(captionKey, objects));
	}

}