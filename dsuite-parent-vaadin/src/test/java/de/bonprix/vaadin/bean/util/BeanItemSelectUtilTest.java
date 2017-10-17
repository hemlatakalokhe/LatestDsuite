package de.bonprix.vaadin.bean.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;

public class BeanItemSelectUtilTest extends BaseConfiguredUnitTest{
	
	@Mock
	BeanItemSelect<Object> select;
	
	@Test
	public void testGetSelectedItemValueNull() {
		Mockito.when(select.getValue()).thenReturn(null);
		
		Assert.assertEquals(null, BeanItemSelectUtil.getSelectedItem(select));
	}
	
	@Test
	public void testGetSelectedItemValueEmptyList() {
		Mockito.when(select.getValue()).thenReturn(new ArrayList<Object>());
		
		Assert.assertEquals(null, BeanItemSelectUtil.getSelectedItem(select));
	}
	
	@Test
	public void testGetSelectedItemValueListWithOneElement() {
		Mockito.when(select.getValue()).thenReturn(new ArrayList<Object>(Arrays.asList("element1")));
		
		Assert.assertEquals("element1", BeanItemSelectUtil.getSelectedItem(select));
	}
	
	@Test
	public void testGetSelectedItemValueListWithTwoElement() {
		Mockito.when(select.getValue()).thenReturn(new ArrayList<Object>(Arrays.asList("element1", "element2")));
		
		Assert.assertEquals("element1", BeanItemSelectUtil.getSelectedItem(select));
	}
	
	@Test
	public void testGetSelectedItemValueElement() {
		Mockito.when(select.getValue()).thenReturn("element");
		
		Assert.assertEquals("element", BeanItemSelectUtil.getSelectedItem(select));
	}
	
	@Test
	public void testGetSelectedItemsValueNull() {
		Mockito.when(select.getValue()).thenReturn(null);
		
		Assert.assertEquals(Collections.emptyList(), BeanItemSelectUtil.getSelectedItems(select));
	}
	
	@Test
	public void testGetSelectedItemsValueEmptyList() {
		Mockito.when(select.getValue()).thenReturn(new ArrayList<Object>());
		
		Assert.assertEquals(Collections.emptyList(), BeanItemSelectUtil.getSelectedItems(select));
	}
	
	@Test
	public void testGetSelectedItemsValueListWithOneElement() {
		ArrayList<Object> value = new ArrayList<Object>(Arrays.asList("element1"));
		Mockito.when(select.getValue()).thenReturn(value);
		
		Assert.assertEquals(value, BeanItemSelectUtil.getSelectedItems(select));
	}
	
	@Test
	public void testGetSelectedItemsValueListWithTwoElement() {
		ArrayList<Object> value = new ArrayList<Object>(Arrays.asList("element1", "element2"));
		Mockito.when(select.getValue()).thenReturn(value);
		
		Assert.assertEquals(value, BeanItemSelectUtil.getSelectedItems(select));
	}
	
	@Test
	public void testGetSelectedItemsValueElement() {
		Mockito.when(select.getValue()).thenReturn("element");
		
		Assert.assertEquals(new ArrayList<Object>(Arrays.asList("element")), BeanItemSelectUtil.getSelectedItems(select));
	}

}
