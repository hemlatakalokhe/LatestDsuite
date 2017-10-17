package de.bonprix.vaadin.data.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author a.bogari
 *	
 * This Converter test class for String to String List.
 */
@Test
public class StringToStringListConverterTest extends AbstractTestNGSpringContextTests{ 
	
	@Test
	public void convertToModelTest(){
		StringToStringListConverter converter = new StringToStringListConverter();
		//positive Test case
		List<String> list = converter.convertToModel("12345,45678,89799", List.class, Locale.US);
		Assert.assertEquals(list.size(), 3);
		Assert.assertEquals(list.get(0), "12345");
		//negative Test case
		Assert.assertNotEquals(list.size(), 4);
	}
	
	@Test
	public void convertToPresentationTest(){
		StringToStringListConverter converter = new StringToStringListConverter();
		List<String> list = new ArrayList<String>();
		list.add("12345");
		list.add("45678");
		list.add("89799");		
		
		//positive Test case
		Assert.assertEquals(converter.convertToPresentation(list, String.class, Locale.US),"12345, 45678, 89799");
		//negative Test case
		Assert.assertNotEquals(converter.convertToPresentation(list, String.class, Locale.US),"12345, 45678, 89799 ,12345");
	}
	
	
}
