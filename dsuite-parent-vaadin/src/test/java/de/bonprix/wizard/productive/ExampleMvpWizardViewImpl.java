package de.bonprix.wizard.productive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardView;

@SpringViewComponent
public class ExampleMvpWizardViewImpl extends AbstractMvpWizardView<ExampleMvpWizardPresenter>
		implements ExampleMvpWizardView<ExampleMvpWizardPresenter> {
	private static final long serialVersionUID = 1L;

	public ExampleMvpWizardViewImpl() {
		super(new DialogConfigurationBuilder()	.withButton(DialogButton.BACK)
												.withButton(DialogButton.CANCEL)
												.withPrimaryButton(DialogButton.FINISH)
												.withButton(DialogButton.NEXT)
												.withWidth(1000)
												.withHeight(900)
												.build());
	}

	@Override
	protected Component layout() {
		getPresenter().addWizardStep(new WizardStepOne((step) -> Notification.show("step one enter"),
				(step) -> getPresenter().setPojo(((WizardStepOne) step).getPojo())));
		getPresenter().addWizardStep(new WizardStepTwo(
				(step) -> ((WizardStepTwo) step).setFromPojo(getPresenter().getPojo()),
				(step) -> Notification.show("step two leave")));

		return super.layout();
	}
}
