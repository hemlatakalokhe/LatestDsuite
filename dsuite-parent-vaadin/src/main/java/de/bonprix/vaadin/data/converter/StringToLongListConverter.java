package de.bonprix.vaadin.data.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.vaadin.data.util.converter.Converter;

/**
 *  This converter can convert a string containing a list of comma separated integer values to a list of Long values. 
 *  
 * @author amar.bogari
 * 
 */
@SuppressWarnings("rawtypes")
public class StringToLongListConverter implements Converter<String, List> {

    private static final long serialVersionUID = 1L;

    @Override
    public List<Long> convertToModel(final String value, final Class<? extends List> targetType, final java.util.Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        try {
            // split the input by ",", trim the tokens and convert to arraylist
            List<Long> orderIds = null;
            if (value != null && value.length() > 0) {
                orderIds = new ArrayList<Long>();
                for (final String part : value.split(",")) {
                    if (part != null && part.length() > 0) {
                        orderIds.add(Long.parseLong(part.trim()));
                    }
                }
            }

            return orderIds;
        }
        catch (final NumberFormatException e) {
            throw new ConversionException("Could not convert value to number");
        }
    }

    @Override
    public String convertToPresentation(final List value, final Class<? extends String> targetType, final java.util.Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null || value.isEmpty()) {
            return "";
        }

        final List<String> strings = new ArrayList<String>(value.size());

        for (final Object l : value) {
            strings.add(((Long) l).toString());
        }

        return Joiner.on(", ").join(strings);
    }

    @Override
    public Class<List> getModelType() {
        return List.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
