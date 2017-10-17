package de.bonprix.vaadin.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.DefaultUIProvider;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.server.SpringUIProvider;
import com.vaadin.spring.server.SpringVaadinServlet;

/**
 * Subclass of the Vaadin {@link SpringVaadinServlet} that adds a
 * {@link BpUIProvider} to every new Vaadin session.
 */

public class BpVaadinServlet extends SpringVaadinServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void servletInitialized() throws ServletException {
		getService().addSessionInitListener(new SessionInitListener() {

			private static final long serialVersionUID = -6307820453486668084L;

			/**
			 * Does the same as the super.sessionInit(), just adding a
			 * {@link BpUIProvider} instead of a {@link SpringUIProvider}
			 */
			@Override
			public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
				WebApplicationContextUtils.getWebApplicationContext(getServletContext());

				// remove DefaultUIProvider instances to avoid mapping
				// extraneous UIs if e.g. a servlet is declared as a nested
				// class in a UI class
				VaadinSession session = sessionInitEvent.getSession();
				List<UIProvider> uiProviders = new ArrayList<>(session.getUIProviders());
				for (UIProvider provider : uiProviders) {
					// use canonical names as these may have been loaded with
					// different classloaders
					if (DefaultUIProvider.class.getCanonicalName()
						.equals(provider.getClass()
							.getCanonicalName())) {
						session.removeUIProvider(provider);
					}
				}

				// add Bonprix UI provider
				BpUIProvider uiProvider = new BpUIProvider(session);
				session.addUIProvider(uiProvider);
			}
		});
	}

}
