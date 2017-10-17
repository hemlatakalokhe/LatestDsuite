package de.bonprix.vaadin.mvp.view.regular;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Layout.MarginHandler;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.security.PrincipalProvider;
import de.bonprix.user.dto.PermissionType;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.theme.DSuiteTheme;
import de.bonprix.vaadin.ui.ComponentBar;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarComponent;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarElement;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarItem;
import de.bonprix.vaadin.ui.ComponentBar.ComponentBarStyle;

/**
 * View part of mvp for {@link View}
 * 
 * @author thacht
 *
 * @param <PRESENTER>
 *            presenter interface
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMvpView<PRESENTER extends MvpViewPresenter> extends CustomComponent implements MvpView {
	private static final long serialVersionUID = 1L;

	@Autowired
	PRESENTER presenter;

	@Autowired
	private PrincipalProvider principalProvider;

	private VerticalLayout viewLayout;
	private ComponentBar menuBar;
	private HorizontalLayout mvpView;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		getPresenter().setView(this);

		this.menuBar = new ComponentBar(ComponentBarStyle.BUTTON, DSuiteTheme.COMPONENTBAR_MENU);
		this.menuBar.setVisible(false);
		initializeUI();

		try {
			getPresenter().init();
		} catch (Exception e) {
			UI.getCurrent()
				.getErrorHandler()
				.error(new com.vaadin.server.ErrorEvent(e));
		}
	}

	/**
	 * Initialized the view. Gets called ONCE after postconstruct of this view.
	 */
	protected abstract void initializeUI();

	@Override
	public void enter(final ViewChangeEvent event) {
		try {
			getPresenter().onViewEnter();
		} catch (Exception e) {
			UI.getCurrent()
				.getErrorHandler()
				.error(new com.vaadin.server.ErrorEvent(e));
		}
	}

	/**
	 * returns the corresponding presenter of mvp
	 *
	 * @return presenter
	 */
	protected PRESENTER getPresenter() {
		return this.presenter;
	}

	@Override
	public void tryNavigateTo(NavigationRequest request) {
		getPresenter().tryNavigateTo(request);
	}

	/**
	 * This method triggers the creation of the view layout with searchFilter,
	 * buttonBar and content. Note that the setter of the composition accepts
	 * the content itself but the getter will return not the content but the
	 * whole layout with filter, action bar and content.
	 */
	@Override
	protected void setCompositionRoot(final Component compositionRoot) {
		if (this.mvpView != null) {
			return;
		}

		initSurroundingLayout();

		if (compositionRoot instanceof MarginHandler) {
			MarginHandler marginHandler = (MarginHandler) compositionRoot;
			MarginInfo marginInfo = marginHandler.getMargin();
			if (!marginInfo.hasTop() && !marginInfo.hasRight() && !marginInfo.hasBottom() && !marginInfo.hasLeft()) {
				marginHandler.setMargin(true);
			}
		}
		this.mvpView.addComponent(compositionRoot);
	}

	protected void initSurroundingLayout() {
		setSizeFull();

		this.viewLayout = new VerticalLayout();
		this.viewLayout.setSizeFull();

		this.mvpView = new HorizontalLayout();
		this.mvpView.setSizeFull();

		final VerticalLayout viewWrapper = new VerticalLayout(this.menuBar, this.mvpView);
		viewWrapper.addStyleName("first");
		if (getModuleStyleName() != null) {
			viewWrapper.addStyleName(getModuleStyleName());
		}
		viewWrapper.setExpandRatio(this.mvpView, 1);
		viewWrapper.setSizeFull();
		final VerticalLayout viewWrapper2 = new VerticalLayout(viewWrapper);
		viewWrapper2.addStyleName("second");
		viewWrapper2.setSizeFull();

		this.viewLayout.addComponent(viewWrapper2);
		this.viewLayout.setExpandRatio(viewWrapper2, 1);
		super.setCompositionRoot(this.viewLayout);
	}

	protected String getModuleStyleName() {
		return null;
	}

	protected VerticalLayout getModuleLayout() {
		return this.viewLayout;
	}

	protected void addMenuElement(final ComponentBarElement element) {
		addMenuElement(element, null);
	}

	protected void addMenuElement(final ComponentBarElement element, String capabilityKey) {
		if (!StringUtils.isEmpty(capabilityKey)
				&& this.principalProvider.hasPermission(capabilityKey, PermissionType.NONE)) {
			element.withVisible(false);
		}

		if (!StringUtils.isEmpty(capabilityKey)
				&& this.principalProvider.hasPermission(capabilityKey, PermissionType.READ)) {
			element.withEnabled(false);
		}

		if (element instanceof ComponentBarComponent) {
			ComponentBarComponent component = (ComponentBarComponent) element;
			if (component.getComponent() instanceof Button) {
				throw new IllegalArgumentException(
						"com.vaadin.ui.Button is not allowed in the menu, use addMenuElement(ComponentBarItem item) instead.");
			}

		}

		this.menuBar.addElement(element);

		if (!this.menuBar.isVisible()) {
			if (element instanceof ComponentBarComponent) {
				this.menuBar.setVisible(element.isVisible());
			}
			if (element instanceof ComponentBarItem) {
				this.menuBar.setVisible(this.menuBar.getFirstParentItem(((ComponentBarItem) element).getId())
					.isVisible());
			}

		}
	}

	protected void enableMenuElement(final String itemId, final boolean enabled) {
		this.menuBar.setItemEnabled(itemId, enabled);
	}

	protected void enableMenuElement(final Enum enumItemId, final boolean enabled) {
		this.menuBar.setItemEnabled(enumItemId.name(), enabled);
	}

}
