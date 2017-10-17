package de.bonprix.module.interceptor.child2;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import de.bonprix.VaadinUI;
import de.bonprix.module.interceptor.InterceptorViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringView(parentName = InterceptorViewImpl.VIEW_NAME, name = InterceptorChild2ViewImpl.VIEW_NAME, ui = {
		VaadinUI.class }, isDefault = false, order = 30)
public class InterceptorChild2ViewImpl extends AbstractMvpView<InterceptorChild2Presenter>
		implements InterceptorChild2View {

	private static final long serialVersionUID = 2688782241672861374L;

	public static final String VIEW_NAME = "INTERCEPTOR_CHILD2";

	private CheckBox checkBox;

	@Override
	protected void initializeUI() {
		this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");
		setCompositionRoot(FluentUI	.vertical()
									.sizeFull()
									.add(FluentUI	.label()
													.captionKey(VIEW_NAME)
													.get())
									.add(this.checkBox, Alignment.MIDDLE_CENTER)
									.get());
		setSizeFull();
	}

	@Override
	public void checkCheckBox(NavigationRequest request) {
		getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
	}

}
