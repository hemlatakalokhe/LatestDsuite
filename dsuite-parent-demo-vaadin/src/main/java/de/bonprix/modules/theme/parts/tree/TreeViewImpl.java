package de.bonprix.modules.theme.parts.tree;

import com.vaadin.data.Container;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.modules.theme.util.ValoThemeUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = TreeViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class TreeViewImpl extends AbstractMvpView<TreePresenter> implements TreeView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "TREE";

	@Override
	protected void initializeUI() {
		VerticalLayout root = new VerticalLayout();
		root.setMargin(true);

		Label h1 = new Label("Tree");
		h1.addStyleName("h1");
		root.addComponent(h1);

		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName("wrapping");
		row.setSpacing(true);
		root.addComponent(row);

		Tree tree = new Tree();
		tree.setSelectable(true);
		tree.setMultiSelect(true);
		Container generateContainer = ValoThemeUI.generateContainer(10, true);
		tree.setContainerDataSource(generateContainer);
		tree.setDragMode(TreeDragMode.NODE);
		tree.setItemCaptionPropertyId(ValoThemeUI.CAPTION_PROPERTY);
		tree.expandItem(generateContainer.getItemIds()
			.iterator()
			.next());

		tree.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(final DragAndDropEvent event) {
				Notification.show(event.getTransferable()
					.toString());
			}
		});

		// Add actions (context menu)
		tree.addActionHandler(ValoThemeUI.getActionHandler());
		row.addComponent(tree);

		tree = new Tree();
		tree.setSelectable(true);
		tree.setMultiSelect(true);
		generateContainer = ValoThemeUI.generateContainer(10, true);
		tree.setContainerDataSource(generateContainer);
		tree.setDragMode(TreeDragMode.NODE);
		tree.setItemCaptionPropertyId(ValoThemeUI.CAPTION_PROPERTY);
		tree.setItemIconPropertyId(ValoThemeUI.ICON_PROPERTY);
		tree.expandItem(generateContainer.getItemIds()
			.iterator()
			.next());

		tree.setDropHandler(new DropHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public AcceptCriterion getAcceptCriterion() {
				return AcceptAll.get();
			}

			@Override
			public void drop(final DragAndDropEvent event) {
				Notification.show(event.getTransferable()
					.toString());
			}
		});

		// Add actions (context menu)
		tree.addActionHandler(ValoThemeUI.getActionHandler());
		row.addComponent(tree);

		setCompositionRoot(root);
	}

}
