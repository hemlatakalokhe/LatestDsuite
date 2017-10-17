package de.bonprix.security;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;

import de.bonprix.configuration.AbstractSecurityConfiguration;
import de.bonprix.user.service.AuthenticationService;

/**
 * Security configuration class for webservices.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebserviceSecurityConfiguration extends AbstractSecurityConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(WebserviceSecurityConfiguration.class);

	@Resource
	protected Environment environment;

	@Resource
	protected ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		super.configure(http);

		final AuthenticationService authenticationService = (AuthenticationService) this.applicationContext
			.getBean("authenticationService");

		WebserviceSecurityConfiguration.LOG.info("Filtering Webservice calls ..");
		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.rememberMe()
			.rememberMeServices(new BonprixRememberMeService(authenticationService, this.applicationId))
			.and()
			.sessionManagement()
			.disable();
		http.getConfigurer(SecurityContextConfigurer.class)
			.disable();
	}
}
