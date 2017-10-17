package de.bonprix.showcase;

import de.bonprix.showcase.cases.dialog.mvp.ExampleMvpDialogPresenter;
import de.bonprix.showcase.cases.wizard.mvp.ExampleMvpWizardPresenter;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter

public class ShowCasePresenter extends AbstractMvpViewPresenter<ShowCaseViewImpl> implements ShowCaseView.Presenter {

	@Override
	public void init() {
		// empty
	}

	@Override
	public void onViewEnter() {
		// empty
	}

	@Override
	public void openMvpDialog() {
		final ExampleMvpDialogPresenter mvpDialogPresenter = createPresenter(ExampleMvpDialogPresenter.class);
		mvpDialogPresenter.open();
	}

	@Override
	public void openMvpWizard() {
		final ExampleMvpWizardPresenter mvpWizardPresenter = createPresenter(ExampleMvpWizardPresenter.class);
		mvpWizardPresenter.open();
	}
}