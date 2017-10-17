package de.bonprix.vaadin.layout;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.vaadin.fluentui.FluentUI;

public class BpMenuBar extends CustomComponent {
	private static final long serialVersionUID = 1L;

	private final boolean primary;

	private final HorizontalLayout mainLayout;
	private final MenuBar menuBar;

	private final Map<String, MenuItem> elements = new HashMap<>();
	private String activeViewId = null;
	private final NavigationListener navigationListener;

	public BpMenuBar(final NavigationListener navigationListener, final boolean primary) {
		this.navigationListener = navigationListener;
		this.primary = primary;

		this.menuBar = FluentUI	.menuBar()
								.style(ValoTheme.MENUBAR_BORDERLESS, "bp-menubar-menubar")
								.width(100, Unit.PERCENTAGE)
								.get();

		this.mainLayout = FluentUI	.horizontal()
									.sizeFull()
									.add(this.menuBar, 1, Alignment.MIDDLE_LEFT)
									.get();

		setWidth("100%");
		setStyleName("bp-menubar");

		if (this.primary) {
			setHeight("60px");
			addStyleName("primary");
		} else {
			setHeight("46px");
			addStyleName("secondary");
		}

		setCompositionRoot(this.mainLayout);
	}

	public void clear() {
		this.elements.clear();
		this.menuBar.removeItems();
	}

	public void addItem(final String viewId, final String moduleName) {
		MenuItem menuItem = this.menuBar.addItem(moduleName, new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				selectedItem.setChecked(viewId.equals(BpMenuBar.this.activeViewId));
				BpMenuBar.this.navigationListener.onNavigate(	viewId, BpMenuBar.this.primary,
																BpMenuBar.this.activeViewId);
			}
		});
		menuItem.setCheckable(true);
		this.elements.put(viewId, menuItem);
	}

	public void markAsActive(final String viewId) {
		// deactivate active viewId
		if (this.activeViewId != null && this.elements.containsKey(this.activeViewId)) {
			this.elements	.get(this.activeViewId)
							.setChecked(false);
		}

		if (!this.elements.containsKey(viewId)) {
			return;
		}

		this.elements	.get(viewId)
						.setChecked(true);
		this.activeViewId = viewId;
	}

	/**
	 * Set right custom component (for example a menu)
	 * 
	 * @param rightCustomComponent
	 */
	public void setRightCustomComponent(final Component rightCustomComponent) {
		if (rightCustomComponent != null) {
			this.mainLayout.removeComponent(rightCustomComponent);
		}

		this.mainLayout.addComponent(rightCustomComponent);
		this.mainLayout.setComponentAlignment(rightCustomComponent, Alignment.MIDDLE_RIGHT);
	}

	/**
	 * interface for click on view
	 */
	public static interface NavigationListener {
		public void onNavigate(String viewId, boolean primary, String activeViewId);
	}

	/**
	 * if menubar contains viewId
	 * 
	 * @param viewName
	 * @return if menubar contains viewId
	 */
	public boolean contains(String viewName) {
		return this.elements.containsKey(viewName);
	}

	public boolean isFirstViewSelected() {
		return this.menuBar	.getItems()
							.get(0)
							.equals(this.elements.get(this.activeViewId));
	}
}
