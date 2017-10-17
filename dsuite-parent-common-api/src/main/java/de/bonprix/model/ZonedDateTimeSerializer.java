package de.bonprix.model;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Ivan Slavchev
 */

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	@Override
	public void serialize(final ZonedDateTime value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
		gen.writeString(value.toString());
	}
}