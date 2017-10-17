/**
 *
 */
package de.bonprix.jms;

import org.springframework.jms.config.AbstractJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.user.service.AuthenticationService;

/**
 * Factory for the custom {@link BonprixMessageListenerContainer}
 *
 * @author cthiel
 * @date 11.11.2016
 *
 */
public class BonprixJmsListenerContainerFactory
		extends AbstractJmsListenerContainerFactory<BonprixMessageListenerContainer>
		implements JmsListenerContainerFactory<BonprixMessageListenerContainer> {

	private final GeneralApplicationInformation appInfo;
	private AuthenticationService authenticationService;
	private String systemuserUsername;
	private String systemuserPassword;

	public BonprixJmsListenerContainerFactory(final GeneralApplicationInformation appInfo) {
		this.appInfo = appInfo;
	}

	@Override
	protected BonprixMessageListenerContainer createContainerInstance() {
		return new BonprixMessageListenerContainer(this.appInfo, this.authenticationService, this.systemuserUsername,
				this.systemuserPassword);
	}

	/**
	 * Set the authentication details for system principal.
	 *
	 * @param authenticationService
	 * @param systemuserUsername
	 * @param systemuserPassword
	 */
	public void setAuthenticationDetails(final AuthenticationService authenticationService,
			final String systemuserUsername, final String systemuserPassword) {
		this.authenticationService = authenticationService;
		this.systemuserUsername = systemuserUsername;
		this.systemuserPassword = systemuserPassword;
	}

}
