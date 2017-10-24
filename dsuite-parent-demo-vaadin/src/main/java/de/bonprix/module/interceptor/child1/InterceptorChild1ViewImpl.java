package de.bonprix.module.interceptor.child1;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;

import de.bonprix.VaadinUI;
import de.bonprix.module.interceptor.InterceptorViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringView(
    parentName = InterceptorViewImpl.VIEW_NAME,
    name = InterceptorChild1ViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class InterceptorChild1ViewImpl extends AbstractMvpView<InterceptorChild1Presenter> implements InterceptorChild1View {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "INTERCEPTOR_CHILD1";

    private CheckBox checkBox;

    @Override
    protected void initializeUI() {
        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
        setCompositionRoot(FluentUI.vertical()
            .sizeFull()
            .add(FluentUI.label()
                .captionKey(InterceptorChild1ViewImpl.VIEW_NAME)
                .get())
            .add(this.checkBox, Alignment.MIDDLE_CENTER)
            .get());
        setSizeFull();
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

}
