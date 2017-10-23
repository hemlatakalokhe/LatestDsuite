package de.bonprix.showcase.cases.wizard.mvp;

import javax.annotation.PostConstruct;

import com.vaadin.ui.Notification;

import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardPresenter;

@SuppressWarnings("rawtypes")
@SpringPresenter
public class ExampleMvpWizardPresenter extends AbstractMvpWizardPresenter<ExampleMvpWizardView> implements ExampleMvpWizardView.Presenter {

    private ShowCasePojo pojo = new ShowCasePojo();

    @PostConstruct
    public void init() {
        addWizardStep(new WizardStepOne(step -> Notification.show("step one enter"), step -> setPojo(((WizardStepOne) step).getPojo())));
        addWizardStep(new WizardStepTwo(step -> ((WizardStepTwo) step).setFromPojo(getPojo()), step -> Notification.show("step two leave")));
    }

    @Override
    public boolean onFinished() {
        return true;
    }

    public ShowCasePojo getPojo() {
        return this.pojo;
    }

    public void setPojo(final ShowCasePojo pojo) {
        this.pojo = pojo;
    }

}
