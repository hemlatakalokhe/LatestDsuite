package de.bonprix.vaadin.data.converter;

import java.util.Locale;

import org.junit.Assert;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author a.bogari
 *	
 * This Converter test class for String to Long.
 */
@Test
public class StringToLongConverterTest extends AbstractTestNGSpringContextTests{ 
	
	@Test
	public void convertToModelTest(){
		StringToLongConverter converter = new StringToLongConverter();
		//positive Test case
		Assert.assertEquals(converter.convertToModel("1235478", Long.class, Locale.US), new Long(1235478));
		//negative Test case
		Assert.assertNotEquals(converter.convertToModel("1235478", Long.class, Locale.US), new Long(12354789));
		Assert.assertEquals(converter.convertToModel("", Long.class, Locale.US), null);
		Assert.assertEquals(converter.convertToModel(null, Long.class, Locale.US), null);
	}
	
	@Test
	public void convertToPresentationTest(){
		StringToLongConverter converter = new StringToLongConverter();
		//positive Test case
		Assert.assertEquals(converter.convertToPresentation(1235478L, String.class, Locale.US),"1235478");
		//negative Test case
		Assert.assertNotEquals(converter.convertToPresentation(1235478L, String.class, Locale.US), "12354787");
		Assert.assertEquals(converter.convertToPresentation(null, String.class, Locale.US), null);
		Assert.assertEquals(converter.convertToPresentation(0L, String.class, Locale.US), "0");
	}
	
	
}
