package de.bonprix.vaadin.fluentui;

import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

import de.bonprix.I18N;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.i18n.localizer.I18NLanguageElementFunction;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.PermissionType;

/**
 * Provides a fluent API to configure a Vaadin {@link Component}.
 *
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class Components<COMPONENT extends Component, CONFIG extends Components<?, ?>> {

	private final COMPONENT component;

	protected Components(final COMPONENT component) {
		this.component = component;
	}

	/**
	 * Call this method to apply all default configurations for a Vaadin
	 * {@link Component}.
	 */
	@SuppressWarnings("unchecked")
	public CONFIG defaults() {
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG id(final String id) {
		get().setId(id);
		return (CONFIG) this;
	}

	public CONFIG captionKey(final String captionKey, final Object... objects) {
		return caption(captionKey == null || "".equals(captionKey) ? captionKey : I18N.get(captionKey, objects));
	}

	<CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> CONFIG get(
			CONTAINER languageContainerEntity, I18NLanguageElementFunction<ELEMENT> function) {
		return caption(I18N.get(languageContainerEntity, function));
	}

	@SuppressWarnings("unchecked")
	public CONFIG caption(final String caption) {
		get().setCaption(caption);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG icon(final Resource resource) {
		get().setIcon(resource);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG width(final String with) {
		get().setWidth(with);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG width(final float width, final Unit unit) {
		get().setWidth(width, unit);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG widthFull() {
		get().setWidth("100%");
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG height(final String with) {
		get().setHeight(with);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG height(final float width, final Unit unit) {
		get().setHeight(width, unit);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG heightFull() {
		get().setHeight("100%");
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG size(final String width, final String height) {
		get().setWidth(width);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG size(final float width, final float height, final Unit unit) {
		get().setWidth(width, unit);
		get().setHeight(height, unit);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG size(final float width, final Unit widthUnit, final float height, final Unit heightUnit) {
		get().setWidth(width, widthUnit);
		get().setHeight(height, heightUnit);
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG sizeFull() {
		get().setSizeFull();
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG sizeUndefined() {
		get().setSizeUndefined();
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG style(final String... styles) {
		if (styles != null) {
			for (final String style : styles) {
				get().addStyleName(style);
			}
		}
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG style(final boolean add, final String... styles) {
		if (add) {
			return style(styles);
		}
		return (CONFIG) this;
	}

	@SuppressWarnings("unchecked")
	public CONFIG enabled(final boolean enabled) {
		get().setEnabled(enabled);
		return (CONFIG) this;
	}

	public CONFIG disabled() {
		return enabled(false);
	}

	@SuppressWarnings("unchecked")
	public CONFIG visible(final boolean visible) {
		get().setVisible(visible);
		return (CONFIG) this;
	}

	public CONFIG invisible() {
		return visible(false);
	}

	@SuppressWarnings("unchecked")
	public CONFIG immediate(final boolean immediate) {
		if (get() instanceof AbstractComponent) {
			final AbstractComponent abstractComponent = (AbstractComponent) this.component;
			abstractComponent.setImmediate(immediate);
		}
		return (CONFIG) this;
	}

	public CONFIG immediate() {
		return immediate(true);
	}

	@SuppressWarnings("unchecked")
	public CONFIG readOnly(final boolean readOnly) {
		get().setReadOnly(readOnly);
		return (CONFIG) this;
	}

	public CONFIG readOnly() {
		return readOnly(true);
	}

	/**
	 * Access the configured Vaadin component.
	 * 
	 * @return the configured component
	 */
	public COMPONENT get() {
		return this.component;
	}

	/**
	 * @param capabilityKey
	 * @return Sets the capability key from which the permission type is
	 *         resolved. Based on the permission type component's properties are
	 *         returned.
	 */
	@SuppressWarnings("unchecked")
	public CONFIG capabilityKey(final String capabilityKey) {
		if (capabilityKey == null || capabilityKey.isEmpty()) {
			return (CONFIG) this;
		}

		if (PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.NONE)) {
			this.component.setVisible(false);
			return (CONFIG) this;
		}
		if (!PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.EDIT)) {
			this.component.setEnabled(false);
			return (CONFIG) this;
		}
		return (CONFIG) this;
	}

}
