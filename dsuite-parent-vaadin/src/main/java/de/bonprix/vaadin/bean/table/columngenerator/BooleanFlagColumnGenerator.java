package de.bonprix.vaadin.bean.table.columngenerator;

import com.vaadin.data.Property;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.FontIconColor;
import de.bonprix.vaadin.IconSize;

/**
 * Implemenation of a {@link ColumnGenerator} for generating a boolean flag generator.
 */
public class BooleanFlagColumnGenerator implements ColumnGenerator {
	private static final long serialVersionUID = 1L;

	@Override
    public Object generateCell(final Table source, final Object itemId, final Object columnId) {
        @SuppressWarnings("unchecked")
        final Property<Boolean> property = source.getContainerDataSource()
            .getContainerProperty(itemId, columnId);

        final Image img = new Image(null, FontBonprix.DOT.getPng(IconSize.SIZE_16, property.getValue() ? FontIconColor.RED : FontIconColor.GREEN));
        img.setHeight(16, Unit.PIXELS);
        img.setWidth(16, Unit.PIXELS);
        return img;
    }
}
