package de.bonprix.showcase.cases.table;

import com.vaadin.data.Item;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.bean.table.BeanItemTable;
import de.bonprix.vaadin.bean.table.columngenerator.BooleanFlagColumnGenerator;
import de.bonprix.vaadin.bean.table.columngenerator.DateColumnGenerator;
import de.bonprix.vaadin.bean.table.columngenerator.PlainNumberColumnGenerator;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.ui.ComponentBar;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarComponent;

@SuppressWarnings("serial")
@SpringViewComponent
public class BeanItemTableDemo extends ShowcaseWrapper {

    protected static final String ON_FACEBOOK = "onFacebook";

    public BeanItemTableDemo() {
        super("GRID_TABLE_TREE", "BEANITEMTABLEDEMO");
    }

    @Override
    protected Component createLayout() {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        // create an empty bean table
        final BeanItemTable<Inhabitants> table = new BeanItemTable<>(Inhabitants.class);
        table.setImmediate(true);
        table.setSizeFull();
        // Fill BeanContainer with Data
        table.addAllBeans(GridDummyData.genInhabitants(1000));
        // Make the Table selectable
        table.setSelectable(true);
        // Allow the Multiselect of the Table
        table.setMultiSelect(true);
        // Hide and Show available Columns in Table. For that you find a Button
        // in the upper right corner of the Table
        table.setColumnCollapsingAllowed(true);
        // Shows Images in Table via GeneratedColumn
        table.addGeneratedColumn("image", (source, itemId, columnId) -> {
            final Item item = source.getItem(itemId);
            final String url = (String) item.getItemProperty("imageUrl")
                .getValue();

            return new Embedded("", new ExternalResource(url)) {
            };
        });
        // This are the Columns shown in the Table
        table.setVisibleColumns("id", "name", "gender", "birthday", "bodySize", BeanItemTableDemo.ON_FACEBOOK, "image");
        // How to shown in the table header
        table.setColumnHeaders(new String[] { "Id", "Vorname", "Gender", "Birthday", "Size", "onFacebook", "Image" });
        // Convert the Id to a plain number
        table.addGeneratedColumn("id", new PlainNumberColumnGenerator());
        // Convert the Date to the given Locale
        table.addGeneratedColumn("birthday", new DateColumnGenerator());
        // Converts a boolean Value to green (true) or red (false) dots
        table.addGeneratedColumn(BeanItemTableDemo.ON_FACEBOOK, new BooleanFlagColumnGenerator());

        // Adds a generated column to the Table.
        // A generated column is a column that exists only in the Table, not as
        // a property in the underlying Container. It shows up just as a regular
        // column.
        // Integrates a Button to the Table with ClickListener.
        table.addGeneratedColumn("status", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Object generateCell(final Table source, final Object itemId, final Object columnId) {
                return new Button("Details", new Button.ClickListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void buttonClick(final ClickEvent event) {
                        Notification.show("-_-");
                    }
                });
            }
        });
        // This generated column adds a ToolBar with Icons to the Table. Each
        // Icon of the ToolBar has his own ClickListener.
        table.addGeneratedColumn("manage", (source, itemId, columnId) -> {
            final ComponentBar toolbar = new ComponentBar();

            toolbar.addElement(new ComponentBarComponent(FluentUI.button()
                .icon(FontBonprix.EDIT)
                .onClick(event1 -> Notification.show("You clicked on edit!"))
                .get()).withAlignment(Alignment.MIDDLE_CENTER));

            toolbar.addElement(new ComponentBarComponent(FluentUI.button()
                .icon(FontBonprix.DELETE)
                .onClick(event2 -> Notification.show("You clicked on delete!"))
                .get()).withAlignment(Alignment.MIDDLE_CENTER));

            return toolbar;
        });

        // add a selection listener to the Table. Gets called on every selection
        // change on the table
        table.addSelectionChangeListener(event -> {
            final String text = event.getSelectedItems()
                .size() == 1 ? " row" : " rows";
            Notification.show("You selected " + event.getSelectedItems()
                .size() + text);
        });

        layout.addComponent(table);
        layout.setExpandRatio(table, 1);

        return layout;
    }

}
