package de.bonprix.demo.mvp.productive;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@Component
public class Presenter extends AbstractMvpViewPresenter<ViewImpl> implements View.Presenter {

	@Resource
	private EventBus eventbus;

	@Resource
	private DummyService dummyService;

	@Override
	public void init() {
		System.out.println("init");
		getView().viewImplFunction();
	}

	@Override
	public void presenterFunction() {
		System.out.println("presenterFunction");
	}

	public void callDummyServiceFunction() {
		this.dummyService.dummyServiceFunction();
	}

	public void callEventBusToStringMethod() {
		this.eventbus.setUseCache(true);
	}

	@Override
	public void onViewEnter() {
	}
}