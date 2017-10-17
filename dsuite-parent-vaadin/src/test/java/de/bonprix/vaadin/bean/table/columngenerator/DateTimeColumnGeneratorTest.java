package de.bonprix.vaadin.bean.table.columngenerator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class DateTimeColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<TemporalAccessor> property;

	@Test
	public void testGenerateCellValueDATETIME() {
		DateTimeColumnGenerator generator = new DateTimeColumnGenerator();
		
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(zonedDateTime);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof String);
		String string = (String) cell;
		
		Assert.assertEquals(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zonedDateTime));
	}
	
	@Test
	public void testGenerateCellValueDATE() {
		DateTimeColumnGenerator generator = new DateTimeColumnGenerator(DateTimeColumnGenerator.Format.DATE);
		
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(zonedDateTime);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof String);
		String string = (String) cell;
		
		Assert.assertEquals(string, DateTimeFormatter.ISO_LOCAL_DATE.format(zonedDateTime));
	}
	
	@Test
	public void testGenerateCellValueNull() {
		DateTimeColumnGenerator generator = new DateTimeColumnGenerator();
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(null);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId"), null);
	}
	
}
