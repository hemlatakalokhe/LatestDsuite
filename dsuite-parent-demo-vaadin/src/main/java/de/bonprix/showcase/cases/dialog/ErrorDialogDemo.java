package de.bonprix.showcase.cases.dialog;

import com.vaadin.ui.Component;

import de.bonprix.exception.SimpleShowcaseException;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SuppressWarnings("serial")
@SpringViewComponent
public class ErrorDialogDemo extends ShowcaseWrapper {

	public ErrorDialogDemo() {
		super("STRUCTURE_HIERARCHY", "ERRORDIALOGDEMO");
	}

	@Override
	protected Component createLayout() {
		return FluentUI.vertical()
			.margin()
			.add(FluentUI.button()
				.style(DSuiteTheme.BUTTON_PRIMARY)
				.captionKey("OPEN_ERROR_DIALOG")
				.onClick(event -> {
					throw new SimpleShowcaseException(
							"Some random RuntimeException to demonstrate the fallback error dialog");
				})
				.get())
			.get();
	}
}
