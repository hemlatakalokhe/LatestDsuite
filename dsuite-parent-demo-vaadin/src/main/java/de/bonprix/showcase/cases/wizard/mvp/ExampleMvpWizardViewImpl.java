package de.bonprix.showcase.cases.wizard.mvp;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardView;

@SpringViewComponent
public class ExampleMvpWizardViewImpl extends AbstractMvpWizardView<ExampleMvpWizardPresenter>
		implements ExampleMvpWizardView<ExampleMvpWizardPresenter> {

	private static final long serialVersionUID = 1L;

	public ExampleMvpWizardViewImpl() {
		super(new DialogConfigurationBuilder().withButton(DialogButton.BACK)
			.withButton(DialogButton.CANCEL)
			.withPrimaryButton(DialogButton.FINISH)
			.withButton(DialogButton.NEXT)
			.withWidth(1000)
			.withHeight(900)
			.build());
	}

}
