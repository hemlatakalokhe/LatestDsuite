package de.bonprix.wizard.productive;

import de.bonprix.vaadin.mvp.wizard.MvpWizardPresenter;
import de.bonprix.vaadin.mvp.wizard.MvpWizardView;

public interface ExampleMvpWizardView<PRESENTER extends ExampleMvpWizardView.Presenter>
		extends MvpWizardView<PRESENTER> {

	interface Presenter extends MvpWizardPresenter {

	}

}
