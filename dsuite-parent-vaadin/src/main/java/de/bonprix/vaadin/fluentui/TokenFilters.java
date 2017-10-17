package de.bonprix.vaadin.fluentui;

import java.util.List;
import java.util.Set;

import org.vaadin.addons.tokenfilter.TokenFilter;
import org.vaadin.addons.tokenfilter.model.FilterType;

import com.vaadin.ui.AbstractSelect;

/**
 * Provides a fluent API to configure a Vaadin {@link AbstractSelect} component,
 * including all configuration possibilities that the {@link Components} and
 * {@link Fields} provides.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class TokenFilters<CONFIG extends TokenFilters<CONFIG, FILTERTYPE>, FILTERTYPE extends FilterType<?>>
		extends AbstractFields<TokenFilter<FILTERTYPE>, Set<FILTERTYPE>, CONFIG> {

	protected TokenFilters(final TokenFilter<FILTERTYPE> tokenFilter) {
		super(tokenFilter);
	}

	@SuppressWarnings("unchecked")
	public CONFIG maxItemsVisible(Integer maxItemsVisible) {
		get().setMaxItemsVisible(maxItemsVisible);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(List<FILTERTYPE> beans) {
		get().replaceItems(beans);
		return (CONFIG) this;
	}

}
