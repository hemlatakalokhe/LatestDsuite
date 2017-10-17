package de.bonprix.module.style.wizard.mvp;

import de.bonprix.base.demo.dto.Style;
import de.bonprix.vaadin.mvp.wizard.MvpWizardPresenter;
import de.bonprix.vaadin.mvp.wizard.MvpWizardView;

public interface AddStyleMvpWizardView <PRESENTER extends AddStyleMvpWizardView.Presenter> extends MvpWizardView<PRESENTER>{

    public interface Presenter extends MvpWizardPresenter{
        void saveStyle(Style style);
    }


}
