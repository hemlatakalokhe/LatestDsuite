package de.bonprix.common.component;

import com.vaadin.data.util.BeanItem;

import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.bean.grid.FilterHeader;

public class AdminGrid<BEANTYPE> {

    BeanItemGrid<BEANTYPE> beanItemGrid=null;

    public AdminGrid(final BeanItemGrid<BEANTYPE> beanItemGrid) {
        this.beanItemGrid = beanItemGrid;
    }

    public void configureGridHeaders(final String[] translations, final Object... propertyIds) {
        this.beanItemGrid.setColumns(propertyIds);
        this.beanItemGrid.setColumnHeaderKeys(translations);
        this.beanItemGrid.setSizeFull();
    }

    public BEANTYPE getContainerSelectedBean() {
        if (getGrid().getSelectedItem() != null) {
            final BeanItem<BEANTYPE> beanItem = getGrid().getBeanItemContainerDataSource()
                    .getItem(getGrid().getSelectedItem());
            return beanItem.getBean();
        }
        return null;
    }

    private BeanItemGrid<BEANTYPE> getGrid() {
        return this.beanItemGrid;
    }

    public void addNestedContainerProperty(final String propertyId) {
        this.beanItemGrid.addNestedContainerProperty(propertyId);
    }

    public FilterHeader getFilterHeader() {
        return this.beanItemGrid.addFilterHeader();
    }
}
