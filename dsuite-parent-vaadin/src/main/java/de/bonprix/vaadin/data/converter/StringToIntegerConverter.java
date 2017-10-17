package de.bonprix.vaadin.data.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

/**
 *  This converter can convert a String to Integer.
 * @author amar.bogari
 * 
 */
public class StringToIntegerConverter extends com.vaadin.data.util.converter.StringToIntegerConverter implements Converter<String, Integer> {

    private static final long serialVersionUID = 1L;

    @Override
    protected NumberFormat getFormat(final Locale locale) {
        final NumberFormat format = super.getFormat(locale);

        format.setGroupingUsed(false);
        return format;
    }

}
