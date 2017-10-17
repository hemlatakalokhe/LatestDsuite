package de.bonprix.vaadin.bean.table.columngenerator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class URLColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<String> property;

	@Test
	public void testGenerateCellValueUrl() {
		URLColumnGenerator generator = new URLColumnGenerator();
		
		String url = "www.bonprix.de";
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(url);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		Assert.assertTrue(cell instanceof Link);
		Link link = (Link) cell;
		
		Assert.assertEquals(link.getCaption(), url);
	}
	
	@Test
	public void testGenerateCellValueUrlShort() {
		URLColumnGenerator generator = new URLColumnGenerator(3);
		
		String url = "www.bonprix.de";
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(url);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		Assert.assertTrue(cell instanceof Link);
		Link link = (Link) cell;
		
		Assert.assertEquals(link.getCaption(), "www...");
	}
	
	@Test
	public void testGenerateCellValueNull() {
		URLColumnGenerator generator = new URLColumnGenerator();
		
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(null);
		
		Assert.assertEquals(generator.generateCell(source, "itemId", "columnId"), "");
	}
	
}
