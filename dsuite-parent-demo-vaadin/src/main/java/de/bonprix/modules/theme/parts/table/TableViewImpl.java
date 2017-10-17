package de.bonprix.modules.theme.parts.table;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.modules.theme.util.ValoThemeUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = TableViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class TableViewImpl extends AbstractMvpView<TablePresenter> implements TableView {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "TABLE";
	public static final String BUTTON = "button";
	public static final String TEXT_FIELD = "textfield";
	public static final String LABEL = "label";
	public static final String CHECKBOX = "checkbox";
	public static final String DATEFIELD = "datefield";
	public static final String OPTIONGROUP = "optiongroup";
	public static final String COMBOBOX = "combobox";
	public static final String SLIDER = "slider";
	public static final String PROGRESS = "progress";
	public static final String COMPACT = "compact";
	public static final String BORDERLESS = "borderless";
	public static final String ICON = "icon";
	public static final String NO_VERTICAL_LINES = "no-vertical-lines";
	public static final String NO_HEADER = "no-header";

	private final IndexedContainer normalContainer = ValoThemeUI.generateContainer(200, false);
	private final IndexedContainer gridContainer = ValoThemeUI.generateContainer(200, false);
	private final Container hierarchicalContainer = ValoThemeUI.generateContainer(200, true);

	private CheckBox hierarchical = new CheckBox("Hierarchical");
	private CheckBox footer = new CheckBox("Footer", false);
	private CheckBox verticalLines = new CheckBox("Vertical lines", true);
	private CheckBox headers = new CheckBox("Header", true);
	private CheckBox compactCheckbox = new CheckBox("Compact");
	private CheckBox componentsInCells = new CheckBox("Components in Cells", false);
	private VerticalLayout componentsLayout;

	@Override
	protected void initializeUI() {
		VerticalLayout root = new VerticalLayout();
		root.setMargin(true);

		Label h1 = new Label("Tables & Grids");
		h1.addStyleName("h1");
		root.addComponent(h1);

		Label disclaimer = new Label(
				"<p>Note that most of the toggles only affect the Table component. The Grid component supports footers, expand ratios, row indexes/captions/icons and cell renderers, but those have not been implemented here.</p>",
				ContentMode.HTML);
		disclaimer.setCaption("Toggle features/styles");
		root.addComponent(disclaimer);
		disclaimer.addStyleName(ValoTheme.LABEL_SMALL);

		HorizontalLayout wrap = new HorizontalLayout();
		wrap.addStyleName("wrapping");
		wrap.setSpacing(true);
		root.addComponent(wrap);

		wrap.addComponents(	this.hierarchical, this.footer, this.verticalLines, this.headers, this.compactCheckbox,
							this.componentsInCells);

		ValueChangeListener update = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				configure();
			}
		};

		this.hierarchical.addValueChangeListener(update);
		this.footer.addValueChangeListener(update);
		this.verticalLines.addValueChangeListener(update);
		this.headers.addValueChangeListener(update);
		this.compactCheckbox.addValueChangeListener(update);
		this.componentsInCells.addValueChangeListener(update);

		this.componentsLayout = new VerticalLayout();
		root.addComponent(this.componentsLayout);

		configure();

		setCompositionRoot(root);
	}

	private void configure() {
		this.componentsLayout.removeAllComponents();

		Table table;

		if (!this.hierarchical.getValue()) {
			table = new Table("Table component");
			table.setContainerDataSource(this.normalContainer);
		} else {
			table = new TreeTable("Tree Table component");
			table.setContainerDataSource(this.hierarchicalContainer);
		}

		table.addStyleName(DSuiteTheme.CAPTION_PRIMARY);
		table.setSelectable(true);
		table.setMultiSelect(true);
		table.setSortEnabled(true);

		table.setColumnCollapsingAllowed(true);
		table.setColumnReorderingAllowed(true);

		table.setPageLength(6);

		table.addActionHandler(ValoThemeUI.getActionHandler());
		table.setDragMode(TableDragMode.MULTIROW);
		table.setDropHandler(new DropHandler() {
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
		table.setColumnAlignment(ValoThemeUI.DESCRIPTION_PROPERTY, Align.RIGHT);
		table.setColumnAlignment(ValoThemeUI.INDEX_PROPERTY, Align.CENTER);

		table.removeContainerProperty(TableViewImpl.TEXT_FIELD);
		table.removeGeneratedColumn(TableViewImpl.TEXT_FIELD);
		table.removeContainerProperty(TableViewImpl.BUTTON);
		table.removeGeneratedColumn(TableViewImpl.BUTTON);
		table.removeContainerProperty(TableViewImpl.LABEL);
		table.removeGeneratedColumn(TableViewImpl.LABEL);
		table.removeContainerProperty(TableViewImpl.CHECKBOX);
		table.removeGeneratedColumn(TableViewImpl.CHECKBOX);
		table.removeContainerProperty(TableViewImpl.DATEFIELD);
		table.removeGeneratedColumn(TableViewImpl.DATEFIELD);
		table.removeContainerProperty(TableViewImpl.COMBOBOX);
		table.removeGeneratedColumn(TableViewImpl.COMBOBOX);
		table.removeContainerProperty(TableViewImpl.OPTIONGROUP);
		table.removeGeneratedColumn(TableViewImpl.OPTIONGROUP);
		table.removeContainerProperty(TableViewImpl.SLIDER);
		table.removeGeneratedColumn(TableViewImpl.SLIDER);
		table.removeContainerProperty(TableViewImpl.PROGRESS);
		table.removeGeneratedColumn(TableViewImpl.PROGRESS);

		if (this.componentsInCells.getValue()) {
			table.addContainerProperty(TableViewImpl.TEXT_FIELD, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.TEXT_FIELD, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					TextField tf = new TextField();
					tf.setInputPrompt("Type here…");
					tf.addStyleName(TableViewImpl.COMPACT);
					if ((Integer) itemId % 2 == 0) {
						tf.addStyleName(TableViewImpl.BORDERLESS);
					}
					return tf;
				}
			});

			table.addContainerProperty(TableViewImpl.DATEFIELD, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.DATEFIELD, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					DateField tf = new DateField();
					tf.addStyleName(TableViewImpl.COMPACT);
					if ((Integer) itemId % 2 == 0) {
						tf.addStyleName(TableViewImpl.BORDERLESS);
					}
					return tf;
				}
			});

			table.addContainerProperty(TableViewImpl.COMBOBOX, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.COMBOBOX, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					ComboBox tf = new ComboBox();
					tf.setInputPrompt("Select");
					tf.addStyleName(TableViewImpl.COMPACT);
					if ((Integer) itemId % 2 == 0) {
						tf.addStyleName(TableViewImpl.BORDERLESS);
					}
					return tf;
				}
			});

			table.addContainerProperty(TableViewImpl.BUTTON, Button.class, null);
			table.addGeneratedColumn(TableViewImpl.BUTTON, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					Button b = new Button("Button");
					b.addStyleName("small");
					return b;
				}
			});

			table.addContainerProperty(TableViewImpl.LABEL, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.LABEL, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					Label label = new Label("Label component");
					label.setSizeUndefined();
					label.addStyleName("bold");
					return label;
				}
			});

			table.addContainerProperty(TableViewImpl.CHECKBOX, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.CHECKBOX, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					return new CheckBox(null, true);
				}
			});

			table.addContainerProperty(TableViewImpl.OPTIONGROUP, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.OPTIONGROUP, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					OptionGroup op = new OptionGroup();
					op.addItem("Male");
					op.addItem("Female");
					op.addStyleName("horizontal");
					return op;
				}
			});

			table.addContainerProperty(TableViewImpl.SLIDER, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.SLIDER, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					Slider s = new Slider();
					s.setValue(30.0);
					return s;
				}
			});

			table.addContainerProperty(TableViewImpl.PROGRESS, TextField.class, null);
			table.addGeneratedColumn(TableViewImpl.PROGRESS, new ColumnGenerator() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object generateCell(final Table source, final Object itemId, final Object columnId) {
					ProgressBar bar = new ProgressBar();
					bar.setValue(0.7f);
					return bar;
				}
			});
		}

		table.setFooterVisible(this.footer.getValue());
		if (this.footer.getValue()) {
			table.setColumnFooter(ValoThemeUI.CAPTION_PROPERTY, "caption");
			table.setColumnFooter(ValoThemeUI.DESCRIPTION_PROPERTY, "description");
			table.setColumnFooter(ValoThemeUI.ICON_PROPERTY, TableViewImpl.ICON);
			table.setColumnFooter(ValoThemeUI.INDEX_PROPERTY, "index");
		}

		table.setSizeUndefined();

		Grid grid = new Grid("Grid component");

		grid.setContainerDataSource(this.gridContainer);
		grid.addStyleName(DSuiteTheme.CAPTION_PRIMARY);
		grid.setSelectionMode(SelectionMode.MULTI);
		for (Column c : grid.getColumns()) {
			if (!c.getPropertyId()
				.equals(TableViewImpl.ICON)) {
				c.setSortable(true);
			}
			c.setHidable(true);
		}

		grid.setColumnReorderingAllowed(true);

		grid.setHeightByRows(6);

		grid.setSizeUndefined();

		if (this.componentsInCells.getValue()) {
			table.setWidth("100%");
		} else {
			table.setWidth(null);
		}

		if (!this.verticalLines.getValue()) {
			table.addStyleName(TableViewImpl.NO_VERTICAL_LINES);
			grid.addStyleName(TableViewImpl.NO_VERTICAL_LINES);
		} else {
			table.removeStyleName(TableViewImpl.NO_VERTICAL_LINES);
			grid.removeStyleName(TableViewImpl.NO_VERTICAL_LINES);
		}

		if (!this.headers.getValue()) {
			table.addStyleName(TableViewImpl.NO_HEADER);
			grid.addStyleName(TableViewImpl.NO_HEADER);
		} else {
			table.removeStyleName(TableViewImpl.NO_HEADER);
			grid.removeStyleName(TableViewImpl.NO_HEADER);
		}

		if (this.compactCheckbox.getValue()) {
			table.addStyleName(TableViewImpl.COMPACT);
			grid.addStyleName(TableViewImpl.COMPACT);
		} else {
			table.removeStyleName(TableViewImpl.COMPACT);
			grid.removeStyleName(TableViewImpl.COMPACT);
		}

		this.componentsLayout.addComponent(table);
		if (!this.hierarchical.getValue()) {
			this.componentsLayout.addComponent(grid);
		}
	}

}
