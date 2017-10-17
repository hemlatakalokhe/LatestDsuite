package de.bonprix.showcase.cases.field;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.bean.field.util.OnEnterKeyListener;
import de.bonprix.vaadin.mvp.SpringViewComponent;

/**
 * A simple demo to show any kind of KeyListener
 *
 * @author tmeissne
 *
 */
@SuppressWarnings("serial")
@SpringViewComponent
public class KeyListenerDemo extends ShowcaseWrapper {

	public KeyListenerDemo() {
		super("INTERACTION", "KEYLISTENERDEMO");
	}

	@Override
	protected Component createLayout() {

		// Creating the Widgets
		TextField nameTextField = new TextField("Name");
		TextField streetTextField = new TextField("Street");

		// Create a OnEnterKeyListener
		OnEnterKeyListener onEnterKeyListener = new OnEnterKeyListener() {
			@Override
			public void onEnterKeyPressed() {
				Notification.show("Enter was pressed");
			}
		};

		// Add Widget to the OnEnterKeyListener
		onEnterKeyListener.installOn(nameTextField);
		onEnterKeyListener.installOn(streetTextField);

		// create the simple form layout
		final FormLayout formLayout = new FormLayout(nameTextField, streetTextField);

		formLayout.setWidth(100, Unit.PERCENTAGE);
		formLayout.setMargin(true);

		// make it look cooler with a custom style
		final Panel panel = new Panel(formLayout);
		panel.setSizeFull();
		panel.setStyleName("form-panel");

		// place a margin around
		final HorizontalLayout marginWrapper = new HorizontalLayout(panel);
		marginWrapper.setMargin(true);
		marginWrapper.setSizeFull();

		return marginWrapper;

	}

}
