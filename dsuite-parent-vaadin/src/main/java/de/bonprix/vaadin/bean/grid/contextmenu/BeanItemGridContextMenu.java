package de.bonprix.vaadin.bean.grid.contextmenu;

import java.io.Serializable;
import java.util.EventListener;

import com.vaadin.addon.contextmenu.ContextMenu;
import com.vaadin.addon.contextmenu.ContextMenu.ContextMenuOpenListener.ContextMenuOpenEvent;
import com.vaadin.addon.contextmenu.GridContextMenu;
import com.vaadin.addon.contextmenu.MenuItem;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.grid.GridConstants.Section;
import com.vaadin.ui.Grid.GridContextClickEvent;
import com.vaadin.ui.UI;

import de.bonprix.I18N;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.bean.grid.contextmenu.BeanItemGridContextMenu.BeanItemGridContextMenuOpenListener.BeanItemGridContextMenuOpenEvent;
import de.bonprix.vaadin.bean.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder;
//import de.bonprix.vaadin.bean.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder;
import de.bonprix.vaadin.bean.grid.contextmenu.primaryinfo.GridPrimaryInfoDialog;

@SuppressWarnings("serial")
public class BeanItemGridContextMenu<BEANTYPE> extends GridContextMenu {

	private Class<? super BEANTYPE> clazz;
	private BEANTYPE bean;

	private ContextMenuOpenListener defaultHeaderContextMenuOpenListener;
	private ContextMenuOpenListener defaultBodyContextMenuOpenListener;
	private ContextMenuOpenListener defaultFooterContextMenuOpenListener;

	public BeanItemGridContextMenu(BeanItemGrid<? super BEANTYPE> parentComponent, Class<? super BEANTYPE> clazz) {
		super(parentComponent);
		this.clazz = clazz;

		// needed to know which element was selected at the moment
		addBeanItemGridBodyContextMenuListener(event -> {
			this.bean = event.getItemId();
		});

		// adding default header, body and footer listener to remove all items
		// from
		// context menu
		this.defaultHeaderContextMenuOpenListener = addBeanItemGridHeaderContextMenuListener(event -> {
			this.removeAllItems();
		});
		this.defaultBodyContextMenuOpenListener = addBeanItemGridBodyContextMenuListener(event -> {
			this.removeAllItems();
		});
		this.defaultFooterContextMenuOpenListener = addBeanItemGridFooterContextMenuListener(event -> {
			this.removeAllItems();
		});
	}

	/**
	 * Use the
	 * {@link #addBeanItemGridHeaderContextMenuListener(BeanItemGridContextMenuOpenListener)
	 * addBeanItemGridHeaderContextMenuListener} method.
	 */
	@Override
	@Deprecated
	public void addGridHeaderContextMenuListener(GridContextMenuOpenListener listener) {
		super.addGridHeaderContextMenuListener(listener);
	}

	/**
	 * Use the
	 * {@link #addBeanItemGridFooterContextMenuListener(BeanItemGridContextMenuOpenListener)
	 * addBeanItemGridFooterContextMenuListener} method.
	 */
	@Override
	@Deprecated
	public void addGridFooterContextMenuListener(GridContextMenuOpenListener listener) {
		super.addGridFooterContextMenuListener(listener);
	}

	/**
	 * Use the
	 * {@link #addBeanItemGridBodyContextMenuListener(BeanItemGridContextMenuOpenListener)
	 * addBeanItemGridBodyContextMenuListener} method.
	 */
	@Override
	@Deprecated
	public void addGridBodyContextMenuListener(GridContextMenuOpenListener listener) {
		super.addGridBodyContextMenuListener(listener);
	}

	public ContextMenuOpenListener addBeanItemGridHeaderContextMenuListener(
			BeanItemGridContextMenuOpenListener<BEANTYPE> listener) {
		return addBeanItemGridSectionContextMenuListener(Section.HEADER, listener);
	}

	public ContextMenuOpenListener addBeanItemGridFooterContextMenuListener(
			BeanItemGridContextMenuOpenListener<BEANTYPE> listener) {
		return addBeanItemGridSectionContextMenuListener(Section.FOOTER, listener);
	}

	public ContextMenuOpenListener addBeanItemGridBodyContextMenuListener(
			BeanItemGridContextMenuOpenListener<BEANTYPE> listener) {
		return addBeanItemGridSectionContextMenuListener(Section.BODY, listener);
	}

	private ContextMenuOpenListener addBeanItemGridSectionContextMenuListener(final Section section,
			final BeanItemGridContextMenuOpenListener<BEANTYPE> listener) {
		clearDefaultContextMenuOpenListener(section);

		ContextMenuOpenListener contextMenuOpenListener = new ContextMenuOpenListener() {
			@Override
			public void onContextMenuOpen(ContextMenuOpenEvent event) {
				if (event.getContextClickEvent() instanceof GridContextClickEvent) {
					GridContextClickEvent gridEvent = (GridContextClickEvent) event.getContextClickEvent();
					if (gridEvent.getSection() == section) {
						listener.onContextMenuOpen(new BeanItemGridContextMenuOpenEvent<BEANTYPE>(
								BeanItemGridContextMenu.this, gridEvent));
					}
				}
			}
		};

		addContextMenuOpenListener(contextMenuOpenListener);

		return contextMenuOpenListener;
	}

	private void clearDefaultContextMenuOpenListener(Section section) {
		switch (section) {
		case FOOTER:
			if (this.defaultFooterContextMenuOpenListener != null) {
				removeContextMenuOpenListener(this.defaultFooterContextMenuOpenListener);
				this.defaultFooterContextMenuOpenListener = null;
			}
			break;
		case HEADER:
			if (this.defaultHeaderContextMenuOpenListener != null) {
				removeContextMenuOpenListener(this.defaultHeaderContextMenuOpenListener);
				this.defaultHeaderContextMenuOpenListener = null;
			}
			break;
		case BODY:
			if (this.defaultBodyContextMenuOpenListener != null) {
				removeContextMenuOpenListener(this.defaultBodyContextMenuOpenListener);
				this.defaultBodyContextMenuOpenListener = null;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Use the {@link #withItem(BeanItemGridContextMenuItem)
	 * addItem(BeanItemGridContextMenuItem)} method with
	 * {@link de.bonprix.vaadin.ui.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder
	 * BeanItemGridContextMenuItemBuilder}.
	 */
	@Override
	@Deprecated
	public MenuItem addItem(String caption, Command command) {
		return super.addItem(caption, command);
	}

	/**
	 * Use the {@link #withItem(BeanItemGridContextMenuItem)
	 * addItem(BeanItemGridContextMenuItem)} method with
	 * {@link de.bonprix.vaadin.ui.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder
	 * BeanItemGridContextMenuItemBuilder}.
	 */
	@Override
	@Deprecated
	public MenuItem addItem(String caption, Resource icon, Command command) {
		return super.addItem(caption, icon, command);
	}

	/**
	 * Use the {@link #withItem(BeanItemGridContextMenuItem)
	 * addItem(BeanItemGridContextMenuItem)} method with
	 * {@link de.bonprix.vaadin.ui.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder
	 * BeanItemGridContextMenuItemBuilder}.
	 */
	@Override
	@Deprecated
	public MenuItem addItemBefore(String caption, Resource icon, Command command, MenuItem itemToAddBefore) {
		return super.addItemBefore(caption, icon, command, itemToAddBefore);
	}

	/**
	 * Use the {@link #removeAllItems() removeAllItems()} method.
	 */
	@Override
	@Deprecated
	public void removeItems() {
		super.removeItems();
	}

	public BeanItemGridContextMenu<BEANTYPE> removeAllItems() {
		super.removeItems();
		return this;
	}

	/**
	 * Adds an beanitem to the context menu, use with
	 * {@link de.bonprix.vaadin.ui.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder
	 * BeanItemGridContextMenuItemBuilder}.
	 * 
	 * @param item
	 * @return self
	 */
	public BeanItemGridContextMenu<BEANTYPE> withItem(BeanItemGridContextMenuItem<BEANTYPE> item) {
		addItemRecursive(item, null);

		return this;
	}

	private MenuItem addItemRecursive(BeanItemGridContextMenuItem<BEANTYPE> item,
			BeanItemGridContextMenuItem<BEANTYPE> parentItem) {
		MenuItem menuItem = addItem(I18N.get(item.getCaptionKey()), item.getIcon(), selectedItem -> {
			item.getBeanCommand()
				.beanSelected(selectedItem, this.bean);
		});
		menuItem.setCheckable(item.isCheckable());
		menuItem.setChecked(item.isChecked());
		if (item.isSeperator()) {
			addSeparator();
		}

		if (parentItem != null) {
			removeItem(menuItem);
		}

		if (item.getChildren() != null) {
			for (BeanItemGridContextMenuItem<BEANTYPE> childItem : item.getChildren()) {
				// workaround with dummy, cant initialise children from the
				// outside
				MenuItem dummy = null;
				if (!menuItem.hasChildren()) {
					dummy = menuItem.addItem("dummy", null);
				}
				menuItem.getChildren()
					.add(addItemRecursive(childItem, item));
				if (dummy != null) {
					menuItem.removeChild(dummy);
				}
			}
		}

		return menuItem;
	}

	/**
	 * adds the option of opening a dialog with the primary information of the
	 * clicked row without seperator
	 * 
	 * @return self
	 */
	public BeanItemGridContextMenu<BEANTYPE> withPrimaryInfoItem() {
		return withPrimaryInfoItem(false);
	}

	/**
	 * adds the option of opening a dialog with the primary information of the
	 * clicked row
	 * 
	 * @param seperator
	 *            if after the MenuItem a seperator (line) follows
	 * @return self
	 */
	public BeanItemGridContextMenu<BEANTYPE> withPrimaryInfoItem(boolean seperator) {
		return withItem(new BeanItemGridContextMenuItemBuilder<BEANTYPE>().withCaptionKey("PRIMARY_INFORMATION")
			.withIcon(FontBonprix.OK)
			.withBeanCommand((selectedItem, selectedBean) -> {
				UI.getCurrent()
					.addWindow(new GridPrimaryInfoDialog<BEANTYPE>(this.bean, this.clazz));
			})
			.withSeperator(seperator)
			.build());
	}

	private void removeContextMenuOpenListener(ContextMenuOpenListener contextMenuComponentListener) {
		removeListener(ContextMenuOpenEvent.class, contextMenuComponentListener, ContextMenuOpenListener.MENU_OPENED);
	}

	public interface BeanItemGridContextMenuOpenListener<BEANTYPE> extends EventListener, Serializable {

		public void onContextMenuOpen(BeanItemGridContextMenuOpenEvent<BEANTYPE> event);

		public static class BeanItemGridContextMenuOpenEvent<BEANTYPE> extends ContextMenuOpenEvent {
			private final BEANTYPE itemId;
			private final int rowIndex;
			private final Object propertyId;
			private final Section section;

			public BeanItemGridContextMenuOpenEvent(ContextMenu contextMenu, GridContextClickEvent contextClickEvent) {
				super(contextMenu, contextClickEvent);
				this.itemId = (BEANTYPE) contextClickEvent.getItemId();
				this.rowIndex = contextClickEvent.getRowIndex();
				this.propertyId = contextClickEvent.getPropertyId();
				this.section = contextClickEvent.getSection();
			}

			public BEANTYPE getItemId() {
				return this.itemId;
			}

			public int getRowIndex() {
				return this.rowIndex;
			}

			public Object getPropertyId() {
				return this.propertyId;
			}

			public Section getSection() {
				return this.section;
			}

			/**
			 * Use the {@link #getBeanItemGridContextMenu()
			 * getBeanItemGridContextMenu()} method.
			 */
			@Override
			@Deprecated
			public ContextMenu getContextMenu() {
				return super.getContextMenu();
			}

			public BeanItemGridContextMenuOpenEvent<BEANTYPE> getBeanItemGridContextMenu() {
				return this;
			}
		}
	}

}
