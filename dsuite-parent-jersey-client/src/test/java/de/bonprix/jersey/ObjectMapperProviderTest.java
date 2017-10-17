package de.bonprix.jersey;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProviderTest {

    @InjectMocks
    private ObjectMapperProvider provider;

    @Mock
    private ObjectMapper mapper;

    @BeforeTest
    public void initTest() {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    private void objectMapperProviderTest() {
	ObjectMapper created = ObjectMapperFactory.createObjectMapper();
	Assert.assertNotNull(created);
    }

    @Test
    private void getContextTest() {
	this.provider.getContext(Mockito.any());
    }

}
