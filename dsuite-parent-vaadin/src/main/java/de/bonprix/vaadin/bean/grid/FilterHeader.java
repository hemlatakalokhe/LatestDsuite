package de.bonprix.vaadin.bean.grid;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.Compare.GreaterOrEqual;
import com.vaadin.data.util.filter.Compare.LessOrEqual;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.grid.GridStaticCellType;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;
import de.bonprix.dto.NamedEntity;
import de.bonprix.vaadin.bean.field.BeanItemComboBox;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.bean.field.util.ItemCaptionGenerator;
import de.bonprix.vaadin.data.filter.AppliesToProperty;
import de.bonprix.vaadin.data.filter.CustomBeanFilter;
import de.bonprix.vaadin.data.filter.PassesFilter;
import de.bonprix.vaadin.fluentui.FluentUI;

/**
 * {@link BeanItemGrid} is able to use this {@link FilterHeader} to have
 * automatic filtering based on there values.
 */
public class FilterHeader {

	private static List<NamedEntity> filterWayObj = Arrays.asList(	new NamedEntity(1L, ">="), new NamedEntity(2L, "<="),
																	new NamedEntity(3L, "="));

	private final HeaderRow wrapped;
	private final BeanItemGrid<?> grid;

	private final Map<Object, Filter> assignedFilters = new HashMap<Object, Filter>();

	public FilterHeader(final HeaderRow wrapped, final BeanItemGrid<?> grid) {
		super();
		this.wrapped = wrapped;
		this.grid = grid;

		this.wrapped.setStyleName("bp-grid-filter-header");
	}

	/**
	 * @return the wrapped
	 */
	public HeaderRow getWrappedHeaderRow() {
		return this.wrapped;
	}

	/**
	 * Returns the cell for the given property id on this row. If the column is
	 * merged returned cell is the cell for the whole group.
	 * 
	 * @param propertyId
	 *            the property id of the column
	 * @return the cell for the given property, merged cell for merged
	 *         properties, null if not found
	 */
	public HeaderCell getCell(final Object propertyId) {
		return this.wrapped.getCell(propertyId);
	}

	/**
	 * Merges columns cells in a row
	 * 
	 * @param propertyIds
	 *            The property ids of columns to merge
	 * @return The remaining visible cell after the merge
	 */
	public HeaderCell join(final Object... propertyIds) {
		return this.wrapped.join(propertyIds);
	}

	/**
	 * Merges columns cells in a row
	 * 
	 * @param cells
	 *            The cells to merge. Must be from the same row.
	 * @return The remaining visible cell after the merge
	 */
	public HeaderCell join(final HeaderCell... cells) {
		return this.wrapped.join(cells);
	}

	/**
	 * Returns the custom style name for this row.
	 * 
	 * @return the style name or null if no style name has been set
	 */
	public String getStyleName() {
		return this.wrapped.getStyleName();
	}

	/**
	 * Sets a custom style name for this row.
	 * 
	 * @param styleName
	 *            the style name to set or null to not use any style name
	 */
	public void setStyleName(final String styleName) {
		this.wrapped.setStyleName(styleName);
	}

	/**
	 * checks assignedFilters replace already handled one and add new one
	 * 
	 * @param filter
	 *            container filter
	 * @param columnId
	 *            id of property
	 */
	public void replaceFilter(final Filter filter, final Object columnId) {
		final Filterable f = this.grid.getContainerDataSource();
		if (this.assignedFilters.containsKey(columnId)) {
			f.removeContainerFilter(this.assignedFilters.get(columnId));
		}
		f.addContainerFilter(filter);
		this.assignedFilters.put(columnId, filter);
		this.grid.cancelEditor();
	}

	/**
	 * remove the filter and notify listeners
	 * 
	 * @param columnId
	 *            id of property
	 */
	private void removeFilter(final Object columnId) {
		final Filterable f = this.grid.getContainerDataSource();
		if (this.assignedFilters.containsKey(columnId)) {
			f.removeContainerFilter(this.assignedFilters.get(columnId));
			this.assignedFilters.remove(columnId);
		}
	}

	/**
	 * Adds a string based filter to this header row for the given property.
	 * 
	 * @param propertyId
	 *            the propertyId to filter
	 * @param inputPromptKey
	 *            the I18N key of the prompt text
	 */
	public void addStringFilter(final String propertyId, final String inputPromptKey) {
		addCustomStringFilter(propertyId, inputPromptKey, null, null);
	}

	/**
	 * Adds a string based filter to this header row for the given property.
	 * 
	 * @param propertyId
	 *            the propertyId to filter
	 * @param inputPromptKey
	 *            the I18N key of the prompt text
	 */
	public <BEANTYPE> void addCustomStringFilter(final String propertyId, String inputPromptKey,
			PassesFilter<String, BEANTYPE> passesFilter, AppliesToProperty appliesToProperty) {
		final TextField filter = new TextField();
		filter.setInputPrompt(I18N.get(inputPromptKey));
		filter.setWidth("100%");
		filter.addTextChangeListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			Filter filter = null;

			@Override
			public void textChange(final TextChangeEvent event) {
				// if custom filter
				if (passesFilter != null && appliesToProperty != null) {
					final Filterable f = FilterHeader.this.grid.getContainerDataSource();

					// Remove old filter
					if (this.filter != null) {
						f.removeContainerFilter(this.filter);
					}

					// set new filter (prevent an empty-string filter)
					if (!StringUtils.isEmpty(event.getText())) {
						this.filter = new CustomBeanFilter<>(event.getText(), passesFilter, appliesToProperty);
						f.addContainerFilter(this.filter);
					}
					return;
				}

				// if regular filter
				final Filterable f = FilterHeader.this.grid.getContainerDataSource();

				// Remove old filter
				if (this.filter != null) {
					f.removeContainerFilter(this.filter);
				}

				// set new filter (prevent an empty-string filter)
				if (!StringUtils.isEmpty(event.getText())) {
					this.filter = new SimpleStringFilter(propertyId, event.getText(), true, false);
					f.addContainerFilter(this.filter);
				}
			}
		});

		getCell(propertyId).setComponent(filter);
	}

	/**
	 * Adds a date based filter to this header row for the given property.
	 * 
	 * @param propertyId
	 *            the propertyId to filter
	 */
	public void addDateFilter(final String propertyId) {
		addDateFilter(propertyId, Resolution.DAY);
	}

	/**
	 * Adds a date based filter to this header row for the given property and
	 * resolution.
	 *
	 * @param propertyId
	 *            the property id
	 * @param resolutionType
	 *            the resolution type
	 */
	public void addDateFilter(final String propertyId, Resolution resolutionType) {
		final BeanItemComboBox<NamedEntity> filterWay = new BeanItemComboBox<>(NamedEntity.class);
		filterWay.setNullSelectionAllowed(false);
		filterWay.setItemCaptionPropertyId("name");
		filterWay.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		filterWay.addAllBeans(FilterHeader.filterWayObj);
		filterWay.setValue(FilterHeader.filterWayObj.get(0));
		float filterWayWidth = 75;
		filterWay.setWidth(filterWayWidth, Unit.PIXELS);

		final DateField dateField = new DateField();
		dateField.setResolution(resolutionType);

		ValueChangeListener valueChangeHandler = new ValueChangeListener() {
			Compare filter = null;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				final Filterable f = FilterHeader.this.grid.getContainerDataSource();

				// Remove old filter
				if (this.filter != null) {
					f.removeContainerFilter(this.filter);
				}

				final Date date = dateField.getValue();
				if (date != null) {
					if (filterWay.getValue()
						.equals(FilterHeader.filterWayObj.get(0))) {
						this.filter = new GreaterOrEqual(propertyId, date);
					} else if (filterWay.getValue()
						.equals(FilterHeader.filterWayObj.get(1))) {
						this.filter = new LessOrEqual(propertyId, date);
					} else {
						this.filter = new Equal(propertyId, date);
					}

					f.addContainerFilter(this.filter);
				}
			}
		};

		filterWay.addValueChangeListener(valueChangeHandler);
		dateField.addValueChangeListener(valueChangeHandler);

		final Component c = FluentUI.horizontal()
			.add(filterWay)
			.add(dateField, 1)
			.widthFull()
			.get();

		getCell(propertyId).setComponent(c);

		float cellWidth = filterWayWidth;
		switch (resolutionType) {
		case SECOND:
		case MINUTE:
		case HOUR:
			cellWidth += 55;
		case YEAR:
		case MONTH:
		case DAY:
			cellWidth += 110;
		default:
			break;
		}

		this.grid.getColumn(propertyId)
			.setWidth(cellWidth);
	}

	/**
	 * Assigns a Combobox multiselect filter to grid for given columnId. The
	 * combobox will fill itself automatically with the possible values of the
	 * grid container.
	 * 
	 * @param columnId
	 *            id of property
	 */
	public <E> void addComboBoxFilter(final Object columnId, final String inputPrompt) {
		addComboBoxFilter(columnId, inputPrompt, null);
	}

	/**
	 * Assigns a Combobox multiselect filter to grid for given columnId. The
	 * combobox will be filled with the given list of possible values.
	 * 
	 * @param columnId
	 *            id of property
	 */
	public <E> void addComboBoxFilter(final Object columnId, final String inputPrompt, final List<E> list) {
		addCustomComboBoxFilter(columnId, inputPrompt, list, null, null);
	}

	/**
	 * Assigns a Combobox multiselect filter to grid for given columnId. The
	 * combobox will fill itself automatically with the possible values of the
	 * grid container.
	 * 
	 * @param columnId
	 *            id of property
	 */
	public <FILTERTYPE, BEANTYPE> void addCustomComboBoxFilter(final Object columnId, final String inputPrompt,
			PassesFilter<Collection<FILTERTYPE>, BEANTYPE> passesFilter, AppliesToProperty appliesToProperty) {
		addCustomComboBoxFilter(columnId, inputPrompt, null, passesFilter, appliesToProperty);
	}

	/**
	 * Assigns a custom Combobox multiselect filter to grid for given columnId.
	 * The combobox will be filled with the given list of possible values.
	 * 
	 * @param columnId
	 *            id of property
	 */
	@SuppressWarnings("unchecked")
	public <FILTERTYPE, BEANTYPE> void addCustomComboBoxFilter(final Object columnId, final String inputPrompt,
			final List<FILTERTYPE> list, PassesFilter<Collection<FILTERTYPE>, BEANTYPE> passesFilter,
			AppliesToProperty appliesToProperty) {
		final BeanItemComboBoxMultiselect<FILTERTYPE> comboBox = new BeanItemComboBoxMultiselect<>(
				(Class<FILTERTYPE>) this.grid.getContainerDataSource()
					.getType(columnId));
		comboBox.setInputPrompt(I18N.get(inputPrompt));

		Converter converter = FilterHeader.this.grid.getColumn(columnId)
			.getConverter();
		if (converter != null) {
			comboBox.setItemCaptionGenerator(new ItemCaptionGenerator<FILTERTYPE>() {
				@Override
				public String getCaption(FILTERTYPE item) {
					return (String) converter.convertToPresentation(item, String.class, null);
				}
			});
		}

		if (list != null) {
			comboBox.addAllBeans(list);
		} else {
			this.grid.getContainerDataSource()
				.addItemSetChangeListener(event -> {

					refreshFilterComboBoxItems(columnId, comboBox, event.getContainer());

				});
		}

		comboBox.setImmediate(true);
		comboBox.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 4657429154535483528L;
			CustomBeanFilter<Collection<FILTERTYPE>, BEANTYPE> filter = null;

			@Override
			public void valueChange(final ValueChangeEvent event) {
				// if custom filter
				if (passesFilter != null && appliesToProperty != null) {
					final Filterable f = FilterHeader.this.grid.getContainerDataSource();

					// Remove old filter
					if (this.filter != null) {
						f.removeContainerFilter(this.filter);
					}

					// set new filter (prevent an empty-string filter)
					if (!comboBox.getSelectedItems()
						.isEmpty()) {
						this.filter = new CustomBeanFilter<Collection<FILTERTYPE>, BEANTYPE>(
								comboBox.getSelectedItems(), passesFilter, appliesToProperty);
						f.addContainerFilter(this.filter);
					}
					return;
				}

				// if regular filter
				if (!comboBox.getSelectedItems()
					.isEmpty()) {

					final List<Filter> filterList = new ArrayList<>();

					for (final FILTERTYPE item : comboBox.getSelectedItems()) {
						filterList.add(new Equal(columnId, item));
					}

					replaceFilter(new Or(filterList.toArray(new Filter[] {})), columnId);
				} else {
					removeFilter(columnId);
				}
			}
		});

		getCell(columnId).setComponent(comboBox);
		refreshFilterComboBoxItems(columnId, comboBox, this.grid.getContainerDataSource());

	}

	@SuppressWarnings("unchecked")
	private <E> void refreshFilterComboBoxItems(final Object columnId, final BeanItemComboBoxMultiselect<E> comboBox,
			final Container container) {
		final Set<E> comboValues = new TreeSet<>();

		try {
			/*
			 * Ok, to be honest, I'm not proud about this one but it is
			 * definetly the most simple and effective solution. The problem: I
			 * want to access ALL items in a container without any filtering.
			 * The method getItemIds() returns only the visible, filtered items,
			 * which is useless in this case. There is a protected method
			 * "getAllItemIds()". Since this method does everything I want to
			 * do, I reflect this method, make it accessible and execute it. The
			 * only alternative would be subclassing the BeanItemContainer and
			 * then forcing every developer to use the subclass just for this
			 * single one feature. I've written a test which fails, when this
			 * reflection fails in later Vaadin releases so we get to know about
			 * this as soon as possible.
			 */
			final Method m = AbstractInMemoryContainer.class.getDeclaredMethod("getAllItemIds");
			m.setAccessible(true);
			final List<Object> allItemIds = (List<Object>) m.invoke(this.grid.getBeanItemContainerDataSource());
			Converter converter = this.grid.getColumn(columnId)
				.getConverter();
			Class<?> clazz = this.grid.getContainerDataSource()
				.getType(columnId);
			for (final Object itemId : allItemIds) {
				Object value = container.getContainerProperty(itemId, columnId)
					.getValue();
				// if (converter != null) {
				// value = converter.convertToPresentation(value, clazz, null);
				// }
				comboValues.add((E) value);
			}

			comboBox.replaceAllBeans(comboValues);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void clear() {
		for (Column column : this.grid.getColumns()) {
			HeaderCell cell = getCell(column.getPropertyId());
			if (cell.getCellType()
				.equals(GridStaticCellType.WIDGET)) {
				Component component = cell.getComponent();
				if (component != null && component instanceof AbstractField) {
					((AbstractField) component).clear();
				}
			}
		}

		this.grid.getBeanItemContainerDataSource()
			.removeAllContainerFilters();
	}
}