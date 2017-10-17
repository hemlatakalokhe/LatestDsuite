/**
 *
 */
package de.bonprix.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import de.bonprix.jersey.ClientFactoryConfig;
import de.bonprix.jersey.ClientFactoryConfig.ClientSideLogLevel;
import de.bonprix.jersey.JerseyClientFactory;
import de.bonprix.service.appinfo.ApplicationInfoService;
import de.bonprix.user.service.AuthenticationService;

/**
 * Wrapper class for the default {@link JerseyClientFactory}. For every base URL
 * the createClient method is called, a singleton bean is registered providing
 * an {@link ApplicationInfoService} interface to this remote rest application.
 * This bean is usually published to the ApplicationInfoDialog in
 * dsuite-parent-vaadin.<br>
 * <br>
 * This class actually does not check if the target REST URL really provides the
 * {@link ApplicationInfoService} endpoint. So always expect HTTP404 or similar
 * when calling methods on the registered service bean.
 *
 * @author cthiel
 * @date 16.11.2016
 *
 */
public class ApplicationInfoProvidingJerseyClientFactory extends JerseyClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInfoProvidingJerseyClientFactory.class);

	private final SingletonBeanRegistry beanRegistry;

	public ApplicationInfoProvidingJerseyClientFactory(final SingletonBeanRegistry beanFactory) {
		super();
		this.beanRegistry = beanFactory;
	}

	public ApplicationInfoProvidingJerseyClientFactory(final SingletonBeanRegistry beanFactory,
			final String defaultAuthUsername, final String defaultAuthPassword) {
		super(defaultAuthUsername, defaultAuthPassword);
		this.beanRegistry = beanFactory;
	}

	public ApplicationInfoProvidingJerseyClientFactory(final SingletonBeanRegistry beanFactory,
			final String defaultAuthUsername, final String defaultAuthPassword,
			final AuthenticationService defaultAuthService) {
		super(defaultAuthUsername, defaultAuthPassword, defaultAuthService);
		this.beanRegistry = beanFactory;
	}

	@Override
	public <E> E createClient(final String uri, final Class<E> clazz, final ClientFactoryConfig config) {
		String tmpUrl = uri;
		if (tmpUrl.endsWith("/")) {
			tmpUrl = uri.substring(0, uri.length() - 2);
		}

		tmpUrl = uri.substring(tmpUrl.lastIndexOf('/') + 1);
		final String beanName = uri.substring(tmpUrl.lastIndexOf('/') + 1);

		// add singleton bean for the url if not already exists
		if (!this.beanRegistry.containsSingleton(beanName)) {
			final ClientFactoryConfig beanClientConfig = new ClientFactoryConfig();
			beanClientConfig.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

			final ApplicationInfoService appInfoService = super.createClient(	uri, ApplicationInfoService.class,
																				beanClientConfig);

			this.beanRegistry.registerSingleton(beanName, appInfoService);
			ApplicationInfoProvidingJerseyClientFactory.LOGGER
				.debug("Registering applicationInfoService for " + beanName);
		}

		return super.createClient(uri, clazz, config);
	}

}
