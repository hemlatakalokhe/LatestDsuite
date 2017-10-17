package de.bonprix.module.style.wizard;


import de.bonprix.module.style.dialogview.update.UpdateMvpDialogPresenter;
import de.bonprix.module.style.wizard.mvp.AddStyleMvpWizardPresenter;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class StyleMvpPresenter extends AbstractMvpViewPresenter<StyleMvpViewImpl> implements StyleMvpView.Presenter {

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
        final UpdateMvpDialogPresenter mvpDialogPresenter = createPresenter(UpdateMvpDialogPresenter.class);
        mvpDialogPresenter.open();
    }

    @Override
    public void openMvpWizard() {
        final AddStyleMvpWizardPresenter mvpWizardPresenter = this.createPresenter(AddStyleMvpWizardPresenter.class);
        mvpWizardPresenter.open();
    }
}