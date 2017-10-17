package de.bonprix.jersey;

import java.time.ZonedDateTime;

import javax.ws.rs.ext.ParamConverter;

public class ZonedDateTimeParamConverter implements ParamConverter<ZonedDateTime> {

	@Override
	public ZonedDateTime fromString(final String value) {
		return ZonedDateTime.parse(value);
	}

	@Override
	public String toString(final ZonedDateTime value) {
		return value.toString();
	}

}
