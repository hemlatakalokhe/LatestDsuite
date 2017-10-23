package de.bonprix.module.style.wizard;

import com.vaadin.spring.annotation.SpringView;

import de.bonprix.VaadinUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SuppressWarnings("serial")
@SpringView(
    name = StyleMvpViewImpl.VIEW_NAME,
    ui = { VaadinUI.class })
public class StyleMvpViewImpl extends AbstractMvpView<StyleMvpPresenter> implements StyleMvpView {

    protected static final String VIEW_NAME = "styles";

    @Override
    protected void initializeUI() {
        //
    }

}
