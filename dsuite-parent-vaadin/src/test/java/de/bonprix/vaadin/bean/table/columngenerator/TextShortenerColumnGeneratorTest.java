package de.bonprix.vaadin.bean.table.columngenerator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class TextShortenerColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<String> property;

	@Test
	public void testGenerateCellValueText() {
		TextShortenerColumnGenerator generator = new TextShortenerColumnGenerator(8);
		
		String text = "TEXT";
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(text);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId").toString(), text);
	}
	
	@Test
	public void testGenerateCellValueVeryLongText() {
		TextShortenerColumnGenerator generator = new TextShortenerColumnGenerator(8);
		
		String text = "VERYLONGTEXT";
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(text);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId").toString(), "VERYLONG...");
	}
	
	@Test
	public void testGenerateCellValueNull() {
		TextShortenerColumnGenerator generator = new TextShortenerColumnGenerator(10);
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(null);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId"), "");
	}
	
}
