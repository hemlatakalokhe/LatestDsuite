package de.bonprix.vaadin.bean.table.columngenerator;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 * Implemenation of a {@link ColumnGenerator} for generating a date. 
 */
public class DateColumnGenerator implements ColumnGenerator {

    private static final long serialVersionUID = 7643234254842266766L;

    @Override
    public Object generateCell(final Table source, final Object itemId, final Object columnId) {
        @SuppressWarnings("unchecked")
        final Property<Date> property = source.getContainerDataSource()
            .getContainerProperty(itemId, columnId);

        if (property.getValue() == null) {
            return null;
        }

        DateFormat f = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return f.format(property.getValue());
    }
}
