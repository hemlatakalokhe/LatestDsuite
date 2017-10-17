package de.bonprix.eventbus;

import static org.mockito.Mockito.times;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventMethodCache;

public class EventBusTest {

	private EventBus eventbus;
	private EventBusTestHandler handler;
	private EventMethodCache mockedEventMethodCache;
	
	@BeforeMethod
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		eventbus = new EventBus();
		handler = new EventBusTestHandler();
		
		this.initMockEventMethodCache();
	}

	@Test
	public void addHandlerEventCachingFalseTest() throws Exception {

		eventbus.setUseCache(false);

		eventbus.addHandler(handler);
		
		Mockito.verify(mockedEventMethodCache, Mockito.never()).addMethodToCache(Mockito.anyObject(), Mockito.any(java.lang.reflect.Method.class));

	}
	
	@Test
	public void addHandlerEventCachingTrueClassIsCachedTest() throws Exception {

		eventbus.setUseCache(true);
			
		Mockito.when(mockedEventMethodCache.isClassCached(Mockito.any(Class.class))).thenReturn(true);
		
		eventbus.addHandler(handler);
		
		Mockito.verify(mockedEventMethodCache, Mockito.never()).addMethodToCache(Mockito.anyObject(), Mockito.any(java.lang.reflect.Method.class));
		Mockito.verify(mockedEventMethodCache, times(1)).getMethods(Mockito.anyObject());

	}
	
	@Test
	public void addHandlerEventCachingTrueClassNotCachedTest() throws Exception {
		
		eventbus.setUseCache(true);
			
		Mockito.when(mockedEventMethodCache.isClassCached(Mockito.any(Class.class))).thenReturn(false);

		eventbus.addHandler(handler);
		
		Mockito.verify(mockedEventMethodCache, times(1)).addMethodToCache(Mockito.anyObject(), Mockito.any(java.lang.reflect.Method.class));
	}
	
	@Test
	public void fireEventTest(){
		
		eventbus.addHandler(handler);
		TestMessageChangeEvent mockEvent = new TestMessageChangeEvent("test");

		Boolean fired = eventbus.fireEvent(mockEvent);
		
		Assert.assertTrue(fired);
		
	}
	
	private void initMockEventMethodCache() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		mockedEventMethodCache = Mockito.mock(EventMethodCache.class);
		Field field = EventBus.class.getDeclaredField("eventMethodChache");
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(EventBus.class, mockedEventMethodCache);
	}
}
