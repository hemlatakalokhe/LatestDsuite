package de.bonprix.vaadin.fluentui;

import java.util.Collection;

import com.vaadin.ui.AbstractSelect;

import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;
import de.bonprix.vaadin.bean.util.BeanItemSelect;
import de.bonprix.vaadin.bean.util.BeanItemSelect.SelectionChangeListener;

/**
 * Provides a fluent API to configure a bonprix {@link BeanItemSelect}
 * implementing component, including all configuration possibilities that the
 * {@link AbstractSelects}, {@link Components} and {@link Fields} provides.
 */
public abstract class BeanItemSelects<BEANITEMSELECT extends AbstractSelect & BeanItemSelect<BEANTYPE>, BEANTYPE, CONFIG extends BeanItemSelects<BEANITEMSELECT, BEANTYPE, CONFIG>>
		extends AbstractSelects<BEANITEMSELECT, CONFIG> {

	protected BeanItemSelects(final BEANITEMSELECT select) {
		super(select);
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(BEANTYPE bean) {
		get().addBean(bean);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(Collection<BEANTYPE> beans) {
		get().addAllBeans(beans);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG selectAll() {
		get().selectAll();
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG itemCaptionGenerator(ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		get().setItemCaptionGenerator(itemCaptionGenerator);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG itemIconGenerator(ItemIconGenerator<BEANTYPE> itemIconGenerator) {
		get().setItemIconGenerator(itemIconGenerator);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG onSelectionChange(SelectionChangeListener<BEANTYPE> listener) {
		get().addSelectionChangeListener(listener);
		return (CONFIG) this;
	}

}
