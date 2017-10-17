package de.bonprix.vaadin.bean.table.columngenerator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class PlainNumberColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	private final PlainNumberColumnGenerator generator = new PlainNumberColumnGenerator();
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<Number> property;

	@Test
	public void testGenerateCellValueDateFormat() {
		Number number = 0L;
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(number);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof String);
		String string = (String) cell;
		
		Assert.assertEquals(string, number.toString());
	}
	
	@Test
	public void testGenerateCellValueNull() {
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(null);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId"), null);
	}
	
}
