package de.bonprix.vaadin.fluentui;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;

/**
 * Provides a fluent API to configure a Vaadin layout components.
 * 
 * @author Oliver Damm, akquinet engineering GmbH
 */
public class Layouts<LAYOUT extends Layout, CONFIG extends Layouts<LAYOUT, ?>> extends Components<LAYOUT, CONFIG> {

	protected Layouts(final LAYOUT component) {
		super(component);
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(final boolean addComponent, final Component... components) {
		if (components != null) {
			for (final Component component : components) {
				get().addComponent(component);
			}
		}
		return (CONFIG) this;
	}

	public CONFIG add(final Component... components) {
		return add(true, components);
	}

	@SuppressWarnings("unchecked")
	public CONFIG add(final Component component, final boolean addComponent) {
		if (addComponent) {
			get().addComponent(component);
		}
		return (CONFIG) this;
	}

	public CONFIG add(final Component component) {
		return add(component, true);
	}

	public static class AbstractLayouts<LAYOUT extends AbstractLayout, CONFIG extends AbstractLayouts<LAYOUT, ?>>
			extends Layouts<LAYOUT, CONFIG> {

		protected AbstractLayouts(final LAYOUT component) {
			super(component);
		}

		@SuppressWarnings("unchecked")
		public CONFIG childsFullWidth() {
			for (final Component c : get()) {
				c.setWidth(100, Unit.PERCENTAGE);
			}
			return (CONFIG) this;
		}

		@SuppressWarnings("unchecked")
		public CONFIG captionKeyAsHtml(final boolean captionAsHtml) {
			get().setCaptionAsHtml(captionAsHtml);
			return (CONFIG) this;
		}

	}

	public static class CssLayouts<LAYOUT extends CssLayout, CONFIG extends CssLayouts<LAYOUT, ?>>
			extends AbstractLayouts<LAYOUT, CONFIG> {

		protected CssLayouts(final LAYOUT component) {
			super(component);
		}

		@SuppressWarnings("unchecked")
		public CONFIG layoutClickListener(final LayoutClickListener listener) {
			get().addLayoutClickListener(listener);
			return (CONFIG) this;
		}

		@SuppressWarnings("unchecked")
		public CONFIG primaryStyle(final String style) {
			get().setPrimaryStyleName(style);
			return (CONFIG) this;
		}

	}

	public static class AbstractOrderedLayouts<LAYOUT extends AbstractOrderedLayout, CONFIG extends AbstractOrderedLayouts<LAYOUT, ?>>
			extends AbstractLayouts<LAYOUT, CONFIG> {

		protected AbstractOrderedLayouts(final LAYOUT component) {
			super(component);
		}

		@SuppressWarnings("unchecked")
		public CONFIG spacing(final boolean spacing) {
			get().setSpacing(spacing);
			return (CONFIG) this;
		}

		public CONFIG spacing() {
			return spacing(true);
		}

		@SuppressWarnings("unchecked")
		public CONFIG margin(final boolean margin) {
			get().setMargin(margin);
			return (CONFIG) this;
		}

		public CONFIG margin() {
			return margin(true);
		}

		@SuppressWarnings("unchecked")
		public CONFIG margin(final boolean top, final boolean right, final boolean bottom, final boolean left) {
			get().setMargin(new MarginInfo(top, right, bottom, left));
			return (CONFIG) this;
		}

		@SuppressWarnings("unchecked")
		public CONFIG add(final Component component, final float ratio, final Alignment alignment,
				final boolean addComponent) {
			if (addComponent) {
				get().addComponent(component);
				get().setExpandRatio(component, ratio);
				get().setComponentAlignment(component, alignment);
			}
			return (CONFIG) this;
		}

		public CONFIG add(final Component component, final float ratio, final Alignment alignment) {
			return add(component, ratio, alignment, true);
		}

		@SuppressWarnings("unchecked")
		public CONFIG add(final Component component, final float ratio, final boolean addComponent) {
			if (addComponent) {
				get().addComponent(component);
				get().setExpandRatio(component, ratio);
			}
			return (CONFIG) this;
		}

		public CONFIG add(final Component component, final float ratio) {
			return add(component, ratio, true);
		}

		@SuppressWarnings("unchecked")
		public CONFIG add(final Component component, final Alignment alignment, final boolean addComponent) {
			if (addComponent) {
				get().addComponent(component);
				get().setComponentAlignment(component, alignment);
			}
			return (CONFIG) this;
		}

		public CONFIG add(final Component component, final Alignment alignment) {
			return add(component, alignment, true);
		}
	}

}
