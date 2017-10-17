package de.bonprix.vaadin.layout.defaultview;

import javax.annotation.Resource;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;

import de.bonprix.information.ApplicationProvider;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(name = DefaultViewViewImpl.VIEW_NAME, order = -1, isDefault = true)
public class DefaultViewViewImpl extends AbstractMvpView<DefaultViewPresenter> implements DefaultViewView {

	private static final long serialVersionUID = 2688782241672861374L;

	public static final String VIEW_NAME = "DEFAULT_VIEW";

	@Resource
	private ApplicationProvider applicationProvider;

	@Override
	public void enter(final ViewChangeEvent event) {

	}

	@Override
	protected void initializeUI() {
		setSizeFull();
		setPrimaryStyleName("bp-defaultview");
		setCompositionRoot(FluentUI	.vertical()
									.sizeFull()
									.spacing()
									.add(	FluentUI.vertical()
													.get(),
											27)
									.add(	FluentUI.horizontal()
													.sizeFull()
													.add(	FluentUI.image()
																	.style("logo")
																	.source(DSuiteTheme.DSUITE_LOGO_MODULES_URL
																			+ this.applicationProvider	.getApplication()
																										.getId()
																			+ "_"
																			+ this.applicationProvider	.getApplication()
																										.getName()
																										.replace(	"d:",
																													"")
																			+ "-blue.png")
																	.get(),
															Alignment.MIDDLE_CENTER)
													.get(),
											33, Alignment.MIDDLE_CENTER)
									.add(	FluentUI.vertical()
													.get(),
											40)
									.get());
	}

}