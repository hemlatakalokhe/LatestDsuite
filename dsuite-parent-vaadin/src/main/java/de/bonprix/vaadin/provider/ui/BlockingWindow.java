package de.bonprix.vaadin.provider.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.bonprix.I18N;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.provider.UiDialogProvider.BlockingDialog;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringViewComponent
public class BlockingWindow extends Window implements BlockingDialog {

	private final Label loadText;

	public BlockingWindow() {
		setClosable(false);
		setModal(true);
		setResizable(false);
		setDraggable(false);
		setWidth(280, Unit.PIXELS);
		setHeight(100, Unit.PIXELS);
		center();
		setImmediate(true);
		setStyleName("loadingWindow");

		final VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		this.loadText = new Label();
		this.loadText.setStyleName("loadingText");
		layout.addComponent(this.loadText);
		Label spinner = new Label();
		spinner.addStyleName(DSuiteTheme.LABEL_SPINNER);
		layout.addComponent(spinner);
		layout.setComponentAlignment(spinner, Alignment.MIDDLE_CENTER);
		setContent(layout);
	}

	public void setMessageKey(final String messageKey) {
		this.loadText.setValue(I18N.get(messageKey));
	}

}
