package de.bonprix.vaadin.bean.field.util;

import com.vaadin.server.Resource;

public interface ItemCountGenerator<BEANTYPE> {

    /**
     * used to generate a special icon handling
     * 
     * @param item
     * @return the icon resource
     */
    Resource getIcon(final BEANTYPE item);
}
