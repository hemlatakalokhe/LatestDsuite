package de.bonprix.vaadin.data.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

/**
 * @author amar.bogari
 * 
 * This class is used for converting String to Long. 
 */
public class StringToLongConverter extends com.vaadin.data.util.converter.StringToLongConverter implements Converter<String, Long> {

    private static final long serialVersionUID = 1L;

    @Override
    protected NumberFormat getFormat(final Locale locale) {
        final NumberFormat format = super.getFormat(locale);

        format.setGroupingUsed(false);
        return format;
    }

}
