/**
 *
 */
package de.bonprix.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import de.bonprix.jersey.JaxRsClientFactory;
import de.bonprix.rest.ApplicationInfoProvidingJerseyClientFactory;
import de.bonprix.user.service.AuthenticationService;

/**
 * @author cthiel
 * @date 05.11.2016
 *
 */
@Configuration
public class JaxRsClientConfiguration {

	@Bean
	@Primary
	public JaxRsClientFactory jaxRsClientFactory(final Environment environment,
			final ApplicationContext applicationContext,
			@Qualifier("delegatedAuthenticationService") final AuthenticationService authenticationService) {
		final String defaultAuthUsername = environment.getRequiredProperty(	"application.systemuser.username",
																			String.class);
		final String defaultAuthPassword = environment.getRequiredProperty(	"application.systemuser.password",
																			String.class);

		final ApplicationInfoProvidingJerseyClientFactory factory = new ApplicationInfoProvidingJerseyClientFactory(
				(SingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory(), defaultAuthUsername,
				defaultAuthPassword);
		factory.setDefaultAuthenticationService(authenticationService);

		return factory;
	}

	@Bean
	public JaxRsClientFactory dsuiteParentJaxRsClientFactory(final Environment environment,
			final ApplicationContext applicationContext,
			@Qualifier("delegatedAuthenticationService") final AuthenticationService authenticationService) {
		final String defaultAuthUsername = environment.getRequiredProperty("user.ws.username", String.class);
		final String defaultAuthPassword = environment.getRequiredProperty("user.ws.password", String.class);

		final ApplicationInfoProvidingJerseyClientFactory factory = new ApplicationInfoProvidingJerseyClientFactory(
				(SingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory(), defaultAuthUsername,
				defaultAuthPassword);
		factory.setDefaultAuthenticationService(authenticationService);

		return factory;
	}

}
