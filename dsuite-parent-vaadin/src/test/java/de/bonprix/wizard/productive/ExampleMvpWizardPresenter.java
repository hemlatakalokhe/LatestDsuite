package de.bonprix.wizard.productive;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardPresenter;

@SpringPresenter
public class ExampleMvpWizardPresenter extends AbstractMvpWizardPresenter<ExampleMvpWizardView>
		implements ExampleMvpWizardView.Presenter {

	private ShowCasePojo pojo = new ShowCasePojo();

	@Override
	public boolean onFinished() {
		return true;
	}

	public ShowCasePojo getPojo() {
		return this.pojo;
	}

	public void setPojo(ShowCasePojo pojo) {
		this.pojo = pojo;
	}

}
