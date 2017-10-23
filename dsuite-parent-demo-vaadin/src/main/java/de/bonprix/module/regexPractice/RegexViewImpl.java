package de.bonprix.module.regexPractice;

import java.util.List;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringView(
    name = RegexViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class RegexViewImpl extends AbstractMvpView<RegexPresenter> implements RegexView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "Regex";

    private CheckBox checkBox;

    @Override
    protected void initializeUI() {
        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void setAllBeans(final List<Planperiod> beans) {
        //
    }

}
