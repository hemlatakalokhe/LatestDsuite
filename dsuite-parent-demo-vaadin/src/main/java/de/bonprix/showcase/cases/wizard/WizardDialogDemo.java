package de.bonprix.showcase.cases.wizard;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;

import de.bonprix.showcase.ShowCaseView;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;

@SpringViewComponent
public class WizardDialogDemo extends ShowcaseWrapper {

    private static final long serialVersionUID = -5661291409833395612L;

    @Autowired
    private transient ShowCaseView.Presenter presenter;

    public WizardDialogDemo() {
        super("STRUCTURE_HIERARCHY", "WIZARDDIALOGDEMO");
    }

    @Override
    protected Component createLayout() {
        return FluentUI.vertical()
            .margin()

            .add(FluentUI.button()
                .captionKey("open Mvp Dialog")
                .onClick(event -> this.presenter.openMvpWizard())
                .get())
            .get();
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
