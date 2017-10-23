package de.bonprix.modules.theme.util;

import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.Resource;
import com.vaadin.ui.Notification;

public class ValoThemeUI {

    public static final String CAPTION_PROPERTY = "caption";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String ICON_PROPERTY = "icon";
    public static final String INDEX_PROPERTY = "index";

    @SuppressWarnings("serial")
    static Handler actionHandler = new Handler() {
        private final Action actionOne = new Action("Action One");
        private final Action actionTwo = new Action("Action Two");
        private final Action actionThree = new Action("Action Three");
        private final Action[] actions = new Action[] { this.actionOne, this.actionTwo, this.actionThree };

        @Override
        public void handleAction(final Action action, final Object sender, final Object target) {
            Notification.show(action.getCaption());
        }

        @Override
        public Action[] getActions(final Object target, final Object sender) {
            return this.actions;
        }
    };

    private ValoThemeUI() {

    }

    public static Handler getActionHandler() {
        return ValoThemeUI.actionHandler;
    }

    @SuppressWarnings("unchecked")
    public static IndexedContainer generateContainer(final int size, final boolean hierarchical) {
        final TestIcon testIcon = new TestIcon(90);
        final IndexedContainer container = hierarchical ? new HierarchicalContainer() : new IndexedContainer();
        final StringGenerator sg = new StringGenerator();
        container.addContainerProperty(ValoThemeUI.CAPTION_PROPERTY, String.class, null);
        container.addContainerProperty(ValoThemeUI.ICON_PROPERTY, Resource.class, null);
        container.addContainerProperty(ValoThemeUI.INDEX_PROPERTY, Integer.class, null);
        container.addContainerProperty(ValoThemeUI.DESCRIPTION_PROPERTY, String.class, null);
        for (int i = 1; i < size + 1; i++) {
            final Item item = container.addItem(i);
            item.getItemProperty(ValoThemeUI.CAPTION_PROPERTY)
                .setValue(sg.nextString(true) + " " + sg.nextString(false));
            item.getItemProperty(ValoThemeUI.INDEX_PROPERTY)
                .setValue(i);
            item.getItemProperty(ValoThemeUI.DESCRIPTION_PROPERTY)
                .setValue(sg.nextString(true) + " " + sg.nextString(false) + " " + sg.nextString(false));
            item.getItemProperty(ValoThemeUI.ICON_PROPERTY)
                .setValue(testIcon.get());
        }
        container.getItem(container.getIdByIndex(0))
            .getItemProperty(ValoThemeUI.ICON_PROPERTY)
            .setValue(testIcon.get());

        if (hierarchical) {
            for (int i = 1; i < size + 1; i++) {
                for (int j = 1; j < 5; j++) {
                    final String id = i + " -> " + j;
                    Item child = container.addItem(id);
                    child.getItemProperty(ValoThemeUI.CAPTION_PROPERTY)
                        .setValue(sg.nextString(true) + " " + sg.nextString(false));
                    child.getItemProperty(ValoThemeUI.ICON_PROPERTY)
                        .setValue(testIcon.get());
                    ((Hierarchical) container).setParent(id, i);

                    for (int k = 1; k < 6; k++) {
                        final String id2 = id + " -> " + k;
                        child = container.addItem(id2);
                        child.getItemProperty(ValoThemeUI.CAPTION_PROPERTY)
                            .setValue(sg.nextString(true) + " " + sg.nextString(false));
                        child.getItemProperty(ValoThemeUI.ICON_PROPERTY)
                            .setValue(testIcon.get());
                        ((Hierarchical) container).setParent(id2, id);

                        for (int l = 1; l < 5; l++) {
                            final String id3 = id2 + " -> " + l;
                            child = container.addItem(id3);
                            child.getItemProperty(ValoThemeUI.CAPTION_PROPERTY)
                                .setValue(sg.nextString(true) + " " + sg.nextString(false));
                            child.getItemProperty(ValoThemeUI.ICON_PROPERTY)
                                .setValue(testIcon.get());
                            ((Hierarchical) container).setParent(id3, id2);
                        }
                    }
                }
            }
        }
        return container;
    }
}
