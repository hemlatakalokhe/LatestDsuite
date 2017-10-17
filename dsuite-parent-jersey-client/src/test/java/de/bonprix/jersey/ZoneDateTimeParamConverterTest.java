package de.bonprix.jersey;

import java.time.ZonedDateTime;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ZoneDateTimeParamConverterTest {

    @Mock
    private ZonedDateTimeParamConverter converter;

    @BeforeTest
    public void initMocks() {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fromStringTest() {
	this.converter.fromString("2007-12-03T10:15:30+01:00[Europe/Paris]");
	Mockito.verify(this.converter).fromString("2007-12-03T10:15:30+01:00[Europe/Paris]");

    }

    @Test
    public void toStringTest() {
	this.converter.toString(Mockito.any(ZonedDateTime.class));
	Mockito.verify(this.converter).toString(Mockito.any(ZonedDateTime.class));
    }
}
