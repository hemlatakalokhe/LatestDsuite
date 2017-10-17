package de.bonprix.vaadin.bean.table.columngenerator;

import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import de.bonprix.vaadin.bean.table.BeanItemTable;

/**
 * Implemenation of a {@link ColumnGenerator} for generating a column based on a bean. 
 *
 * @param <BEANTYPE> BEANTYPE
 */
public abstract class BeanItemColumnGenerator<BEANTYPE> implements ColumnGenerator {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
    @Override
    public Object generateCell(final Table source, final Object itemId, final Object columnId) {
        return generateBeanCell((BeanItemTable<BEANTYPE>) source, (BEANTYPE) itemId, columnId);
    }

    public abstract Object generateBeanCell(final BeanItemTable<BEANTYPE> source, final BEANTYPE itemId, final Object columnId);

}
