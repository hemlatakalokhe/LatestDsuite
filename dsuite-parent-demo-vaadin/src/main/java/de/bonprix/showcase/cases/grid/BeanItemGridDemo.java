package de.bonprix.showcase.cases.grid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import org.vaadin.grid.cellrenderers.editable.common.EditableRendererEnabled;
import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState;
import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState.TableSelectionMode;

import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;

import de.bonprix.common.dummydata.GridDummyData;
import de.bonprix.model.Inhabitants;
import de.bonprix.model.Inhabitants.Country;
import de.bonprix.model.enums.GridStatus;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.bean.grid.BeanItemGrid.BeanItemGridAction;
import de.bonprix.vaadin.bean.grid.FilterHeader;
import de.bonprix.vaadin.bean.grid.builder.CheckBoxRendererPropertiesBuilder;
import de.bonprix.vaadin.bean.grid.builder.ComboBoxMultiselectRendererPropertiesBuilder;
import de.bonprix.vaadin.bean.grid.builder.ComboBoxRendererPropertiesBuilder;
import de.bonprix.vaadin.bean.grid.builder.DateFieldRendererPropertiesBuilder;
import de.bonprix.vaadin.bean.grid.builder.TextFieldRendererPropertiesBuilder;
import de.bonprix.vaadin.data.converter.StringToIntegerConverter;
import de.bonprix.vaadin.bean.grid.contextmenu.BeanItemGridContextMenuItem;
import de.bonprix.vaadin.bean.grid.contextmenu.builder.BeanItemGridContextMenuItemBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * A simple Grid demo with BeanItemContainer.
 *
 * @author tmeissne
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class BeanItemGridDemo extends ShowcaseWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanItemGridDemo.class);

	protected static final String ON_FACEBOOK = "onFacebook";
	protected static final String BODY_SIZE = "bodySize";
	protected static final String BIRTHDAY = "birthday";
	protected static final String DATE_TIME = "dateTime";
	protected static final String COUNTRY = "country";
	protected static final String GENDER = "gender";
	protected static final String ENTER = "ENTER";

	private BeanItemGrid<Inhabitants> grid;
	private Label latestChangeLabel;

	// --- ItemEditListeners ---
	/**
	 * Update change lable with the column and value of the latest edit
	 */
	private EditableRenderer.ItemEditListener<String> itemEdit;

	/**
	 * Same as itemEdit, but with the date value formatted.
	 */
	private EditableRenderer.ItemEditListener<Date> dateItemEdit;

	@Resource
	private UiNotificationProvider uiNotificationProvider;

	public BeanItemGridDemo() {
		super("GRID_TABLE_TREE", "BEANITEMGRIDDEMO");
	}

	@Override
	protected Component createLayout() {
		this.latestChangeLabel = new Label("Latest change: -none-");
		this.itemEdit = new EditableRenderer.ItemEditListener<String>() {
			@Override
			public void itemEdited(EditableRenderer.ItemEditEvent<String> event) {
				BeanItemGridDemo.this.latestChangeLabel.setValue("Latest change: '" + event.getColumnPropertyId() + "' "
						+ (EditableRenderer.Mode.SINGLE.equals(event.getMode()) ? event.getNewValue()
								: event.getNewValues()));
			}
		};
		this.dateItemEdit = new EditableRenderer.ItemEditListener<Date>() {
			@Override
			public void itemEdited(EditableRenderer.ItemEditEvent<Date> event) {
				BeanItemGridDemo.this.latestChangeLabel.setValue("Latest change: '" + event.getColumnPropertyId() + "' "
						+ new SimpleDateFormat("dd-MM-yyyy").format(event.getNewValue()));
			}
		};

		genGrid();

		TextField frozenColumnTextField = FluentUI.textField()
			.value("0")
			.width(50, Unit.PIXELS)
			.heightFull()
			.converter(new StringToIntegerConverter())
			.get();

		HorizontalLayout selectionLayout = FluentUI.horizontal()
			.spacing()
			.caption("Table Selection Controls - " + TableSelectionMode.NONE.toString())
			.get();

		// Controls for testing different TableSelectionModes
		for (final TableSelectionState.TableSelectionMode t : TableSelectionState.TableSelectionMode.values()) {
			selectionLayout.addComponent(new Button(t.toString(), new Button.ClickListener() {
				@Override
				public void buttonClick(Button.ClickEvent event) {
					BeanItemGridDemo.this.grid.setSelectionMode(t);
					selectionLayout.setCaption("Table Selection Controls - " + t.toString());
				}
			}));
		}

		return FluentUI.vertical()
			.margin()
			.spacing()
			.sizeFull()
			.add(FluentUI.horizontal()
				.spacing()
				.add(FluentUI.button()
					.caption("toggle highlight editable columns")
					.onClick(event -> {
						if (BeanItemGridDemo.this.grid.getStyleName()
							.contains(DSuiteTheme.GRID_HIGHLIGHT_EDITABLE_COLUMNS)) {
							BeanItemGridDemo.this.grid.removeStyleName(DSuiteTheme.GRID_HIGHLIGHT_EDITABLE_COLUMNS);
						} else {
							BeanItemGridDemo.this.grid.addStyleName(DSuiteTheme.GRID_HIGHLIGHT_EDITABLE_COLUMNS);
						}
					})
					.get())
				.add(FluentUI.horizontal()
					.add(frozenColumnTextField)
					.add(FluentUI.button()
						.captionKey("UPDATE_FROZEN_COLUMNS")
						.onClick(event -> this.grid
							.setFrozenColumnCount((int) frozenColumnTextField.getConvertedValue()))
						.get())
					.get())
				.add(FluentUI.button()
					.caption("activate double line headers")
					.onClick(event -> {
						BeanItemGridDemo.this.grid.setColumnHeaderWordWrap(BeanItemGridDemo.BODY_SIZE, 70);
						BeanItemGridDemo.this.grid.setColumnHeaderWordWrap(BeanItemGridDemo.ON_FACEBOOK, 90);
					})
					.get())
				.get())
			.add(this.grid, 0.9f)
			.add(this.latestChangeLabel)
			.add(selectionLayout)
			.get();
	}

	// Create and set the Grid
	private void genGrid() {
		this.grid = new BeanItemGrid<>(
				new BeanItemContainer<Inhabitants>(Inhabitants.class, GridDummyData.genInhabitants(1000)));
		this.grid.setCaption("BeanItemGrid with editable columns");
		this.grid.setButtonIconRenderer("actions", new ArrayList<BeanItemGridAction<Inhabitants>>(
				Arrays.asList(
								new BeanItemGridAction<>(FontAwesome.ANCHOR,
										(Inhabitants itemId) -> this.uiNotificationProvider
											.showInfoMessageBox("anchor"),
										"anchor"),
								new BeanItemGridAction<>(FontAwesome.DOWNLOAD,
										(Inhabitants itemId) -> this.uiNotificationProvider
											.showInfoMessageBox("download"),
										"download"),
								new BeanItemGridAction<>(FontAwesome.GAMEPAD,
										(Inhabitants itemId) -> this.uiNotificationProvider
											.showInfoMessageBox("gamepad"),
										"gamepad"))));

		this.grid.setSizeFull();

		// Disable the appropriate Grid Editor
		this.grid.setEditorEnabled(false);

		// Allow column hiding
		for (Column c : this.grid.getColumns()) {
			c.setHidable(true);
		}

		TextFieldRenderer<String> textFieldRenderer = new TextFieldRenderer<>();
		textFieldRenderer.addClickListener(new RendererClickListener() {

			@Override
			public void click(RendererClickEvent event) {
				BeanItemGridDemo.LOGGER.info("clicked");
			}
		});
		textFieldRenderer.addItemEditListener(this.itemEdit);

		this.grid.setButtonTextRenderer("id", new RendererClickListener() {
			@Override
			public void click(RendererClickEvent event) {
				Inhabitants inh = (Inhabitants) event.getItemId();
				BeanItemGridDemo.this.uiNotificationProvider
					.showInfoNotification("clicked id " + inh.getId() + " with name " + inh.getName());
			}
		});
		this.grid.setTextFieldRenderer("name", new TextFieldRendererPropertiesBuilder().withItemClickListener(event -> {
			Inhabitants inhabitant = (Inhabitants) event.getItemId();
			this.uiNotificationProvider.showInfoNotification("clicked into textfield: " + inhabitant.getName());
		})
			.withItemEditListener(this.itemEdit)
			.build());
		this.grid.setDateFieldRenderer(	BeanItemGridDemo.BIRTHDAY,
										new DateFieldRendererPropertiesBuilder().withItemEditListener(this.dateItemEdit)
											.withEditableRendererEnabled(new EditableRendererEnabled<Inhabitants>() {

												@Override
												public boolean isEnabled(Inhabitants bean) {
													return bean.getName()
														.length() < 8;
												}
											})
											.build());
		this.grid.getColumn(BeanItemGridDemo.DATE_TIME)
			.setRenderer(new DateRenderer(new SimpleDateFormat("dd.MM.yyyy hh:mm:ss a")));
		this.grid.setCheckBoxRenderer(BeanItemGridDemo.ON_FACEBOOK, new CheckBoxRendererPropertiesBuilder()
			.withEditableRendererEnabled(new EditableRendererEnabled<Inhabitants>() {

				@Override
				public boolean isEnabled(Inhabitants bean) {
					return bean.getName()
						.length() < 7;
				}
			})
			.build());
		this.grid.setComboBoxRenderer(	BeanItemGridDemo.COUNTRY,
										new ComboBoxRendererPropertiesBuilder<Country>().withClazz(Country.class)
											.withOptions(GridDummyData.COUNTRIES)
											.withEditableRendererEnabled(new EditableRendererEnabled<Inhabitants>() {

												@Override
												public boolean isEnabled(Inhabitants bean) {
													return bean.getName()
														.length() < 6;
												}
											})
											.build());
		this.grid.setComboBoxMultiselectRenderer(	"visitedCountries",
													new ComboBoxMultiselectRendererPropertiesBuilder<Country>()
														.withClazz(Country.class)
														.withOptions(GridDummyData.COUNTRIES)
														.withEditableRendererEnabled(new EditableRendererEnabled<Inhabitants>() {

															@Override
															public boolean isEnabled(Inhabitants bean) {
																return bean.getName()
																	.length() < 5;
															}
														})
														.build());

		this.grid.getBeanItemGridContextMenu()
			.addBeanItemGridBodyContextMenuListener(event -> {
				Inhabitants inhabitant = event.getItemId();

				List<BeanItemGridContextMenuItem<Inhabitants>> stateItems = new ArrayList<>();
				for (GridStatus status : GridStatus.values()) {
					if (status.equals(inhabitant.getStatus())) {
						continue;
					}
					stateItems.add(new BeanItemGridContextMenuItemBuilder<Inhabitants>()
						.withCaptionKey("change to : " + status.toString())
						.withIcon(new ExternalResource(status.getIconName()))
						.withBeanCommand((selectedItem, selectedBean) -> Notification
							.show("State changed to: " + status.toString()))
						.build());
				}

				this.grid.getBeanItemGridContextMenu()
					.removeAllItems()
					.withPrimaryInfoItem(true)
					.withItem(new BeanItemGridContextMenuItemBuilder<Inhabitants>()
						.withCaptionKey("Dynamic created context")
						.withIcon(FontBonprix.EDIT)
						.withChildren(Arrays.asList(new BeanItemGridContextMenuItemBuilder<Inhabitants>()
							.withCaptionKey("Search for other people with the same name: " + event.getItemId()
								.getName())
							.withIcon(FontBonprix.PEOPLE_1)
							.withBeanCommand((selectedItem, selectedBean) -> Notification
								.show(	selectedBean.getName() + " clicked, could search now? do something special?",
										com.vaadin.ui.Notification.Type.WARNING_MESSAGE))
							.build()))
						.build())
					.withItem(new BeanItemGridContextMenuItemBuilder<Inhabitants>().withCaptionKey("State")
						.withIcon(FontBonprix.EDIT)
						.withChildren(stateItems)
						.build())
					.withItem(new BeanItemGridContextMenuItemBuilder<Inhabitants>().withCaptionKey("Checkable item")
						.withCheckable(true)
						.withChecked(inhabitant.isChecked())
						.withBeanCommand((selectedItem, selectedBean) -> inhabitant.setChecked(!inhabitant.isChecked()))
						.build());
			});

		this.grid.setColumns(	"id", BeanItemGridDemo.GENDER, "name", BeanItemGridDemo.BODY_SIZE,
								BeanItemGridDemo.BIRTHDAY, BeanItemGridDemo.DATE_TIME, BeanItemGridDemo.ON_FACEBOOK,
								BeanItemGridDemo.COUNTRY, "visitedCountries", "actions");

		initFilterHeader(this.grid);
		initFooterRow(this.grid);
		//initExtraHeaderRow(this.grid);
	}

	public void initFilterHeader(final BeanItemGrid<Inhabitants> grid) {
		FilterHeader filterHeader = grid.addFilterHeader();
		filterHeader.addStringFilter("id", BeanItemGridDemo.ENTER);
		filterHeader.addComboBoxFilter(BeanItemGridDemo.GENDER, "CHOOSE");
		filterHeader.addStringFilter("name", BeanItemGridDemo.ENTER);
		filterHeader.addStringFilter(BeanItemGridDemo.BODY_SIZE, BeanItemGridDemo.ENTER);
		filterHeader.addDateFilter(BeanItemGridDemo.BIRTHDAY);
		filterHeader.addDateFilter(BeanItemGridDemo.DATE_TIME, Resolution.SECOND);
		filterHeader.addComboBoxFilter(BeanItemGridDemo.ON_FACEBOOK, "CHOOSE");
		filterHeader.addStringFilter(BeanItemGridDemo.COUNTRY, BeanItemGridDemo.ENTER);
	}

	public void initExtraHeaderRow(final Grid grid) {
		HeaderRow fistHeaderRow = grid.prependHeaderRow();
		fistHeaderRow.join(	"id", BeanItemGridDemo.GENDER, "name", BeanItemGridDemo.BODY_SIZE, BeanItemGridDemo.BIRTHDAY,
							BeanItemGridDemo.DATE_TIME);
		fistHeaderRow.getCell("id")
			.setHtml("GridCellFilter simplify the filter settings for a grid");
		fistHeaderRow.join(BeanItemGridDemo.ON_FACEBOOK, BeanItemGridDemo.COUNTRY);
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		fistHeaderRow.getCell(BeanItemGridDemo.ON_FACEBOOK)
			.setComponent(buttonLayout);
	}

	public void initFooterRow(final BeanItemGrid<Inhabitants> grid) {
		final FooterRow footerRow = grid.appendFooterRow();
		footerRow.getCell("id")
			.setHtml("total:");
		footerRow.join(	BeanItemGridDemo.GENDER, "name", BeanItemGridDemo.BODY_SIZE, BeanItemGridDemo.BIRTHDAY,
						BeanItemGridDemo.DATE_TIME, BeanItemGridDemo.ON_FACEBOOK, BeanItemGridDemo.COUNTRY);
		// inital total count
		footerRow.getCell(BeanItemGridDemo.GENDER)
			.setHtml("<b>" + grid.getBeanItemContainerDataSource()
				.getItemIds()
				.size() + "</b>");
		// filter change count recalculate
		grid.getBeanItemContainerDataSource()
			.addItemSetChangeListener(new ItemSetChangeListener() {
				@Override
				public void containerItemSetChange(final ItemSetChangeEvent event) {
					footerRow.getCell(BeanItemGridDemo.GENDER)
						.setHtml("<b>" + event.getContainer()
							.getItemIds()
							.size() + "</b>");
				}
			});
	}
}
