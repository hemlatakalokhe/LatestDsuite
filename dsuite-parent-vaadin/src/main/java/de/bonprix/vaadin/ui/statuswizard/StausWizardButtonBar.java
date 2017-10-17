package de.bonprix.vaadin.ui.statuswizard;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.bonprix.I18N;
import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.FontIconColor;
import de.bonprix.vaadin.IconSize;
import de.bonprix.vaadin.fluentui.FluentUI;

/**
 * @author dmut
 */

public class StausWizardButtonBar extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	private Button previous;
	private Button next;
	private Button cancel;
	private Button save;

	public StausWizardButtonBar() {
		initButtonMap();
		setWidth("100%");

	}

	public void initButtonMap() {
		// previous button
		this.previous = new Button();
		this.previous.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.previous
			.setIcon(new ExternalResource(FontBonprix.PREVIOUS.getPngUrl(IconSize.SIZE_16, FontIconColor.BLUE)));

		// next button
		this.next = new Button();
		this.next.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.next.setIcon(new ExternalResource(FontBonprix.NEXT.getPngUrl(IconSize.SIZE_16, FontIconColor.BLUE)));

		// save button
		this.save = new Button(I18N.get("SAVE"));
		this.save.addStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		this.cancel = new Button(I18N.get("CANCEL"));

	}

	public Component layout() {

		HorizontalLayout middleButtons = FluentUI.horizontal()
			.add(this.cancel, Alignment.BOTTOM_CENTER)
			.add(this.save, Alignment.BOTTOM_LEFT)
			.spacing()
			.sizeFull()
			.get();
		this.cancel.setWidth("100%");
		this.save.setWidth("100%");

		return FluentUI.horizontal()
			.add(this.previous, 0.1f, Alignment.BOTTOM_CENTER)
			.add(middleButtons, 0.8f, Alignment.BOTTOM_CENTER)
			.add(this.next, 0.1f, Alignment.BOTTOM_CENTER)
			.spacing()
			.sizeFull()
			.get();
	}

	public Button getPrevious() {
		return this.previous;
	}

	public Button getNext() {
		return this.next;
	}

	public Button getCancel() {
		return this.cancel;
	}

	public Button getSave() {
		return this.save;
	}

}
