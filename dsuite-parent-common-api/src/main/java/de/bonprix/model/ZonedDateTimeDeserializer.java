package de.bonprix.model;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author Ivan Slavchev
 */

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
		return ZonedDateTime.parse(jp.getText());
	}
}
