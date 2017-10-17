package de.bonprix.vaadin.fluentui;

import de.bonprix.vaadin.bean.field.BeanItemListSelect;

/**
 * Provides a fluent API to configure a bonprix {@link BeanItemListSelect} component,
 * including all configuration possibilities that the {@link BeanItemSelects},
 * {@link AbstractSelects}, {@link Components} and {@link Fields} provides.
 */
public class BeanItemListSelects<CONFIG extends BeanItemListSelects<CONFIG, BEANTYPE>, BEANTYPE> extends BeanItemSelects<BeanItemListSelect<BEANTYPE>, BEANTYPE, CONFIG> {

	protected BeanItemListSelects(final BeanItemListSelect<BEANTYPE> beanItemListSelect) {
		super(beanItemListSelect);
	}

}
