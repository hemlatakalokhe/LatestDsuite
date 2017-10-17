package de.bonprix.vaadin.fluentui;

import de.bonprix.vaadin.bean.field.BeanItemExtendedOptionGroup;
import de.bonprix.vaadin.bean.field.BeanItemListSelect;

/**
 * Provides a fluent API to configure a bonprix {@link BeanItemListSelect}
 * component, including all configuration possibilities that the
 * {@link BeanItemSelects}, {@link AbstractSelects}, {@link Components} and
 * {@link Fields} provides.
 */
public class BeanItemExtendedOptionGroups<CONFIG extends BeanItemExtendedOptionGroups<CONFIG, BEANTYPE>, BEANTYPE>
		extends BeanItemSelects<BeanItemExtendedOptionGroup<BEANTYPE>, BEANTYPE, CONFIG> {

	protected BeanItemExtendedOptionGroups(final BeanItemExtendedOptionGroup<BEANTYPE> beanItemListSelect) {
		super(beanItemListSelect);
	}

	@SuppressWarnings("unchecked")
	public CONFIG itemStylePropertyId(Object itemStylePropertyId) {
		get().setItemStylePropertyId(itemStylePropertyId);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG itemCountPropertyId(Object itemCountPropertyId) {
		get().setItemCountPropertyId(itemCountPropertyId);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG maxItemsVisible(int itemCount) {
		get().setMaxItemsVisible(itemCount);
		return (CONFIG) this;
	}

}
