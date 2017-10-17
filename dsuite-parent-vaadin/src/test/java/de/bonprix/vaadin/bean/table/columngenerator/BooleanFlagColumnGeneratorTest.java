package de.bonprix.vaadin.bean.table.columngenerator;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;

import de.bonprix.BaseConfiguredUnitTest;

public class BooleanFlagColumnGeneratorTest extends BaseConfiguredUnitTest {
	
	private final BooleanFlagColumnGenerator generator = new BooleanFlagColumnGenerator();
	
	@Mock
	Table source;
	
	@Mock
	Container container;
	
	@Mock
	Property<Boolean> property;

	@Test
	public void testGenerateCellValueTrue() {
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(true);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof Image);
		Image image = (Image) cell;
		
		Assert.assertTrue(image.getSource() instanceof ExternalResource);
		ExternalResource externalResource = (ExternalResource) image.getSource();
		
		Assert.assertTrue(externalResource.getURL().contains("red"));
	}
	
	@Test
	public void testGenerateCellValueFalse() {
		Mockito.when(source.getContainerDataSource()).thenReturn(container);
		Mockito.when(container.getContainerProperty(Mockito.any(), Mockito.any())).thenReturn(property);
		Mockito.when(property.getValue()).thenReturn(false);
		
		Object cell = generator.generateCell(source, "itemId", "columnId");
		
		Assert.assertTrue(cell instanceof Image);
		Image image = (Image) cell;
		
		Assert.assertTrue(image.getSource() instanceof ExternalResource);
		ExternalResource externalResource = (ExternalResource) image.getSource();
		
		Assert.assertTrue(externalResource.getURL().contains("green"));
	}
	
}
