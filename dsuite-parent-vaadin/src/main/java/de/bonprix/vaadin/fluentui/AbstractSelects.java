package de.bonprix.vaadin.fluentui;

import com.vaadin.data.Container;
import com.vaadin.ui.AbstractSelect;

/**
 * Provides a fluent API to configure a Vaadin {@link AbstractSelect} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class AbstractSelects<SELECT extends AbstractSelect, CONFIG extends AbstractSelects<SELECT, CONFIG>>
		extends AbstractFields<SELECT, Object, CONFIG> {

	protected AbstractSelects(final SELECT select) {
		super(select);
	}

	@SuppressWarnings("unchecked")
	public CONFIG containerDataSource(Container containerDataSource) {
		get().setContainerDataSource(containerDataSource);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(Object... itemIds) {
		get().addItems(itemIds);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG select(Object itemId) {
		get().select(itemId);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG multiselect() {
		get().setMultiSelect(true);
		return (CONFIG) this;
	}

}
