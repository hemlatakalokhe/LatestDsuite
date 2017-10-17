package de.bonprix.vaadin.bean.table.columngenerator;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class DateColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	private final DateColumnGenerator generator = new DateColumnGenerator();
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<Date> property;

	@Test
	public void testGenerateCellValueDateFormat() {
		Date date = new Date();
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(date);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof String);
		String string = (String) cell;
		
		Assert.assertEquals(string, DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date));
	}
	
	@Test
	public void testGenerateCellValueNull() {
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(null);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId"), null);
	}
	
}
