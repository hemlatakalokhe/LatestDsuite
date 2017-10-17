/**
 *
 */
package de.bonprix.jms;

import java.time.LocalDateTime;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Strings;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.RequestId;
import de.bonprix.configuration.LoggingConfiguration;
import de.bonprix.security.AuthenticationUtil;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.user.service.AuthenticationService;

/**
 * Custom message listener container. This container extends the default
 * {@link SimpleMessageListenerContainer} by the following features:<br/>
 * <ul>
 * <li>Adding and clearing security context with the system principal</li>
 * <li>setting and unsetting common {@link ThreadContext} variables like
 * REQUEST_ID and APPLICATION</li>
 * </ul>
 *
 * @author cthiel
 * @date 11.11.2016
 *
 */
public class BonprixMessageListenerContainer extends SimpleMessageListenerContainer {
	private static final Logger LOGGER = LoggerFactory.getLogger(BonprixMessageListenerContainer.class);

	private static final Long AUTH_KEY_CACHE_TTL = 300000L;

	private final GeneralApplicationInformation appInfo;
	private final AuthenticationService authenticationService;
	private final String systemuserUsername;
	private final String systemuserPassword;

	private LocalDateTime authKeyCacheExpireDate = null;
	private BonprixAuthentication token = null;

	/**
	 * @param authenticationService
	 * @param systemuserUsername
	 * @param systemuserPassword
	 */
	public BonprixMessageListenerContainer(final GeneralApplicationInformation appInfo,
			final AuthenticationService authenticationService, final String systemuserUsername,
			final String systemuserPassword) {
		this.appInfo = appInfo;
		this.authenticationService = authenticationService;
		this.systemuserUsername = systemuserUsername;
		this.systemuserPassword = systemuserPassword;
	}

	@Override
	protected void doInvokeListener(final MessageListener listener, final Message message) throws JMSException {
		try {
			AuthenticationUtil.setPrincipal(this.systemuserUsername, this.systemuserPassword,
											this.authenticationService, this.appInfo.getApplicationId(),
											AUTH_KEY_CACHE_TTL, this.token, this.authKeyCacheExpireDate);
			// set instance variable to created @BonprixAuthentication to avoid
			// create it again, because this is redundant
			this.token = (BonprixAuthentication) SecurityContextHolder.getContext()
				.getAuthentication();
			setLoggingProperties(message);

			super.doInvokeListener(listener, message);
		} finally {
			AuthenticationUtil.unsetPrincipal();
			unsetLoggingProperties();
		}
	}

	@Override
	protected void doInvokeListener(final SessionAwareMessageListener listener, final Session session,
			final Message message) throws JMSException {
		try {
			AuthenticationUtil.setPrincipal(this.systemuserUsername, this.systemuserPassword,
											this.authenticationService, this.appInfo.getApplicationId(),
											AUTH_KEY_CACHE_TTL, this.token, this.authKeyCacheExpireDate);
			// set instance variable to created @BonprixAuthentication to avoid
			// create it again, because this is redundant
			this.token = (BonprixAuthentication) SecurityContextHolder.getContext()
				.getAuthentication();
			setLoggingProperties(message);

			super.doInvokeListener(listener, session, message);
		} finally {
			AuthenticationUtil.unsetPrincipal();
			unsetLoggingProperties();
		}
	}

	/**
	 * Sets some logging properties to {@link ThreadContext}.
	 *
	 * @param message
	 *            the received message
	 * @throws JMSException
	 */
	void setLoggingProperties(final Message message) throws JMSException {
		final String requestId = message.getStringProperty(RequestId.REQUEST_ID_HEADER);
		if (!Strings.isNullOrEmpty(requestId)) {
			BonprixMessageListenerContainer.LOGGER.debug("Found requestId in message metadata: " + requestId);
			RequestId.setRequestId(requestId);
		} else {
			RequestId.setRequestId(RequestId.generateRequestId());
		}

		ThreadContext.put(LoggingConfiguration.APPLICATION, this.appInfo.getApplicationContextPath());
		ThreadContext.put(LoggingConfiguration.REQUEST_ID, RequestId.getRequestId());
		ThreadContext.put(LoggingConfiguration.ENVIRONMENT, "DEV");
	}

	/**
	 * Unset the previously set logging properties.
	 */
	void unsetLoggingProperties() {
		ThreadContext.remove(LoggingConfiguration.REQUEST_ID);
		ThreadContext.remove(LoggingConfiguration.APPLICATION);
		ThreadContext.remove(LoggingConfiguration.ENVIRONMENT);
	}
}
