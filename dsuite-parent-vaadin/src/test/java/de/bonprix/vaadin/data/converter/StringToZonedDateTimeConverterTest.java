package de.bonprix.vaadin.data.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
public class StringToZonedDateTimeConverterTest  extends AbstractTestNGSpringContextTests{ 
	
	@Test
	public void convertToModelTest(){
		StringToZonedDateTimeConverter converter = new StringToZonedDateTimeConverter(new DateTimeFormatterBuilder() .parseCaseInsensitive()
		        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
		        .appendOffsetId()
		        .toFormatter());
		
//		positive Test case				
		ZonedDateTime zodaTime = converter.convertToModel("2016-10-04T11:23:55.851+05:30",
				ZonedDateTime.class, Locale.US);
				
		Assert.assertEquals(zodaTime.toString(),"2016-10-04T11:23:55.851+05:30");
		
		
		StringToZonedDateTimeConverter converter1 = new StringToZonedDateTimeConverter(new DateTimeFormatterBuilder() .parseCaseInsensitive()
		        .append(DateTimeFormatter.ISO_DATE_TIME)
		        .toFormatter()); 
		
//		positive Test case				
		ZonedDateTime zodaTime1 = converter1.convertToModel("2016-10-04T11:33:16.818+05:30[Asia/Calcutta]",
				ZonedDateTime.class, Locale.US);
						
		Assert.assertEquals(zodaTime1.toString(),"2016-10-04T11:33:16.818+05:30[Asia/Calcutta]");
		
//		negative Test case
		Assert.assertNotEquals(zodaTime.getDayOfMonth()+"." + zodaTime.getMonthValue()+"."+zodaTime.getYear(),
				ZonedDateTime.now().getDayOfMonth()+"." + ZonedDateTime.now().getMonthValue()+"."+ZonedDateTime.now().getYear()+1 );
	}
	
	@Test
	public void convertToPresentationTest(){
		StringToZonedDateTimeConverter converter = new StringToZonedDateTimeConverter(DateTimeFormatter.ofPattern ("d.M.yyyy"));
		//positive Test case
		ZonedDateTime zonedTime = ZonedDateTime.of(2016, 12, 29, 1, 1, 1, 1, ZoneId.systemDefault());
		
		Assert.assertEquals(converter.convertToPresentation(zonedTime,
				String.class, Locale.US),zonedTime.format(DateTimeFormatter.ofPattern ("d.M.yyyy")));
		//negative Test case
		Assert.assertNotEquals(converter.convertToPresentation(zonedTime,
				String.class, Locale.US),zonedTime.format(DateTimeFormatter.ofPattern ("d-M-yyyy")));
	}
	
}
