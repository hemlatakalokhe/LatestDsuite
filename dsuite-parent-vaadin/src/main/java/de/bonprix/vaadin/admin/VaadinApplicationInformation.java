/**
 *
 */
package de.bonprix.vaadin.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.bonprix.AbstractApplicationInformation;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.user.dto.Principal;

/**
 * Bean which provides basic vaadin application and session informations.
 *
 * @author cthiel
 * @date 15.11.2016
 *
 */
@Component
public class VaadinApplicationInformation extends AbstractApplicationInformation
		implements AttachListener, DetachListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(VaadinApplicationInformation.class);

	public static final String CREATED_UI_INSTANCES = "CREATED_UI_INSTANCES";
	public static final String ACTIVE_SESSIONS = "ACTIVE_SESSIONS";
	public static final String ACTIVE_SESSIONS_10 = "ACTIVE_SESSIONS_TEN";

	private final Set<UI> activeUis = new HashSet<>();
	private volatile int createdUis = 0;

	/**
	 * Pojo for session information.
	 *
	 * @author cthiel
	 * @date 15.11.2016
	 *
	 */
	public static class SessionInformation {
		private final Principal principal;
		private final Date lastAction;

		public SessionInformation(final Principal principal, final Date lastAction) {
			super();
			this.principal = principal;
			this.lastAction = lastAction;
		}

		public Principal getPrincipal() {
			return this.principal;
		}

		public Date getLastAction() {
			return this.lastAction;
		}
	}

	public VaadinApplicationInformation() {
		super("VAADIN_APPLICATION_INFORMATION");

		addProvider(VaadinApplicationInformation.CREATED_UI_INSTANCES, () -> getCreatedUiInstances());
		addProvider(VaadinApplicationInformation.ACTIVE_SESSIONS, () -> getActiveSessions());
		addProvider(VaadinApplicationInformation.ACTIVE_SESSIONS_10, () -> getActiveSessions10());
	}

	/**
	 * Returns the number of currently active sessions.
	 *
	 * @return
	 */
	public int getActiveSessions() {
		return this.activeUis.size();
	}

	/**
	 * Returns the number of currently active sessions which have been active
	 * within the last ten minutes.
	 *
	 * @return
	 */
	public int getActiveSessions10() {
		int num = 0;

		final long lastRequestLimit = System.currentTimeMillis() - 10 * 60 * 1000;

		for (final UI ui : this.activeUis) {
			if (ui.getSession()
				.getLastRequestTimestamp() > lastRequestLimit) {
				num++;
			}
		}
		return num;
	}

	/**
	 * SendGlobalNotification will get visible to the user when he click's
	 * somewhere. problem is that we cannot get this notification shown without
	 * a sync between server and client. the only solution would be to setup a
	 * general polling interval that is not sufficient
	 */
	public void sendlobalNotification(final String caption, final String description) {
		for (final UI ui : this.activeUis) {
			ui.access(() -> new Notification(caption, description, Type.ERROR_MESSAGE).show(ui.getPage()));
		}
	}

	/**
	 * Returns the number of overall created UI instances of this application
	 * deployment.
	 *
	 * @return
	 */
	public int getCreatedUiInstances() {
		return this.createdUis;
	}

	/**
	 * Returns a list of basic session informations of all currently active UI
	 * sessions.
	 *
	 * @return
	 */
	public List<SessionInformation> getAllSessionInformations() {
		final List<SessionInformation> result = new ArrayList<>();

		for (final UI ui : this.activeUis) {
			final Principal p = VaadinApplicationInformation.getPrincipalOfUi(ui);
			final Date lastAction = new Date(ui.getSession()
				.getLastRequestTimestamp());

			result.add(new SessionInformation(p, lastAction));
		}

		return result;
	}

	@Override
	public void detach(final DetachEvent event) {
		VaadinApplicationInformation.LOGGER.info("received detach event for UI {}", event.getConnector()
			.getUI()
			.hashCode());
		synchronized (this.activeUis) {
			this.activeUis.remove(event.getConnector()
				.getUI());
		}
	}

	@Override
	public void attach(final AttachEvent event) {
		final Principal p = VaadinApplicationInformation.getPrincipalOfUi(event.getConnector()
			.getUI());

		if (p != null) {
			VaadinApplicationInformation.LOGGER.info(	"received attach event for UI {} and principal {}",
														event.getConnector()
															.getUI()
															.hashCode(),
														p.getName());
		}

		this.createdUis++;
		synchronized (this.activeUis) {
			this.activeUis.add(event.getConnector()
				.getUI());
		}
	}

	/**
	 * Extracts the principal from the session.
	 *
	 * @param ui
	 *            some ui with a session
	 * @return
	 */
	private static Principal getPrincipalOfUi(final UI ui) {
		try {
			final SecurityContext securityContext = (SecurityContext) ui.getSession()
				.getSession()
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

			final BonprixAuthentication token = (BonprixAuthentication) securityContext.getAuthentication();

			return token.getPrincipal();
		} catch (final Exception e) {
			VaadinApplicationInformation.LOGGER.warn("couldn't optain principal from active vaadin session", e);
		}

		return null;
	}

}
