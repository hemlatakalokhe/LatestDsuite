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
 * This Converter test class for String to Long List.
 */
@Test
public class StringToLongListConverterTest extends AbstractTestNGSpringContextTests{ 
	
	@Test
	public void convertToModelTest(){
		StringToLongListConverter converter = new StringToLongListConverter();
		//positive Test case
		List<Long> list = converter.convertToModel("12345,45678,89799", List.class, Locale.US);
		Assert.assertEquals(list.size(), 3);
		Assert.assertEquals(list.get(0), new Long(12345));
		//negative Test case
		Assert.assertNotEquals(list.size(), 4);
	}
	
	@Test
	public void convertToPresentationTest(){
		StringToLongListConverter converter = new StringToLongListConverter();
		List<Long> list = new ArrayList<Long>();
		list.add(12345L);
		list.add(45678L);
		list.add(89799L);		
		
		//positive Test case
		Assert.assertEquals(converter.convertToPresentation(list, String.class, Locale.US),"12345, 45678, 89799");
		//negative Test case
		Assert.assertNotEquals(converter.convertToPresentation(list, String.class, Locale.US),"12345, 45678, 89799 ,12345");
	}
	
	
}
