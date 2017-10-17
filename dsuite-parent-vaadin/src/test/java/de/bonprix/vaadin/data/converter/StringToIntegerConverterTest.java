package de.bonprix.vaadin.data.converter;

import java.util.Locale;

import org.junit.Assert;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author a.bogari
 *	
 * This Converter test class for String to Integer.
 */
@Test
public class StringToIntegerConverterTest extends AbstractTestNGSpringContextTests{ 
	
	@Test
	public void convertToModelTest(){
		StringToIntegerConverter converter = new StringToIntegerConverter();
		
		//positive Test case
		Assert.assertEquals(converter.convertToModel("12345", Integer.class, Locale.US), new Integer(12345));
		
		//negative Test case
		Assert.assertNotEquals(converter.convertToModel("12345", Integer.class, Locale.US), new Integer(123456));
		Assert.assertEquals(converter.convertToModel("", Integer.class, Locale.US), null);
		Assert.assertEquals(converter.convertToModel(null, Integer.class, Locale.US), null);
	}
	
	@Test
	public void convertToPresentationTest(){
		StringToIntegerConverter converter = new StringToIntegerConverter();
		
		//positive Test case
		Assert.assertEquals(converter.convertToPresentation(12345, String.class, Locale.US),"12345");
		
		//negative Test case
		Assert.assertNotEquals(converter.convertToPresentation(12345, String.class, Locale.US),"123456");
		Assert.assertEquals(converter.convertToPresentation(null, String.class, Locale.US),null);
		Assert.assertEquals(converter.convertToPresentation(0, String.class, Locale.US),"0");
	}
	
	
}
