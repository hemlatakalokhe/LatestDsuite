package de.bonprix.vaadin.bean.field.util;

public interface ItemCaptionGenerator<BEANTYPE> {

    /**
     * used to generate a special caption handling
     * 
     * @param item
     * @return the caption
     */
    String getCaption(final BEANTYPE item);

}
