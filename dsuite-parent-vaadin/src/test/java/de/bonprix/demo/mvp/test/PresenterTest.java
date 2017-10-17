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

public class PresenterTest extends BaseConfiguredUnitTest {

	@InjectMocks
	Presenter presenter;

	@Mock
	private EventBus eventbus;

	@Mock
	private DummyService dummyService;

	@Mock
	ViewImpl view;

	@Test
	public void testCallDummyServiceFunction() {
		this.presenter.callDummyServiceFunction();
		Mockito	.verify(this.dummyService)
				.dummyServiceFunction();
	}

	@Test
	public void testCallEventBusToStringMethod() {
		this.presenter.callEventBusToStringMethod();
		Mockito	.verify(this.eventbus)
				.setUseCache(true);
	}
}
