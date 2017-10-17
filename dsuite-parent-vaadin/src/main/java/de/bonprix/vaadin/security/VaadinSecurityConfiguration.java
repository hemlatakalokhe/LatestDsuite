package de.bonprix.vaadin.security;

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
import de.bonprix.security.AnyRoleAccessDecisionManager;
import de.bonprix.security.BonprixRememberMeService;
import de.bonprix.user.service.AuthenticationService;

/**
 * Security configuration class for Vaadin application.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VaadinSecurityConfiguration extends AbstractSecurityConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(VaadinSecurityConfiguration.class);

	@Resource
	private Environment environment;

	@Resource
	private ApplicationContext applicationContext;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		super.configure(http);

		final AuthenticationService authenticationService = (AuthenticationService) this.applicationContext
			.getBean("authenticationService");

		http.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.and()
			.rememberMe()
			.rememberMeServices(new BonprixRememberMeService(authenticationService, this.applicationId))
			.authenticationSuccessHandler(bonprixStripAuthKeySuccessHandler())
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint());

		http.getConfigurer(SecurityContextConfigurer.class)
			.disable();

		final boolean applicationNeedsRole = this.environment.getProperty(	"security.role.required", Boolean.class,
																			true);

		VaadinSecurityConfiguration.LOGGER.debug("configuring Vaadin WebSecurity");

		// allow js to work from within the iframe
		http.headers()
			.frameOptions()
			.sameOrigin();

		if (applicationNeedsRole) {
			VaadinSecurityConfiguration.LOGGER.debug("configuring vaadin client security with roles required");
			http.authorizeRequests()
				.anyRequest()
				.authenticated()
				.accessDecisionManager(new AnyRoleAccessDecisionManager());
		} else {
			VaadinSecurityConfiguration.LOGGER.debug("configuring vaadin client security with no roles required");
		}

	}
}
