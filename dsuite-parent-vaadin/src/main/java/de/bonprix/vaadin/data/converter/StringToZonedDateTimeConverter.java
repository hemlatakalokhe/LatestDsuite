package de.bonprix.vaadin.data.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.converter.Converter;

/**
 * This converter can convert a string to ZonedDateTime.
 * 
 * @author amar.bogari
 * 
 */
public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(StringToZonedDateTimeConverter.class);

	private DateTimeFormatter formatter = null;

	/**
	 * Uses a {@link ZonedDateTime} with pattern {@code dd.MM.yyyy HH:mm:ss}.
	 */
	public StringToZonedDateTimeConverter() {
		this(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
			.withLocale(Locale.ENGLISH));
	}

	/**
	 * Uses a {@link ZonedDateTime} with a custom pattern. If the pattern is
	 * {@code null}, a default {@link ZonedDateTime} with style
	 * {@code DateFormat.MEDIUM} is used.
	 */

	public StringToZonedDateTimeConverter(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public ZonedDateTime convertToModel(String value, Class<? extends ZonedDateTime> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (targetType != getModelType()) {
			throw new ConversionException("Converter only supports " + getModelType().getName() + " (targetType was "
					+ targetType.getName() + ")");
		}

		if (value == null) {
			return null;
		}

		// Remove leading and trailing white space
		String trimValue = value.trim();
		ZonedDateTime parsedValue = null;

		try {
			parsedValue = ZonedDateTime.parse(trimValue, this.formatter);
		} catch (DateTimeParseException e) {
			StringToZonedDateTimeConverter.LOGGER.error(e.getLocalizedMessage(), e);
		}

		return parsedValue;
	}

	@Override
	public String convertToPresentation(ZonedDateTime value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null) {
			return null;
		}
		String parsedValue = null;
		try {
			parsedValue = value.format(this.formatter);
		} catch (DateTimeParseException e) {
			StringToZonedDateTimeConverter.LOGGER.error(e.getLocalizedMessage(), e);
		}
		return parsedValue;
	}

	@Override
	public Class<ZonedDateTime> getModelType() {
		return ZonedDateTime.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
