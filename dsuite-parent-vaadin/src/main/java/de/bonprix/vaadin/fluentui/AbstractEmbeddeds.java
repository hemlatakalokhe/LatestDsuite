package de.bonprix.vaadin.fluentui;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractEmbedded;
import com.vaadin.ui.Label;

import de.bonprix.I18N;

/**
 * @author Oliver Damm, akquinet engineering GmbH
 * 
 */
public abstract class AbstractEmbeddeds<COMPONENT extends AbstractEmbedded, CONFIG extends AbstractEmbeddeds<?, ?>>
		extends Components<COMPONENT, CONFIG> {

	protected AbstractEmbeddeds(final COMPONENT component) {
		super(component);
	}

	/**
	 * Call the method to apply all default configurations for a Vaadin
	 * {@link Label}.
	 */
	@Override
	public CONFIG defaults() {
		return super.defaults();
	}

	@SuppressWarnings("unchecked")
	public CONFIG source(Resource source) {
		get().setSource(source);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG source(String sourceURL) {
		get().setSource(new ExternalResource(sourceURL));
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG alternateTextKey(String altTextKey, Object... objects) {
		get().setAlternateText(I18N.get(altTextKey, objects));
		return (CONFIG) this;
	}

}
