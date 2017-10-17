package de.bonprix.demo.mvp.productive;

import javax.annotation.Resource;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SpringView(name = ViewImpl.VIEW_NAME)
public class ViewImpl extends AbstractMvpView<Presenter> implements View {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "PARENT";

	@Resource
	private DummyService dummyService;

	@Resource
	private EventBus eventbus;

	@Override
	protected void initializeUI() {
		setCompositionRoot(new Label(VIEW_NAME));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().presenterFunction();
	}

	@Override
	public void viewImplFunction() {
		System.out.println("viewImplFunction");
	}

	public void callDummyServiceFunction() {
		this.dummyService.dummyServiceFunction();
	}

	public void callEventBusToStringMethod() {
		this.eventbus.setUseCache(true);
	}
}
