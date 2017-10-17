package de.bonprix.jersey;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public ObjectMapperProvider() {
        this.objectMapper = ObjectMapperFactory.createObjectMapper();
    }

    public ObjectMapper getContext(final Class<?> objectType) {
        return this.objectMapper;
    }

}