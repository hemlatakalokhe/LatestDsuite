package de.bonprix.configuration;

import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import de.bonprix.information.service.SimpleApplicationService;
import de.bonprix.information.service.SimpleClientService;
import de.bonprix.jersey.ClientFactoryConfig;
import de.bonprix.jersey.ClientFactoryConfig.ClientSideLogLevel;
import de.bonprix.jersey.JaxRsClientFactory;
import de.bonprix.jersey.JerseyClientFactory;
import de.bonprix.security.BonprixAuthenticationEntryPoint;
import de.bonprix.security.BonprixRememberMeAuthenticationProvider;
import de.bonprix.security.BonprixStripAuthKeySuccessHandler;
import de.bonprix.security.service.CachedAuthenticationService;
import de.bonprix.user.dto.PreferencesService;
import de.bonprix.user.service.AuthenticationService;

/**
 * Base class for server security. This class is configuring basics of web
 * security for applications in dsuite-parent. This class does NOT apply some
 * security filter. Therefore it should be subclassed. Current subclasses are
 * for example VaadinSecurityConfiguration or WebserviceSecurityConfiguration.
 * <br/>
 * <br/>
 * This class also scans for all beans implementing the interface
 * {@link SecurityConfigurationEnhancement} and applies the {@link HttpSecurity}
 * to these beans for application-custom security configuration.
 */
@PropertySource("classpath:/default-security.properties")
public abstract class AbstractSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityConfiguration.class);

	@Resource
	protected Environment environment;

	@Value("${user.ws.username}")
	protected String authenticationUsername;

	@Value("${user.ws.password}")
	protected String authenticationPassword;

	@Value("${user.ws.url}")
	protected String authenticationUrl;

	@Value("${application.id}")
	protected Long applicationId;

	@Value("${masterdata.ws.url}")
	private String webserviceUrlMasterdata;

	@Resource
	private List<SecurityConfigurationEnhancement> securityConfigurationEnhancements;

	@PostConstruct
	public void init() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.addFilter(securityContextPersistenceFilter());
		http.csrf()
			.disable();

		for (final SecurityConfigurationEnhancement enhancement : this.securityConfigurationEnhancements) {
			AbstractSecurityConfiguration.LOG.debug("Applying http security enhancement " + enhancement.getClass());
			enhancement.configure(http);
		}
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		for (final SecurityConfigurationEnhancement enhancement : this.securityConfigurationEnhancements) {
			AbstractSecurityConfiguration.LOG.debug("Applying web security enhancement " + enhancement.getClass());
			enhancement.configure(web);
		}
	}

	/**
	 * Central bean definition of the authenticationService. By default this
	 * should be a caching authentication service.
	 *
	 * @param delegatedAuthenticationService
	 * @return the authentication service
	 * @throws URISyntaxException
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public AuthenticationService authenticationService(
			@Qualifier("delegatedAuthenticationService") final AuthenticationService delegatedAuthenticationService)
			throws URISyntaxException {
		final CachedAuthenticationService bean = new CachedAuthenticationService();
		bean.setDelegatedAuthenticationService(delegatedAuthenticationService);
		return bean;
	}

	/**
	 * Central bean definition of the real authentication service.
	 *
	 * @return the authentication service
	 * @throws URISyntaxException
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public AuthenticationService delegatedAuthenticationService(
			@Qualifier("securityJaxRsClientFactory") final JaxRsClientFactory securityJaxRsClientFactory)
			throws URISyntaxException {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);
		config.setAddBasicAuthentication(true);
		return securityJaxRsClientFactory.createClient(this.authenticationUrl, AuthenticationService.class, config);
	}

	/**
	 * Central bean definition of the real authentication service.
	 *
	 * @return the authentication service
	 * @throws URISyntaxException
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public PreferencesService preferencesService(
			@Qualifier("securityJaxRsClientFactory") final JaxRsClientFactory securityJaxRsClientFactory)
			throws URISyntaxException {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);
		config.setAddAuthKeyAuthentication(false);
		config.setAddBasicAuthentication(true);
		return securityJaxRsClientFactory.createClient(this.authenticationUrl, PreferencesService.class, config);
	}

	@Bean
	public AuthenticationSuccessHandler bonprixStripAuthKeySuccessHandler() {
		return new BonprixStripAuthKeySuccessHandler();
	}

	@Bean
	public BonprixRememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new BonprixRememberMeAuthenticationProvider();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new BonprixAuthenticationEntryPoint(this.applicationId, this.authenticationUrl + "/ui/login");
	}

	@Bean
	public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		return new SecurityContextPersistenceFilter();
	}

	@Bean
	public JaxRsClientFactory securityJaxRsClientFactory(final Environment environment) {
		return new JerseyClientFactory(this.authenticationUsername, this.authenticationPassword);
	}

	@Bean
	public SimpleApplicationService dsuiteApplicationService(
			@Qualifier("dsuiteParentJaxRsClientFactory") final JaxRsClientFactory dsuiteParentJaxRsClientFactory) {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

		return dsuiteParentJaxRsClientFactory.createClient(	this.webserviceUrlMasterdata, SimpleApplicationService.class,
															config);
	}

	@Bean
	public SimpleClientService dsuiteClientService(
			@Qualifier("dsuiteParentJaxRsClientFactory") final JaxRsClientFactory dsuiteParentJaxRsClientFactory) {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

		return dsuiteParentJaxRsClientFactory.createClient(	this.webserviceUrlMasterdata, SimpleClientService.class,
															config);
	}

}
