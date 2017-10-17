/**
 *
 */
package de.bonprix.vaadin.bean.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.anna.gridactionrenderer.GridAction;
import org.vaadin.anna.gridactionrenderer.GridActionRenderer;
import org.vaadin.anna.gridactionrenderer.GridActionRenderer.GridActionClickEvent;
import org.vaadin.grid.cellrenderers.EditableRenderer;
import org.vaadin.grid.cellrenderers.editable.DateFieldRenderer;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;
import org.vaadin.grid.enhancements.cellrenderers.CheckBoxRenderer;
import org.vaadin.grid.enhancements.cellrenderers.ComboBoxMultiselectRenderer;
import org.vaadin.grid.enhancements.cellrenderers.ComboBoxRenderer;
import org.vaadin.grid.enhancements.navigation.GridNavigationExtension;
import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState;
import org.vaadin.teemusa.gridextensions.client.tableselection.TableSelectionState.TableSelectionMode;
import org.vaadin.teemusa.gridextensions.tableselection.TableSelectionModel;

import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.NestedMethodProperty;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontIcon;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickListener;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.renderers.Renderer;
import com.vaadin.ui.renderers.TextRenderer;

import de.bonprix.I18N;
import de.bonprix.exception.CapabilityException;
import de.bonprix.security.PrincipalSecurityContext;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.bean.field.util.ItemIconGenerator;
import de.bonprix.vaadin.bean.grid.contextmenu.BeanItemGridContextMenu;
import de.bonprix.vaadin.bean.util.BeanItemSelect;
import de.bonprix.vaadin.bean.util.BeanItemSelectUtil;
import de.bonprix.vaadin.bean.util.EnumIconProvider;
import de.bonprix.vaadin.theme.DSuiteTheme;

/**
 * The {@link BeanItemGrid} is a simple grid to display bean types based on the
 * vaadin {@link Grid}. It also provides some additional utilities to easily
 * retrieve selected items or selection event listeners.
 *
 * @author cthiel
 *
 */
public class BeanItemGrid<BEANTYPE> extends Grid implements BeanItemSelect<BEANTYPE>,
		org.vaadin.anna.gridactionrenderer.GridActionRenderer.GridActionClickListener {
	private static final long serialVersionUID = -1215095679769824462L;

	public static abstract class GridColumnGenerator<BT, COLUMNTYPE> extends PropertyValueGenerator<COLUMNTYPE> {
		private static final long serialVersionUID = 1L;

		private final Class<COLUMNTYPE> columnType;

		public GridColumnGenerator(final Class<COLUMNTYPE> columnType) {
			super();
			this.columnType = columnType;
		}

		@SuppressWarnings("unchecked")
		@Override
		public COLUMNTYPE getValue(final Item item, final Object itemId, final Object propertyId) {
			return generateBeanPropertyValue((BT) itemId, propertyId);
		}

		@Override
		public Class<COLUMNTYPE> getType() {
			return this.columnType;
		}

		public abstract COLUMNTYPE generateBeanPropertyValue(final BT itemId, final Object columnId);

	}

	private String captionKey;

	/**
	 * remember the wrapped bean item container for quicker access.
	 */
	private final BeanItemContainer<BEANTYPE> beanItemContainer;

	/**
	 * save listeners to gridActions
	 */
	private final Map<GridAction, GridActionClickListener> gridActionClickListeners = new HashMap<>();

	/**
	 * default content headerrow containing the namings of the columns
	 */
	private final HeaderRow defaultContentHeaderRow;

	/**
	 * used in combination with the ComboBoxMultiselectRenderer to fake a column
	 * to add the combobox to.
	 */
	private Map<Object, Object> fakeColumnPropertyIds = new HashMap<>();

	/**
	 * saves the selection mode, default is NONE
	 */
	private TableSelectionModel tableSelect;

	/** The context menu. */
	private BeanItemGridContextMenu<BEANTYPE> beanItemGridContextMenu;

	/**
	 * Creates the bean item table with the default item container
	 * {@link BeanItemContainer}.<br/>
	 * The container will also be wrapped in a GeneratedPropertyContainer to
	 * make it possible to add dynamic properties to the container. <br/>
	 * By default the table is in selected mode, but not in multiselect.
	 *
	 * @param type
	 *            the type of beans that will be added to the container
	 */
	public BeanItemGrid(final Class<? super BEANTYPE> type) {
		this(new BeanItemContainer<BEANTYPE>(type));
	}

	/**
	 * Creates the bean item table with the default item container
	 * {@link BeanItemContainer}.<br/>
	 * The container will also be wrapped in a GeneratedPropertyContainer to
	 * make it possible to add dynamic properties to the container. <br/>
	 * <br/>
	 * By default the table is in selected mode, but not in multiselect.
	 *
	 * @param beanItemContainer
	 *            the container
	 */
	public BeanItemGrid(final BeanItemContainer<BEANTYPE> beanItemContainer) {
		super(new GeneratedPropertyContainer(beanItemContainer));

		addStyleName(DSuiteTheme.CAPTION_PRIMARY);

		// set the default content header row before any else header
		// gets added to the grid
		this.defaultContentHeaderRow = getHeaderRow(0);

		// Extend grid with navigation extension so we can navigate form input
		// to input
		GridNavigationExtension.extend(this);

		this.beanItemContainer = beanItemContainer;

		this.tableSelect = new TableSelectionModel();
		setSelectionModel(this.tableSelect);
		this.tableSelect.setMode(TableSelectionState.TableSelectionMode.SHIFT);

		this.beanItemGridContextMenu = new BeanItemGridContextMenu<BEANTYPE>(this, (Class<? super BEANTYPE>) getType());

		addSelectionListener(event -> fireEvent(new SelectionChangeEvent<BEANTYPE>(BeanItemGrid.this,
				getSelectedItems())));

	}

	@Override
	public GeneratedPropertyContainer getContainerDataSource() {
		return (GeneratedPropertyContainer) super.getContainerDataSource();
	}

	@Override
	public void setContainerDataSource(final Indexed container) {
		if (!(container instanceof GeneratedPropertyContainer)) {
			throw new IllegalArgumentException();
		}

		super.setContainerDataSource(container);
	}

	/**
	 * Gets the {@link BeanItemContainer} used by this grid. The actual
	 * container is a {@link GeneratedPropertyContainer} wrapping the returned
	 * beanItemContainer.
	 * 
	 * @return the bean item container
	 */
	public BeanItemContainer<BEANTYPE> getBeanItemContainerDataSource() {
		return this.beanItemContainer;
	}

	/**
	 * Adds a nested container property for the container, e.g.
	 * "manager.address.street".
	 *
	 * All intermediate getters must exist and must return non-null values when
	 * the property value is accessed.
	 *
	 * @see NestedMethodProperty
	 *
	 * @param propertyId
	 * @return true if the property was added
	 */
	public boolean addNestedContainerProperty(final String propertyId) {
		return getBeanItemContainerDataSource().addNestedContainerProperty(propertyId);
	}

	/**
	 * Adds a generated property to this grid. The generated property will be
	 * added to the underlying container using the given
	 * {@link GridColumnGenerator}. <br/>
	 * The values of this column's cells will be calculated dynamically when the
	 * column is rendered.<br/>
	 * <br/>
	 * There are some limitations regarding these generated properties compared
	 * to "normal" properties:
	 * <ul>
	 * <li>a generated property cannot be used for filtering</li>
	 * <li>a generated property cannot be used for sorting</li>
	 * <li>a generated property CAN override an existing column/property. In
	 * this case the new property MUST have the same data type as the overridden
	 * property.</li>
	 * </ul>
	 * 
	 * @param propertyId
	 *            the new propertyId
	 * @param propertyType
	 *            the java type of the given property
	 * @param generator
	 *            the property generator used to calculate the value
	 */
	public <CT> void addGeneratedProperty(final String propertyId, final Class<CT> propertyType,
			final GridColumnGenerator<BEANTYPE, CT> generator) {
		this.getContainerDataSource()
			.addGeneratedProperty(propertyId, generator);
	}

	/**
	 * Sets the component's caption <code>String</code> with an {@link I18N}
	 * captionKey. Caption is the visible name of the component. This method
	 * will trigger a repaint.<br/>
	 * <br/>
	 * If the captionKey is null or empty, this value will be set to the caption
	 * directly (Note: There is a difference between a <code>null</code> caption
	 * and an empty caption string.
	 *
	 * @param captionKey
	 *            the new captionKey for the component.
	 */
	public void setCaptionKey(final String captionKey) {
		this.captionKey = captionKey;

		if (captionKey == null || captionKey.equals("")) {
			super.setCaption(captionKey);
		} else {
			super.setCaption(I18N.get(captionKey));
		}
	}

	/**
	 * Returns the current caption key.
	 * 
	 * @return the caption key
	 */
	public String getCaptionKey() {
		return this.captionKey;
	}

	/**
	 * Sets the I18N header keys of the columns.
	 *
	 * <p>
	 * The headers match the property id:s given my the set visible column
	 * headers. In the defaults mode any nulls in the headers array are replaced
	 * with id.toString() outputs when rendering.
	 * </p>
	 *
	 * @param columnHeaderKeys
	 *            the Array of column headers that match the number and order of
	 *            the visible columns.
	 */
	public void setColumnHeaderKeys(final String... columnHeaderKeys) {
		final String[] headers = new String[columnHeaderKeys.length];

		for (int i = 0; i < columnHeaderKeys.length; i++) {
			headers[i] = columnHeaderKeys[i] == null || columnHeaderKeys[i].equals("") ? ""
					: I18N.get(columnHeaderKeys[i]);
		}

		int i = 0;
		for (final Column c : getColumns()) {
			c.setHeaderCaption(columnHeaderKeys[i] == null || columnHeaderKeys[i].equals("") ? ""
					: I18N.get(columnHeaderKeys[i]));
			i++;
		}
	}

	/**
	 * Makes the column header with the column name be double as twice the size
	 * and break the column given by propertyId
	 * 
	 * @param propertyId
	 *            propertyId of column
	 * @param width
	 *            width in pixel
	 */
	public void setColumnHeaderWordWrap(Object propertyId, double width) {
		addStyleName("bp-grid-header-double-line");
		this.defaultContentHeaderRow.setStyleName("bp-grid-header-double-line");
		this.defaultContentHeaderRow.getCell(propertyId)
			.setStyleName("bp-grid-header-break");
		getColumn(propertyId).setWidth(width);
	}

	/**
	 * Sets the width of the specified column to the given width in pixels.
	 * 
	 * @param propertyId
	 *            the column
	 * @param width
	 *            the with in pixels
	 */
	public void setColumnWidth(final String propertyId, final int width) {
		getColumn(propertyId).setWidth(width);
	}

	/**
	 * Sets the expand ratio of the specified column to the given width in
	 * pixels.
	 * 
	 * @param propertyId
	 *            the column
	 * @param expandRatio
	 *            the expand ratio
	 */
	public void setColumnExpandRatio(final String propertyId, final int expandRatio) {
		getColumn(propertyId).setExpandRatio(expandRatio);
	}

	/**
	 * Sets the cell renderer of the given column.
	 * 
	 * @param propertyId
	 *            the column
	 * @param renderer
	 *            the renderer
	 */
	public void setRenderer(final String propertyId, final Renderer<?> renderer) {
		if (getColumn(propertyId) == null) {
			addColumn(propertyId);
		}
		final Column c = getColumn(propertyId);

		c.setRenderer(renderer);
	}

	/**
	 * Sets the renderer for the given column and also adds a converter to
	 * preprocess the cell value before rendering
	 * 
	 * @param propertyId
	 *            the column
	 * @param renderer
	 *            the renderer
	 * @param converter
	 *            the converter
	 */
	public <T> void setRenderer(final String propertyId, final Renderer<T> renderer, final Converter<T, ?> converter) {
		if (getColumn(propertyId) == null) {
			addColumn(propertyId);
		}
		final Column c = getColumn(propertyId);

		c.setRenderer(renderer, converter);
	}

	/**
	 * Sets a special icon renderer for the given column which will render an
	 * enum to an icon.
	 * 
	 * @param propertyId
	 *            the column
	 * @param iconProviderClass
	 *            the icon provider for this enum
	 */
	public <E extends Enum<?>> void setIconRenderer(final String propertyId,
			final EnumIconProvider<E> iconProviderClass) {
		setRenderer(propertyId, new ImageRenderer(), new EnumIconConverter<>(iconProviderClass));
	}

	/**
	 * Adds a textfield renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 */
	public void setTextFieldRenderer(final String propertyId) {
		setTextFieldRenderer(propertyId, new TextFieldRendererProperties());
	}

	/**
	 * Adds a textfield renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created. The properties define some special listeners that maybe should
	 * be registered.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param properties
	 *            the properties
	 * 
	 */
	public void setTextFieldRenderer(final String propertyId, TextFieldRendererProperties properties) {
		final Column c = createAndGetColumn(propertyId);

		TextFieldRenderer<String> textFieldRenderer = new TextFieldRenderer<String>(
				properties.getEditableRendererEnabled());
		setEditableRendererListeners(textFieldRenderer, properties);

		c.setRenderer(textFieldRenderer);
	}

	/**
	 * Adds a text renderer column i.e. vaadin's default text renderer.
	 * 
	 * @param propertyId
	 * @param properties
	 */
	public void setTextRenderer(final String propertyId, TextRendererProperties properties) {
		final Column c = createAndGetColumn(propertyId);

		TextRenderer textRenderer = new TextRenderer();
		c.setRenderer(textRenderer);
	}

	private void setEditableRendererListeners(EditableRenderer editableRenderer, RendererProperties properties) {
		if (properties.getItemClickListener() != null) {
			editableRenderer.addClickListener(properties.getItemClickListener());
		}
		if (properties.getItemEditListener() != null) {
			editableRenderer.addItemEditListener(properties.getItemEditListener());
		}
	}

	/**
	 * Adds a datefield renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 */
	public void setDateFieldRenderer(final String propertyId) {
		setDateFieldRenderer(propertyId, new DateFieldRendererProperties());
	}

	/**
	 * Adds a datefield renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created. The properties define some special listeners that maybe should
	 * be registered.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param properties
	 *            the properties
	 */
	public void setDateFieldRenderer(final String propertyId, DateFieldRendererProperties properties) {
		final Column c = createAndGetColumn(propertyId);

		DateFieldRenderer dateFieldRenderer = new DateFieldRenderer(properties.getEditableRendererEnabled());
		setEditableRendererListeners(dateFieldRenderer, properties);

		c.setRenderer(dateFieldRenderer);
	}

	/**
	 * Adds a checkbox renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 */
	public void setCheckBoxRenderer(final String propertyId) {
		setCheckBoxRenderer(propertyId, new CheckBoxRendererProperties());
	}

	/**
	 * Adds a checkbox renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 */
	public void setCheckBoxRenderer(final String propertyId, CheckBoxRendererProperties properties) {
		final Column c = createAndGetColumn(propertyId);

		CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer(properties.getEditableRendererEnabled());
		setEditableRendererListeners(checkBoxRenderer, properties);

		c.setRenderer(checkBoxRenderer);
	}

	/**
	 * Adds a button renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param beanItemGridActions
	 *            list of actions to add to the column
	 */
	public void setButtonIconRenderer(final String propertyId,
			final List<BeanItemGridAction<BEANTYPE>> beanItemGridActions) {
		// TODO capabilities, show only first and third button default would be
		// "1,3" of three buttons
		final Column c = createAndGetColumn(propertyId, "-1");

		List<GridAction> actions = new ArrayList<>();

		for (BeanItemGridAction<BEANTYPE> beanItemGridAction : beanItemGridActions) {
			actions.add(createGridAction(	beanItemGridAction.getListener(), beanItemGridAction.getDescription(),
											beanItemGridAction.getIcon()));
		}

		c.setRenderer(createGridActionRenderer(actions));
	}

	/**
	 * Adds a button renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param icon
	 *            icon of the button
	 * @param description
	 *            mouseover text of the button
	 * @param listener
	 *            the click listener for this button
	 */
	public void setButtonIconRenderer(final String propertyId, Resource icon, final String description,
			final GridActionClickListener<BEANTYPE> listener) {
		final Column c = createAndGetColumn(propertyId, "-1");

		List<GridAction> actions = new ArrayList<>(Arrays.asList(createGridAction(listener, description, icon)));

		c.setRenderer(createGridActionRenderer(actions));
	}

	/**
	 * Adds a button renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param listener
	 *            the click listener for this button
	 */
	public void setButtonTextRenderer(final String propertyId, final RendererClickListener listener) {
		setButtonTextRenderer(propertyId, listener, "CLICK");
	}

	/**
	 * Adds a button renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param listener
	 *            the click listener for this button
	 * @param nullRepresentaionKey
	 *            the caption of the button when value is null
	 * @param objects
	 *            optional objects for captionKey
	 */
	public void setButtonTextRenderer(final String propertyId, final RendererClickListener listener,
			final String nullRepresentaionKey, Object... objects) {
		final Column c = createAndGetColumn(propertyId);

		c.setRenderer(new ButtonRenderer(listener, I18N.get(nullRepresentaionKey, objects)));
	}

	/**
	 * Adds a combo box renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param properties
	 *            the properties for the comboboxrenderer
	 */
	public <PROPERTYBEAN> void setComboBoxRenderer(final String propertyId,
			ComboBoxRendererProperties<PROPERTYBEAN> properties) {
		final Column c = createAndGetColumn(propertyId);

		ComboBoxRenderer<PROPERTYBEAN> comboBoxRenderer = new ComboBoxRenderer<PROPERTYBEAN>(properties.getClazz(),
				properties.getOptions(), properties.getItemIdPropertyId(), properties.getItemCaptionPropertyId(),
				properties.getPageSize(), I18N.get(properties.getInputPromptKey()), properties.isNullSelectionAllowed(),
				properties.getEditableRendererEnabled());
		setEditableRendererListeners(comboBoxRenderer, properties);

		c.setRenderer(comboBoxRenderer);
	}

	/**
	 * Adds a button renderer column to this grid.<br/>
	 * <br/>
	 * If the given propertyId is unknown to the grid, a new column will be
	 * created.
	 * 
	 * @param propertyId
	 *            the columnId
	 * @param properties
	 *            the properties
	 */
	public <PROPERTYTYPE> void setComboBoxMultiselectRenderer(final String propertyId,
			ComboBoxMultiselectRendererProperties<PROPERTYTYPE> properties) {
		Column column = createAndGetColumn(propertyId);

		String fakeColumnPropertyId = "_" + propertyId;

		((GeneratedPropertyContainer) getContainerDataSource())
			.addGeneratedProperty(fakeColumnPropertyId, new PropertyValueGenerator<PROPERTYTYPE>() {
				private static final long serialVersionUID = 1L;

				@Override
				public PROPERTYTYPE getValue(Item item, Object itemId, Object propertyId) {
					return null;
				}

				@Override
				public Class<PROPERTYTYPE> getType() {
					return properties.getClazz();
				}
			});
		Column fakeColumn = createAndGetColumn(fakeColumnPropertyId);
		fakeColumn.setHeaderCaption(column.getHeaderCaption());
		ComboBoxMultiselectRenderer comboBoxMultiselectRenderer = new ComboBoxMultiselectRenderer<PROPERTYTYPE>(
				properties.getClazz(), properties.getOptions(), properties.getItemIdPropertyId(),
				properties.getItemCaptionPropertyId(), properties.getPageSize(),
				I18N.get(properties.getInputPromptKey()), I18N.get(properties.getSelectAllTextKey()),
				I18N.get(properties.getDeselectAllTextKey()), propertyId, properties.getEditableRendererEnabled());

		fakeColumn.setRenderer(comboBoxMultiselectRenderer);

		this.fakeColumnPropertyIds.put(propertyId, fakeColumnPropertyId);

		setColumns(getColumnPropertyIds());
	}

	private Object[] getColumnPropertyIds() {
		int size = getColumns().size();
		Object[] propertyIds = new Object[getColumns().size()];
		List<Column> columns = getColumns();
		for (int i = 0; i < size; i++) {
			propertyIds[i] = columns.get(i)
				.getPropertyId();
		}
		return propertyIds;
	}

	@Override
	public void setColumns(Object... propertyIds) {
		super.setColumns(replacePropertyIdsWithFakedPropertyIds(propertyIds));
	}

	@Override
	public void setColumnOrder(Object... propertyIds) {
		super.setColumnOrder(replacePropertyIdsWithFakedPropertyIds(propertyIds));
	}

	private Object[] replacePropertyIdsWithFakedPropertyIds(Object... propertyIds) {

		List<Object> replacedPropertyIds = new ArrayList<>();
		for (Object propertyId : propertyIds) {
			if (this.fakeColumnPropertyIds.containsKey(propertyId)) {
				replacedPropertyIds.add(this.fakeColumnPropertyIds.get(propertyId));
				continue;
			}
			if (replacedPropertyIds.contains(propertyId)) {
				continue;
			}
			replacedPropertyIds.add(propertyId);
		}
		return replacedPropertyIds.toArray();
	}

	/**
	 * creates a column if it doesnt exist yet and gives it back default value:
	 * null
	 * 
	 * @param propertyId
	 *            propertyId of the (new) column
	 * @return Column
	 */
	public Column createAndGetColumn(String propertyId) {
		return createAndGetColumn(propertyId, null);
	}

	/**
	 * creates a column if it doesnt exist yet and gives it back
	 * 
	 * @param propertyId
	 *            propertyId of the (new) column
	 * @return Column
	 */
	public Column createAndGetColumn(String propertyId, String defaultValue) {
		if (getColumn(propertyId) == null) {
			try {
				addColumn(propertyId);
			} catch (final IllegalStateException e) {
				// add empty column
				addGeneratedProperty(propertyId, String.class, new GridColumnGenerator<BEANTYPE, String>(String.class) {

					private static final long serialVersionUID = 1L;

					@Override
					public String generateBeanPropertyValue(final BEANTYPE itemId, final Object columnId) {
						return defaultValue;
					}
				});
				if (getColumn(propertyId) == null) {
					addColumn(propertyId);
				}
			}
		}
		return getColumn(propertyId);
	}

	/**
	 * creates a grid action render with a list of actions
	 * 
	 * @param actions
	 * @return GridActionRenderer
	 */
	private GridActionRenderer createGridActionRenderer(List<GridAction> actions) {
		GridActionRenderer renderer = new GridActionRenderer(actions);
		renderer.addActionClickListener(this);
		return renderer;
	}

	/**
	 * creates a gridaction and registers it for the grid
	 * 
	 * @param listener
	 *            listener called when button is clicked
	 * @param description
	 *            mouse over text
	 * @param icon
	 *            icon of the button
	 * @return GridAction
	 */
	private GridAction createGridAction(final GridActionClickListener listener, final String description,
			Resource icon) {
		GridAction gridAction = new GridAction(icon, description);
		this.gridActionClickListeners.put(gridAction, listener);
		return gridAction;
	}

	@Override
	public void attach() {
		// workaround for having double line header column names
		addStyleName("header-count-" + getHeaderRowCount());
		super.attach();
	}

	/**
	 * Creates and returns a new header for the table with special abilities for
	 * filter fields
	 * 
	 * @return the filter header
	 */
	public FilterHeader addFilterHeader() {
		return new FilterHeader(appendHeaderRow(), this);
	}

	/**
	 * Removes filter header
	 */
	public void removeFilterHeader(FilterHeader filterHeader) {
		removeHeaderRow(filterHeader.getWrappedHeaderRow());
	}

	@Override
	public void addBean(final BEANTYPE bean) {
		getBeanItemContainerDataSource().addBean(bean);
	}

	@Override
	public void addAllBeans(final Collection<BEANTYPE> beanCollection) {
		getBeanItemContainerDataSource().addAll(beanCollection);
	}

	@Override
	public void replaceAllBeans(final Collection<BEANTYPE> beans) {
		getContainerDataSource().removeAllItems();
		deselectAll();
		addAllBeans(beans);
	}

	@Override
	public void replaceBean(BEANTYPE bean) {
		BEANTYPE itemId = getBeanItemContainerDataSource().getBeanIdResolver()
			.getIdForBean(bean);
		int indexOfOldBean = getBeanItemContainerDataSource().indexOfId(itemId);
		removeBean(bean);
		getContainerDataSource().addItemAt(indexOfOldBean, bean);
	}

	@Override
	public void replaceBeans(final Collection<BEANTYPE> beans) {
		for (BEANTYPE bean : beans) {
			replaceBean(bean);
		}
	}

	@Override
	public List<BEANTYPE> getAllBeans() {
		return getBeanItemContainerDataSource().getItemIds();
	}

	@Override
	public boolean removeBean(final BEANTYPE bean) {
		deselect(bean);
		return getContainerDataSource().removeItem(bean);
	}

	@Override
	public void addSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener) {
		addListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void removeSelectionChangeListener(final SelectionChangeListener<BEANTYPE> listener) {
		removeListener(SelectionChangeEvent.class, listener, BeanItemSelectUtil.SELECTION_CHANGE_METHOD);
	}

	@Override
	public void selectAll() {
		setValue(getContainerDataSource().getItemIds());
	}

	@Override
	public void unselectAll() {
		setValue(null);
	}

	@Override
	public BEANTYPE getSelectedItem() {
		final Collection<BEANTYPE> items = getSelectedItems();

		if (items.isEmpty()) {
			return null;
		}

		return items.iterator()
			.next();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BEANTYPE> getSelectedItems() {
		final Collection<BEANTYPE> items = new ArrayList<>();

		for (final Object itemId : getSelectedRows()) {
			items.add((BEANTYPE) itemId);
		}

		return items;
	}

	@Override
	public Object getValue() {
		return getSelectedItems();
	}

	@Override
	public void setValue(final Object newValue) throws com.vaadin.data.Property.ReadOnlyException {
		if (newValue instanceof Collection) {
			for (final Object itemId : ((Collection<?>) newValue)) {
				select(itemId);
			}
		} else if (getType().isInstance(newValue)) {
			select(newValue);
		}
	}

	@Override
	public Class<? extends Object> getType() {
		return getBeanItemContainerDataSource().getBeanType();
	}

	@Override
	public void sort(final Object[] propertyId, final boolean[] ascending) {
		getContainerDataSource().sort(propertyId, ascending);
	}

	@Override
	public void click(GridActionClickEvent event) {
		this.gridActionClickListeners.get(event.getAction())
			.clicked(event.getItemId());
	}

	public interface GridActionClickListener<BEANTYPE> {
		void clicked(BEANTYPE object);
	}

	public static class BeanItemGridAction<BEANTYPE> {
		private final FontIcon icon;
		private final String description;
		private final GridActionClickListener<BEANTYPE> listener;

		/**
		 * Use
		 * {@link BeanItemGridAction#BeanItemGridAction(FontIcon, GridActionClickListener, String, Object...)}
		 * 
		 * @param icon
		 * @param description
		 * @param listener
		 */
		@Deprecated
		public BeanItemGridAction(FontIcon icon, String description, GridActionClickListener<BEANTYPE> listener) {
			this.icon = icon;
			this.description = description;
			this.listener = listener;
		}

		public BeanItemGridAction(FontIcon icon, GridActionClickListener<BEANTYPE> listener, String descriptionKey,
				Object... objects) {
			this.icon = icon;
			this.description = I18N.get(descriptionKey, objects);
			this.listener = listener;
		}

		public FontIcon getIcon() {
			return this.icon;
		}

		public String getDescription() {
			return this.description;
		}

		public GridActionClickListener<BEANTYPE> getListener() {
			return this.listener;
		}
	}

	@Override
	public void setItemCaptionGenerator(ItemCaptionGenerator<BEANTYPE> itemCaptionGenerator) {
		// TODO
	}

	@Override
	public void setItemIconGenerator(ItemIconGenerator<BEANTYPE> itemIconGenerator) {
		// TODO

	}

	/**
	 * return of singleton object of GridContextMenu.
	 *
	 * @return the context menu
	 */
	public BeanItemGridContextMenu<BEANTYPE> getBeanItemGridContextMenu() {
		return this.beanItemGridContextMenu;
	}

	/**
	 * Please use {@link #setSelectionMode(TableSelectionMode) setSelectionMode}
	 */
	@Override
	@Deprecated
	public SelectionModel setSelectionMode(SelectionMode selectionMode) throws IllegalArgumentException {
		return super.setSelectionMode(selectionMode);
	}

	/**
	 * Please use {@link #setSelectionMode(TableSelectionMode) setSelectionMode}
	 * , selectionModel will be set already in the constructor
	 */
	@Override
	@Deprecated
	public void setSelectionModel(SelectionModel selectionModel) throws IllegalArgumentException {
		super.setSelectionModel(selectionModel);
	}

	public void setSelectionMode(TableSelectionMode mode) {
		this.tableSelect.setMode(mode);
	}

	/**
	 * Set type compatible read/edit renderers on column otherwise it throws
	 * IllegalArgumentException
	 * 
	 * @param propertyId
	 *            - respective column's property in container.
	 * @param capabilityKey
	 * @param readOnlyRendererProperties
	 * @param editableRendererProperties
	 */
	public void setColumnCapability(final String propertyId, final String capabilityKey,
			final RendererProperties<?> readOnlyRendererProperties,
			final RendererProperties<?> editableRendererProperties) {
		if (getColumn(propertyId) == null || StringUtils.isEmpty(capabilityKey)) {
			throw new CapabilityException(capabilityKey, propertyId);
		}

		Column column = getColumn(propertyId);

		if (PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.NONE)) {
			column.setHidden(true);
			return;
		}

		if (!PrincipalSecurityContext.hasPermission(capabilityKey, PermissionType.EDIT)) {
			setRendererProperties(propertyId, readOnlyRendererProperties);
			return;
		}
		setRendererProperties(propertyId, editableRendererProperties);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setRendererProperties(String propertyId, RendererProperties<?> rendererProperties) {
		if (rendererProperties == null) {
			throw new CapabilityException(rendererProperties, propertyId);
		}
		if (rendererProperties instanceof TextFieldRendererProperties) {
			this.setTextFieldRenderer(propertyId, (TextFieldRendererProperties) rendererProperties);
			return;
		}
		if (rendererProperties instanceof DateFieldRendererProperties) {
			this.setDateFieldRenderer(propertyId, (DateFieldRendererProperties) rendererProperties);
			return;
		}
		if (rendererProperties instanceof CheckBoxRendererProperties) {
			this.setCheckBoxRenderer(propertyId, (CheckBoxRendererProperties) rendererProperties);
			return;
		}
		if (rendererProperties instanceof ComboBoxRendererProperties) {
			this.setComboBoxRenderer(propertyId, (ComboBoxRendererProperties) rendererProperties);
			return;
		}
		if (rendererProperties instanceof ComboBoxMultiselectRendererProperties) {
			this.setComboBoxMultiselectRenderer(propertyId, (ComboBoxMultiselectRendererProperties) rendererProperties);
			return;
		}
		if (rendererProperties instanceof TextRendererProperties) {
			this.setTextRenderer(propertyId, (TextRendererProperties) rendererProperties);
		}
	}

	/**
	 * Rotates the header-content of the grid in vertical orientation. The
	 * header content is rotated by 90 degrees then
	 * 
	 * @param width
	 * @param height
	 */
	public void rotateHeaderContent(int height, int width) {
		String uuid = UUID.randomUUID()
			.toString();
		addStyleName(uuid);

		Page.getCurrent()
			.getStyles()
			.add("." + uuid + " th.v-grid-cell { height: " + height + "px; width: + " + width + "px;}");

		this.defaultContentHeaderRow.setStyleName("bp-grid-rotate-header-content");
		this.setCellStyleGenerator(cellRef -> "bp-grid-col-width-rotate-header-content");
	}

}