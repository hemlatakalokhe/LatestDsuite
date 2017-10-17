package de.bonprix.vaadin.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServletService;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

import de.bonprix.I18N;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.vaadin.admin.VaadinApplicationInformation;
import de.bonprix.vaadin.layout.BpApplicationLayout;
import de.bonprix.vaadin.layout.defaultview.DefaultViewViewImpl;
import de.bonprix.vaadin.provider.UiNotificationProvider;

/**
 * Subclass of the {@link Navigator} to handle our own way of switching between
 * screens.
 *
 */

public class BpNavigator extends Navigator {
	private static final long serialVersionUID = 1;
	private static final Logger LOGGER = LoggerFactory.getLogger(VaadinApplicationInformation.class);

	private SpringViewProvider viewProvider;

	private transient ApplicationContext applicationContext;

	private transient PrincipalProvider principalProvider;

	private transient UiNotificationProvider uiNotificationProvider;

	private transient ApplicationProvider applicationProvider;

	@Autowired
	public BpNavigator(UI ui, SpringViewProvider viewProvider, BpApplicationLayout bpLayout,
			PrincipalProvider principalProvider, UiNotificationProvider uiNotificationProvider,
			ApplicationProvider applicationProvider) {
		super(ui, bpLayout.getMainContent());
		this.viewProvider = viewProvider;
		this.principalProvider = principalProvider;
		this.uiNotificationProvider = uiNotificationProvider;
		this.applicationProvider = applicationProvider;
		addProvider(this.viewProvider);

		// after view is changed refreshing menu
		addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			/**
			 * Before navigating to the view we read the @SpringView annotation
			 * on this bean and check the capability for a particular role.
			 */
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return isNavigationAllowed(event);
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				bpLayout.refreshMenu(event.getViewName());

				updateTitle(event.getViewName());
			}

		});
	}

	@Override
	public void navigateTo(String navigationState) {
		try {
			// if view is empty and has children jump to first child
			View newView = this.viewProvider.getView(navigationState);
			if (isEmptyView(newView) && this.viewProvider.hasViewChildren(navigationState)) {
				navigateTo(this.viewProvider.getFirstViewNameOfChildren(navigationState));
				return;
			}

			super.navigateTo(navigationState);
		} catch (Exception e) {
			handleNavigationError(navigationState, e);
		}
	}

	public boolean isEmptyView(View view) {
		if (view instanceof AbstractLayout) {
			return ((AbstractLayout) view).getComponentCount() == 0;
		}

		if (view instanceof CustomComponent) {
			return ((CustomComponent) view).getComponentCount() == 0;
		}

		return false;
	}

	/**
	 * Workaround for issue 1, related to vaadin issues: 13566, 14884
	 * 
	 * @param navigationState
	 *
	 * @param e
	 *            the exception thrown by Navigator
	 */
	protected void handleNavigationError(String navigationState, Exception e) {
		// dont show error when url was opened blank
		if ("".equals(navigationState)) {
			LOGGER.info("Navigating to default view");
			navigateTo(this.viewProvider.getDefaultViewName());
			return;
		}

		LOGGER.info("Handle navigation error occured, navigating to default view", e);
		navigateTo(this.viewProvider.getDefaultViewName());

		UI.getCurrent()
			.getErrorHandler()
			.error(new com.vaadin.server.ErrorEvent(e));
	}

	private SpringView getViewAnnotation(String viewName) {
		if (this.applicationContext == null) {
			final UI ui = UI.getCurrent();
			if (ui == null) {
				throw new IllegalStateException("Could not find application context and no current UI is available");
			}
			this.applicationContext = ((SpringVaadinServletService) ui.getSession()
				.getService()).getWebApplicationContext();
		}

		return this.applicationContext.findAnnotationOnBean(viewName, SpringView.class);
	}

	/**
	 * Here we check if view change event has to be allowed or not. From the
	 * event we get the target state and then read the annotation from the
	 * respective view class. From that annotation we read its capability and
	 * decide if the view has to be navigated or not.
	 * 
	 * @param event
	 * @return
	 */
	private boolean isNavigationAllowed(ViewChangeEvent event) {
		String newView = event.getViewName();
		String viewBeanName = this.viewProvider.getBeanNameOfViewName(newView);
		if (StringUtils.isNotEmpty(viewBeanName)) {
			SpringView viewAnnotation = getViewAnnotation(viewBeanName);
			if (!this.principalProvider.hasCapability(viewAnnotation.capabilityKey())) {
				return accessDenied();
			}
			String parentViewName = this.viewProvider.getParentViewName(newView);
			if (StringUtils.isNotEmpty(parentViewName)) {
				viewAnnotation = getViewAnnotation(this.viewProvider.getBeanNameOfViewName(parentViewName));
				if (!this.principalProvider.hasCapability(viewAnnotation.capabilityKey())) {
					return accessDenied();
				}
			}
		}
		return Boolean.TRUE;
	}

	private Boolean accessDenied() {
		this.uiNotificationProvider.showErrorNotification("ACCESS_DENIED_CAPTION", "ACCESS_DENIED_MESSAGE");
		navigateTo(DefaultViewViewImpl.VIEW_NAME);
		return Boolean.FALSE;
	}

	private void updateTitle(String viewName) {
		StringBuilder titleBuilder = new StringBuilder(this.applicationProvider.getApplication()
			.getName());
		if (!DefaultViewViewImpl.VIEW_NAME.equals(viewName)) {
			titleBuilder.append(" - " + I18N.get(viewName));
		}

		Page.getCurrent()
			.setTitle(titleBuilder.toString());
	}

}
