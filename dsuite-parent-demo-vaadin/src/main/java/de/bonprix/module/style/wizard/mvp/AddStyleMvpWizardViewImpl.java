package de.bonprix.module.style.wizard.mvp;

import de.bonprix.vaadin.dialog.DialogButton;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.wizard.AbstractMvpWizardView;

@SpringViewComponent
public class AddStyleMvpWizardViewImpl extends AbstractMvpWizardView<AddStyleMvpWizardPresenter> implements AddStyleMvpWizardView<AddStyleMvpWizardPresenter> {

    /**
     * 
     */
    private static final long serialVersionUID = 8247395608407490694L;

    public AddStyleMvpWizardViewImpl() {
        super(new DialogConfigurationBuilder().withHeadline("ADD STYLE")
            .withButton(DialogButton.CANCEL)
            .withButton(DialogButton.BACK)
            .withButton(DialogButton.NEXT)
            .withButton(DialogButton.FINISH)
            .withWidth(1000)
            .withHeight(900)
            .build());
    }
}
