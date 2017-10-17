package de.bonprix.eventbus;

import de.bonprix.vaadin.eventbus.EventHandler;

public class EventBusTestHandler {

	@EventHandler
	public void messageChanged(final TestMessageChangeEvent event){
		System.out.println(event.getTestMessage());
	}
}
