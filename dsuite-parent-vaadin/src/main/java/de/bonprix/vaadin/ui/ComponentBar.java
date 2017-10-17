package de.bonprix.vaadin.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import de.bonprix.I18N;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * @author s.amin
 * @version 1.0 Creates a component bar with any components in a horizontal bar
 *          format which extends AbstractComponent class
 */
public class ComponentBar extends CustomComponent {

	private static final long serialVersionUID = 3715720375679755901L;

	public enum ComponentBarPlacement {
		LEFT(Alignment.TOP_LEFT), CENTER(Alignment.TOP_CENTER), RIGHT(Alignment.TOP_RIGHT);

		private final Alignment alignment;

		private ComponentBarPlacement(final Alignment alignment) {
			this.alignment = alignment;
		}

		public Alignment getAlignment() {
			return this.alignment;
		}

	}

	public abstract static class ComponentBarElement {
		private ComponentBarPlacement placement = ComponentBarPlacement.LEFT;
		private boolean enabled = true;
		private boolean visible = true;

		public ComponentBarPlacement getPlacement() {
			return this.placement;
		}

		public ComponentBarElement withPlacement(final ComponentBarPlacement placement) {
			this.placement = placement;
			return this;
		}

		/**
		 * @return the enabled
		 */
		public boolean isEnabled() {
			return this.enabled;
		}

		/**
		 * @param enabled
		 *            the enabled to set
		 */
		public ComponentBarElement withEnabled(final boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 * @return the visible
		 */
		public boolean isVisible() {
			return this.visible;
		}

		/**
		 * @param visible
		 *            sets the visibility
		 */
		public ComponentBarElement withVisible(final boolean visible) {
			this.visible = visible;
			return this;
		}

		/**
		 * @param capabilityKey
		 *            on ComponentBarElement component
		 */
		public ComponentBarElement withCapabilityKey(final String capabilityKey) {
			if (capabilityKey == null || capabilityKey.isEmpty()) {
				return this;
			}

			if (PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.NONE)) {
				return this.withVisible(false);
			}

			if (!PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.EDIT)) {
				return this.withEnabled(false);
			}

			return this;
		}

	}

	/**
	 * Item builder for component bar.
	 *
	 * @author cthiel
	 *
	 */
	public static class ComponentBarItem extends ComponentBarElement {
		private final String id;
		private final String caption;
		private String tooltip;
		private Resource icon;
		private String parentId;
		private Runnable clickAction;
		private boolean enabled = true;
		private boolean highlightedMenu = false;

		public ComponentBarItem(final String id, final String captionKey, final Object... objects) {
			this.id = id;
			this.caption = I18N.get(captionKey, objects);
		}

		public ComponentBarItem(final Enum enumId, final String captionKey, final Object... objects) {
			this(enumId.name(), captionKey, objects);
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return this.id;
		}

		/**
		 * @return the captionKey
		 */
		public String getCaption() {
			return this.caption;
		}

		/**
		 * @return the tooltipKey
		 */
		public String getTooltip() {
			return this.tooltip;
		}

		/**
		 * @param tooltipKey
		 *            the tooltipKey to set
		 */
		public ComponentBarItem withTooltipKey(final String tooltipKey, final Object... objects) {
			this.tooltip = I18N.get(tooltipKey, objects);
			return this;
		}

		/**
		 * @return the icon
		 */
		public Resource getIcon() {
			return this.icon;
		}

		/**
		 * @param icon
		 *            the icon to set
		 */
		public ComponentBarItem withIcon(final Resource icon) {
			this.icon = icon;

			return this;
		}

		/**
		 * @return the parentId
		 */
		public String getParentId() {
			return this.parentId;
		}

		/**
		 * @param parentId
		 *            the parentId to set
		 */
		public ComponentBarItem withParentId(final String parentId) {
			this.parentId = parentId;

			return this;
		}

		/**
		 * @param enumParentId
		 *            the enumParentId to set
		 */
		public ComponentBarItem withParentId(final Enum enumParentId) {
			return withParentId(enumParentId.name());
		}

		/**
		 * @return the clickAction
		 */
		public Runnable getClickAction() {
			return this.clickAction;
		}

		/**
		 * @param clickAction
		 *            the clickAction to set
		 */
		public ComponentBarItem withClickAction(final Runnable clickAction) {
			this.clickAction = clickAction;

			return this;
		}

		/**
		 * @return the enabled
		 */
		@Override
		public boolean isEnabled() {
			return this.enabled;
		}

		/**
		 * @param enabled
		 *            the enabled to set
		 */
		@Override
		public ComponentBarItem withEnabled(final boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 *
		 * @return the highlighted menu
		 */
		public boolean isHighlightedMenu() {
			return this.highlightedMenu;
		}

		/**
		 *
		 * @return the component bar item the highlightedMenu to set
		 */
		public ComponentBarItem withHighlightedMenu() {
			this.highlightedMenu = true;
			return this;
		}

	}

	/**
	 * Component builder for component bar.
	 *
	 * @author cthiel
	 *
	 */
	public static class ComponentBarComponent extends ComponentBarElement {
		private final AbstractComponent component;
		private Alignment alignment;

		public ComponentBarComponent(final AbstractComponent component) {
			this.component = component;
		}

		public AbstractComponent getComponent() {
			return this.component;
		}

		public Alignment getAlignment() {
			return this.alignment;
		}

		public ComponentBarComponent withAlignment(final Alignment alignment) {
			this.alignment = alignment;
			return this;
		}

	}

	private final Map<String, MenuItem> menuItemMap;
	private Map<MenuItem, String> menuItemCaptionMap;
	private final EnumMap<ComponentBarPlacement, HorizontalLayout> placementLayouts;

	public enum ComponentBarStyle {
		BUTTON(DSuiteTheme.BUTTON_BAR), SMALL(DSuiteTheme.BUTTON_BAR_SMALL), ICON(DSuiteTheme.BUTTON_BAR_SMALL);

		private final String cssStyleName;

		private ComponentBarStyle(final String cssStyleName) {
			this.cssStyleName = cssStyleName;
		}

		public String getCssStyleName() {
			return this.cssStyleName;
		}
	}

	private final ComponentBarStyle style;

	/**
	 * Creates an empty toolbar. This toolbar will use no eventbus. Every
	 * attempt to add a button with an associated event will result in an
	 * {@link IllegalArgumentException}.
	 */
	public ComponentBar() {
		this(ComponentBarStyle.BUTTON);
	}

	/**
	 * Creates an empty toolbar. This toolbar will use no eventbus. Every
	 * attempt to add a button with an associated event will result in an
	 * {@link IllegalArgumentException}.
	 */
	public ComponentBar(final ComponentBarStyle style, final String... extraStyles) {
		this.style = style;
		setStyleName(style.getCssStyleName());
		for (final String extraStyle : extraStyles) {
			addStyleName(extraStyle);
		}

		this.menuItemMap = new HashMap<>();

		this.menuItemCaptionMap = new HashMap<>();

		this.placementLayouts = new EnumMap<>(ComponentBarPlacement.class);
		for (final ComponentBarPlacement placement : ComponentBarPlacement.values()) {
			this.placementLayouts.put(placement, FluentUI.horizontal()
				.get());
		}

		setCompositionRoot(FluentUI.horizontal()
			.widthFull()
			.get());
		setWidth(100, Unit.PERCENTAGE);
	}

	public void addElement(final ComponentBarElement element) {
		if (element instanceof ComponentBarComponent) {
			ComponentBarComponent componentBarComponent = (ComponentBarComponent) element;
			addComponent(componentBarComponent);
			return;
		}
		if (element instanceof ComponentBarItem) {
			ComponentBarItem componentBarItem = (ComponentBarItem) element;
			addItem(componentBarItem);
			return;
		}
	}

	/**
	 * Adds a component to the componentbar
	 *
	 * @param component
	 * @param alignment
	 */
	private void addComponent(final ComponentBarComponent component) {

		if (ComponentBarStyle.SMALL.equals(this.style) && component.getComponent()
			.getIcon() == null) {
			throw new IllegalArgumentException("In the toolbar is in styleMode small, the icon cannot be null");
		}

		if (ComponentBarStyle.ICON.equals(this.style)) {
			component.getComponent()
				.setCaption("");
			component.getComponent()
				.addStyleName(DSuiteTheme.BUTTON_LINK);
		}

		component.getComponent()
			.addStyleName(DSuiteTheme.COMPONENTBAR);
		component.getComponent()
			.addStyleName(DSuiteTheme.COMPONENTBAR_COMPONENT);

		final HorizontalLayout placementLayout = this.placementLayouts.get(component.getPlacement());

		placementLayout.addComponent(component.getComponent());
		if (component.getAlignment() != null) {
			placementLayout.setComponentAlignment(component.getComponent(), component.getAlignment());
		}
		component.getComponent()
			.setEnabled(component.isEnabled());
		component.getComponent()
			.setVisible(component.isVisible());
	}

	/**
	 * Adds a toolbar action.
	 *
	 * @param action
	 *            the toolbar action to add
	 */
	private void addItem(final ComponentBarItem action) {
		if (this.menuItemMap.containsKey(action.getId())) {
			throw new IllegalArgumentException("Action with " + action.getId() + " already registered");
		}

		final Command command = new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (selectedItem.getParent() != null) {
					MenuItem menuItem = selectedItem;
					List<String> parentCaptionList = new ArrayList<>();

					while (menuItem.getParent() != null) {
						parentCaptionList.add(menuItem.getText());
						menuItem = menuItem.getParent();

					}
					if (action.isHighlightedMenu()) {
						Collections.reverse(parentCaptionList);
						String highlightTest = StringUtils.join(parentCaptionList, " - ");
						String originalCaption = ComponentBar.this.menuItemCaptionMap.get(menuItem);
						menuItem.setText(originalCaption.concat(" (")
							.concat(highlightTest)
							.concat(")"));

					} else {
						if (!(menuItem.getText()
							.equals(ComponentBar.this.menuItemCaptionMap.get(menuItem)))) {
							menuItem.setText(ComponentBar.this.menuItemCaptionMap.get(menuItem));
						}
					}

				}

				if (action.getClickAction() != null) {
					action.getClickAction()
						.run();
				}
			}
		};
		MenuItem item;
		if (action.getParentId() != null && this.menuItemMap.containsKey(action.getParentId())) {
			item = getItem(action.getParentId()).addItem(action.getCaption(), command);
		} else {
			MenuBar menuBar;
			HorizontalLayout placementLayout = this.placementLayouts.get(action.getPlacement());
			if (placementLayout.getComponentCount() > 0
					&& placementLayout.getComponent(placementLayout.getComponentCount() - 1) instanceof MenuBar) {
				menuBar = (MenuBar) placementLayout.getComponent(placementLayout.getComponentCount() - 1);
			} else {
				menuBar = new MenuBar();
				menuBar.setAutoOpen(true);
				menuBar.setStyleName(DSuiteTheme.COMPONENTBAR);
				placementLayout.addComponent(menuBar);
			}

			item = menuBar.addItem(action.getCaption(), command);
		}

		item.setIcon(action.getIcon());
		item.setDescription(action.getTooltip());

		this.menuItemMap.put(action.getId(), item);
		this.menuItemCaptionMap.put(item, action.getCaption());
		setItemEnabled(action.getId(), action.isEnabled());
	}

	@Override
	public void attach() {

		for (final ComponentBarPlacement placement : ComponentBarPlacement.values()) {
			final HorizontalLayout placementLayout = this.placementLayouts.get(placement);
			if (needsToBeAdded(placementLayout)) {
				((HorizontalLayout) getCompositionRoot()).addComponent(placementLayout);
				((HorizontalLayout) getCompositionRoot()).setComponentAlignment(placementLayout,
																				placement.getAlignment());
			}
		}

		super.attach();
	}

	/**
	 * If layout needs to be added, to make sure center layout is always in
	 * center
	 *
	 * @param placementLayout
	 *            current Layout to be added
	 * @return
	 */
	private boolean needsToBeAdded(final HorizontalLayout placementLayout) {
		if (placementLayout.getComponentCount() > 0) {
			return true;
		}

		if (this.placementLayouts.get(ComponentBarPlacement.CENTER)
			.getComponentCount() == 0) {
			return false;
		}

		return this.placementLayouts.get(ComponentBarPlacement.LEFT)
			.getComponentCount() > 0
				|| this.placementLayouts.get(ComponentBarPlacement.RIGHT)
					.getComponentCount() > 0;
	}

	/**
	 * Returns the specified menu component
	 *
	 * @param itemId
	 * @return MenutItem
	 */
	public MenuItem getItem(final String itemId) {
		if (!this.menuItemMap.containsKey(itemId)) {
			throw new IllegalArgumentException("No component with " + itemId + " registered");
		}

		return this.menuItemMap.get(itemId);
	}

	/**
	 * Returns whether specified itemId is enabled
	 *
	 * @param itemId
	 * @return boolean enabled
	 *
	 */
	public boolean isItemEnabled(final String itemId) {
		return getItem(itemId).isEnabled();
	}

	/**
	 * Enables or disables the component item
	 *
	 * @param itemId
	 * @param enabled
	 */
	public void setItemEnabled(final String itemId, final boolean enabled) {
		getItem(itemId).setEnabled(enabled);
	}

	public MenuItem getFirstParentItem(final String itemId) {
		return getFirstParentItemRecursive(getItem(itemId));
	}

	public MenuItem getFirstParentItemRecursive(final MenuItem menuItem) {
		MenuItem parent = menuItem.getParent();
		if (parent == null) {
			return menuItem;
		}
		return getFirstParentItemRecursive(parent);
	}

}