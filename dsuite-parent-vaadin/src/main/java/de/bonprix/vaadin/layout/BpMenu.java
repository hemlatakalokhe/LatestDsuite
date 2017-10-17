package de.bonprix.vaadin.layout;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.internal.Conventions;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.navigator.SpringViewProvider.ViewInfo;
import com.vaadin.spring.server.SpringVaadinServletService;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.I18N;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.vaadin.layout.BpMenuBar.NavigationListener;
import de.bonprix.vaadin.layout.burgermenu.BpBurgerMenuImpl;
import de.bonprix.vaadin.layout.defaultview.DefaultViewViewImpl;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.mvp.view.regular.MvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.spring.BpNavigator;
import de.bonprix.vaadin.util.ThemeHelper;

/**
 * This the menu of every bonprix vaadin application. A helper to automatically
 * create a menu from available Vaadin {@link SpringView} annotation from the
 * {@link SpringViewProvider} with first and second level.
 *
 */
@UIScope
public class BpMenu extends VerticalLayout {

	private static final Logger LOG = LoggerFactory.getLogger(ThemeHelper.class);

	private static final String HAS_SECONDARY_NAVIGATION = "has-secondary-navigation";

	@Autowired
	private SpringViewProvider viewProvider;

	private transient ApplicationContext applicationContext;

	@Resource
	private BpBurgerMenuImpl burgerMenu;

	@Resource
	private transient ApplicationProvider applicationProvider;

	private BpMenuBar primaryMenuBar;
	private BpMenuBar secondaryMenuBar;

	private boolean initializedPrimaryMenuBar = false;

	@Resource
	private transient PrincipalProvider principalProvider;

	@PostConstruct
	void init() {
		final NavigationListener listener = (viewId, primary, activeViewId) -> tryNavigateTo(	viewId, primary,
																								activeViewId);

		this.primaryMenuBar = new BpMenuBar(listener, true);
		this.secondaryMenuBar = new BpMenuBar(listener, false);

		this.primaryMenuBar.setRightCustomComponent(this.burgerMenu);

		addComponent(this.primaryMenuBar);
		addComponent(this.secondaryMenuBar);

		setWidth(100, Unit.PERCENTAGE);
	}

	private void tryNavigateTo(final String viewId, final boolean primary, final String activeViewId) {
		if (primary) {
			if (viewId.equals(activeViewId)) {
				if (this.viewProvider.hasViewChildren(viewId)) {
					// if primary menu view doesnt have children do nothing
					return;
				}

				if (((BpNavigator) UI.getCurrent()
					.getNavigator()).isEmptyView(this.viewProvider.getView(viewId))
						&& this.secondaryMenuBar.isFirstViewSelected()) {
					// if primary menu view has children and first element is
					// already selected do nothing
					return;
				}
			}
		} else {
			if (viewId.equals(activeViewId)) {
				// if secondary menu view is already selected do nothing
				return;
			}
		}

		final MvpView mvpView = (MvpView) getUI().getNavigator()
			.getCurrentView();
		mvpView.tryNavigateTo(new NavigationRequest(viewId));
	}

	public void refreshMenu(final String viewName) {
		initPrimaryMenuBar();

		if (this.primaryMenuBar.contains(viewName)) {
			refreshSecondaryMenu(viewName);
			return;
		}

		final String parentViewName = this.viewProvider.getParentViewName(viewName);
		if (!this.secondaryMenuBar.contains(viewName)) {
			refreshSecondaryMenu(parentViewName);
		}

		this.primaryMenuBar.markAsActive(parentViewName);
		this.secondaryMenuBar.markAsActive(viewName);
	}

	private boolean hasSecondaryNavigation() {
		return this.secondaryMenuBar != null ? this.secondaryMenuBar.isVisible() : false;
	}

	private void initPrimaryMenuBar() {
		if (!this.initializedPrimaryMenuBar) {
			this.primaryMenuBar.clear();

			for (final ViewInfo rootView : this.viewProvider.getRootViews()) {
				addItemToMenuBar(rootView, this.primaryMenuBar);
			}

			this.initializedPrimaryMenuBar = true;
		}
	}

	private void refreshSecondaryMenu(final String parentViewName) {
		this.primaryMenuBar.markAsActive(parentViewName);
		final List<ViewInfo> viewInfos = this.viewProvider.getChildViews(parentViewName);
		this.secondaryMenuBar.setVisible(!viewInfos.isEmpty());
		this.secondaryMenuBar.clear();
		for (final ViewInfo viewInfo : viewInfos) {
			addItemToMenuBar(viewInfo, this.secondaryMenuBar);
		}

		getParent().removeStyleName(HAS_SECONDARY_NAVIGATION);
		if (hasSecondaryNavigation()) {
			getParent().addStyleName(HAS_SECONDARY_NAVIGATION);
		}
	}

	/**
	 * throws IllegalStateException when Vaadin View doesn't implement
	 * {@link AbstractMvpView}.
	 *
	 * @param viewInfo
	 * @param menuBar
	 */
	private void addItemToMenuBar(final ViewInfo viewInfo, final BpMenuBar menuBar) {
		final Class<?> viewClass = getWebApplicationContext().getType(viewInfo.getBeanName());

		if (AbstractMvpView.class.isAssignableFrom(viewClass)) {
			final SpringView annotation = getWebApplicationContext().findAnnotationOnBean(	viewInfo.getBeanName(),
																							SpringView.class);
			final String viewName = Conventions.deriveMappingForView(viewClass, annotation);

			String viewNameTranslation;
			if (viewClass.equals(DefaultViewViewImpl.class)) {
				viewNameTranslation = this.applicationProvider.getApplication()
					.getName();
			} else {
				viewNameTranslation = I18N.get(viewName);
			}

			if (this.principalProvider.hasCapability(annotation.capabilityKey())) {
				menuBar.addItem(viewName, viewNameTranslation);
			}
		} else {
			BpMenu.LOG.error("The view bean [{}] does not implement AbstractMvpView", viewInfo);
			throw new IllegalStateException("SpringView bean [" + viewInfo + "] must implement AbstractMvpView");
		}
	}

	private ApplicationContext getWebApplicationContext() {
		if (this.applicationContext == null) {
			// Assume we have serialized and deserialized and Navigator is
			// trying to find a view so UI.getCurrent() is available
			final UI ui = UI.getCurrent();
			if (ui == null) {
				throw new IllegalStateException("Could not find application context and no current UI is available");
			}
			this.applicationContext = ((SpringVaadinServletService) ui.getSession()
				.getService()).getWebApplicationContext();
		}

		return this.applicationContext;
	}

}
