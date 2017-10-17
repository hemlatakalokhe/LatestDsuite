package de.bonprix.demo.mvp.test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.demo.mvp.productive.DummyService;
import de.bonprix.demo.mvp.productive.Presenter;
import de.bonprix.demo.mvp.productive.ViewImpl;
import de.bonprix.vaadin.eventbus.EventBus;

public class ViewImplTest extends BaseConfiguredUnitTest {

	@InjectMocks
	ViewImpl view;

	@Mock
	Presenter presenter;

	@Mock
	private DummyService dummyService;

	@Mock
	private EventBus eventbus;

	@Test
	public void testCallDummyServiceFunction() {
		this.view.callDummyServiceFunction();
		Mockito	.verify(this.dummyService)
				.dummyServiceFunction();
	}

	@Test
	public void testCallEventBusToStringMethod() {
		this.view.callEventBusToStringMethod();
		Mockito	.verify(this.eventbus)
				.setUseCache(true);
	}

}
