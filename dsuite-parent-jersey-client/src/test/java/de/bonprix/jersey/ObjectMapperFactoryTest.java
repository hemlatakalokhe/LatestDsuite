package de.bonprix.jersey;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import static org.testng.Assert.*;
import de.bonprix.jersey.ObjectMapperFactory;

@SuppressWarnings("deprecation")
public class ObjectMapperFactoryTest {

    @Test
    public void testConstructorIsPrivate()
	    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
	Constructor<ObjectMapperFactory> constructor = ObjectMapperFactory.class.getDeclaredConstructor();
	assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    public void createObjectMapperTest() {
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	objectMapper.registerModule(new JSR310Module());

	ObjectMapper created = ObjectMapperFactory.createObjectMapper();
	assertNotNull(created);
    }

}
