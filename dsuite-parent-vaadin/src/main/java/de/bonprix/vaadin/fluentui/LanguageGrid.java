package de.bonprix.vaadin.fluentui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.grid.cellrenderers.editable.TextFieldRenderer;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid.Column;

import de.bonprix.I18N;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.dto.masterdata.SimpleLanguage;
import de.bonprix.dto.masterdata.SimpleLanguageLanguage;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.bean.grid.BeanItemGrid.GridColumnGenerator;
import de.bonprix.vaadin.bean.grid.FilterHeader;
import de.bonprix.vaadin.data.filter.AppliesToProperty;
import de.bonprix.vaadin.data.filter.PassesFilter;

/**
 * Provides fluent API for creating i18n grid for translations
 * 
 * @author d.kolev
 *
 */
public class LanguageGrid<CONTAINER extends I18NLanguageContainer<ELEMENT>, ELEMENT extends I18NLanguageElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageGrid.class);

    private static final String INVALID_PROPERTY_MSG = "Parameter cannot be null or empty!";

    private static final String LANGUAGE_PROPERTY = "languageName";

    private static final String LANGUAGE_PROPERTY_NAME_I18N_KEY = "LANGUAGE";

    private String languageNameKey = LANGUAGE_PROPERTY_NAME_I18N_KEY;

    private boolean hasFilter = false;

    private String filterPromptTextKey;

    private List<String> propertiesList = new ArrayList<>();

    private List<String> i18nKeysList = new ArrayList<>();

    private Float gridWidth;

    private Unit gridWidthUnit;

    private Float gridHeight;

    private Unit gridHeightUnit;

    private int[] expandRatios;

    private Class<ELEMENT> propertyClazz;

    private BeanFieldGroup<CONTAINER> fieldGroup;

    /**
     * Constructor
     * 
     * @param propertyClazz
     * @param fieldGroup
     */
    protected LanguageGrid(Class<ELEMENT> propertyClazz, final BeanFieldGroup<CONTAINER> fieldGroup) {
        if (propertyClazz == null || fieldGroup == null) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }
        this.propertyClazz = propertyClazz;
        this.fieldGroup = fieldGroup;
    }

    /**
     * Sets the i18n key for the language column
     * 
     * @param languageNameKeyArg
     * @return
     */
    public LanguageGrid<CONTAINER, ELEMENT> languageNameKey(String languageNameKeyArg) {
        if (languageNameKeyArg == null) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }
        this.languageNameKey = languageNameKeyArg;
        return this;
    }

    /**
     * 
     * @param propertyArg
     */
    public LanguageGrid<CONTAINER, ELEMENT> bind(String propertyArg, String i18nKeyArg) {
        if (propertyArg == null || i18nKeyArg == null) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }
        propertiesList.add(propertyArg);
        i18nKeysList.add(i18nKeyArg);
        return this;
    }

    /**
     * Set column's filter
     * 
     * @param hasFilterArg - flag shows whether filter should be displayed
     * @param filterPromptTextArg - the text to be shown when filter is empty
     * @return
     */
    public LanguageGrid<CONTAINER, ELEMENT> filter(boolean hasFilterArg, String filterPromptTextKeyArg) {
        this.hasFilter = hasFilterArg;
        this.filterPromptTextKey = filterPromptTextKeyArg;
        return this;
    }

    /**
     * Sets width of the grid
     * 
     * @param width
     * @param unit
     * @return
     */
    public LanguageGrid<CONTAINER, ELEMENT> width(Float width, Unit unit) {
        if (width == null || unit == null) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }

        this.gridWidth = width;
        this.gridWidthUnit = unit;
        return this;
    }

    /**
     * Sets expand ratios of the grid
     * 
     * @param expandRatios
     * @return
     */
    public LanguageGrid<CONTAINER, ELEMENT> expandRatios(int... expandRatios) {
        if (expandRatios == null || expandRatios.length == 0) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }
        this.expandRatios = expandRatios;
        return this;
    }

    /**
     * Sets height of the Grid
     * 
     * @param height
     * @param unit
     * @return
     */
    public LanguageGrid<CONTAINER, ELEMENT> height(Float height, Unit unit) {
        if (height == null || unit == null) {
            throw new IllegalArgumentException(INVALID_PROPERTY_MSG);
        }
        this.gridHeight = height;
        this.gridHeightUnit = unit;
        return this;
    }

    private void createFilter(final BeanItemGrid<ELEMENT> languageGridArg) {
        FilterHeader filterHeader = languageGridArg.addFilterHeader();

        filterHeader.addCustomStringFilter(LANGUAGE_PROPERTY, filterPromptTextKey, new PassesFilter<String, ELEMENT>() {

            @Override
            public boolean passesFilter(String filterString, ELEMENT bean) {
                String value = (String) languageGridArg.getContainerDataSource()
                        .getItem(bean)
                        .getItemProperty(LANGUAGE_PROPERTY)
                        .getValue();

                if (value == null) {
                    return false;
                }
                return value.toLowerCase()
                        .contains(filterString.toLowerCase()) || filterString.isEmpty();
            }
        }, new AppliesToProperty() {

            @Override
            public boolean appliesToProperty(Object propertyId) {
                return ((String) propertyId).contains(LANGUAGE_PROPERTY);
            }
        });

        List<String> filterList = new ArrayList<>(propertiesList);

        // Remove language generated property from the filter
        filterList.remove(LANGUAGE_PROPERTY);

        for (String property : filterList) {
            filterHeader.addStringFilter(property, this.filterPromptTextKey);
        }
    }

    /**
     * Builds and returns the Language grid component
     * 
     * @return
     */
    public BeanItemGrid<ELEMENT> get() {

        BeanItemGrid<ELEMENT> languageGrid = new BeanItemGrid<>(propertyClazz);
        I18NLanguageContainer<ELEMENT> containerBean = fieldGroup.getItemDataSource()
                .getBean();

        if (containerBean != null) {
            addMissingLanguages(containerBean);
        }

        languageGrid.addGeneratedProperty(LANGUAGE_PROPERTY, String.class, new GridColumnGenerator<ELEMENT, String>(String.class) {

            @Override
            public String generateBeanPropertyValue(ELEMENT itemId, Object columnId) {
                I18NLanguageElement languageElement = (I18NLanguageElement) itemId;
                SimpleLanguage language = LanguageGrid.this.findSimpleLanguageById(languageElement.getLanguageId());
                return I18N.get(language, SimpleLanguageLanguage::getName);
            }

            @Override
            public Filter modifyFilter(Filter filter) throws UnsupportedFilterException {
                return filter;
            }

        });

        propertiesList.add(0, LANGUAGE_PROPERTY);
        i18nKeysList.add(0, languageNameKey);

        languageGrid.setColumns(propertiesList.toArray());
        languageGrid.setColumnHeaderKeys(i18nKeysList.toArray(new String[1]));

        if (hasFilter) {
            createFilter(languageGrid);
        }

        List<Column> columns = languageGrid.getColumns();

        for (Column column : columns) {
            if (!LANGUAGE_PROPERTY.equals(column.getPropertyId())) {
                column.setRenderer(new TextFieldRenderer<String>());
            }
        }

        if (gridHeight != null) {
            languageGrid.setHeight(this.gridHeight, this.gridHeightUnit);
        }

        if (gridWidth != null) {
            languageGrid.setWidth(this.gridWidth, this.gridWidthUnit);
        }

        if (containerBean != null) {
            languageGrid.addAllBeans(containerBean.getLanguageElements());
        }

        if (expandRatios != null && expandRatios.length > 0) {
            for (int i = 0; i < expandRatios.length; i++) {
                columns.get(i)
                        .setExpandRatio(expandRatios[i]);
            }
        }
        return languageGrid;
    }

    private SimpleLanguage findSimpleLanguageById(long languageId) {
        for (SimpleLanguage language : I18N.getAvailableLanguages()) {
            if (language.getId() == languageId) {
                return language;
            }
        }
        return null;
    }

    private void addMissingLanguages(I18NLanguageContainer<ELEMENT> container) {
        for (SimpleLanguage language : I18N.getAvailableLanguages()) {
            ELEMENT languageElement = container.getLanguageElement(language.getId());

            if (languageElement == null) {
                try {
                    languageElement = this.propertyClazz.getConstructor()
                            .newInstance();
                    languageElement.setLanguageId(language.getId());
                    container.addI18NLanguageElement(languageElement);
                }
                catch (InstantiationException
                        | IllegalAccessException
                        | IllegalArgumentException
                        | InvocationTargetException
                        | NoSuchMethodException
                        | SecurityException e) {
                    LOGGER.error("Couldn't create languageElement of beantype ELEMENT for languageId: " + language.getId(), e);
                }
            }
        }
    }
}
