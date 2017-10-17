package de.bonprix.vaadin.bean.table.columngenerator;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 * Implemenation of a {@link ColumnGenerator} for generating a plain number. 
 */
public class PlainNumberColumnGenerator implements ColumnGenerator {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
    @Override
    public Object generateCell(final Table source, final Object itemId, final Object columnId) {
        final Property<Number> property = source.getContainerDataSource()
                .getContainerProperty(itemId, columnId);
        if (property.getValue() == null) {
            return null;
        }

        return property.getValue()
                .toString();
    }

}
