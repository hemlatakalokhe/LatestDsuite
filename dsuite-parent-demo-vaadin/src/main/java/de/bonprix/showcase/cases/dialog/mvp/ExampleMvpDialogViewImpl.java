package de.bonprix.showcase.cases.dialog.mvp;

import com.vaadin.ui.Component;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogView;

@SpringViewComponent
public class ExampleMvpDialogViewImpl extends AbstractMvpDialogView<ExampleMvpDialogPresenter>
		implements ExampleMvpDialogView<ExampleMvpDialogPresenter> {

	private static final long serialVersionUID = 1L;

	public ExampleMvpDialogViewImpl() {
		super(new DialogConfigurationBuilder()	.withHeadline("Example Mvp Dialog")
												.withHeight(500)
												.withWidth(500)
												.withButton(DialogButton.CLOSE)
												.withCloseOnAnyButton(true)
												.build());
	}

	@Override
	protected Component layout() {
		return FluentUI	.vertical()
						.margin()
						.add(FluentUI	.label()
										.captionKey("HELLO_WORLD")
										.get())
						.get();
	}

}
