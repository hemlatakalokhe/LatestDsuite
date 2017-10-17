package de.bonprix.vaadin.ui.lazyscroll;

import com.vaadin.ui.Component;

@SuppressWarnings("serial")
public class LazyObjectSelectedEvent extends Component.Event {
	private LazyObject obj;

	public LazyObjectSelectedEvent(Component source, LazyObject obj) {
		super(source);
		this.obj = obj;
	}

	public LazyObject getLazyObject() {
		return obj;
	}
}
