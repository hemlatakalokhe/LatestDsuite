package de.bonprix.jersey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import de.bonprix.filter.RequestIdProvider;
import de.bonprix.filter.WebserviceExceptionClientResponseFilter;
import de.bonprix.jersey.ClientFactoryConfig.ClientSideLogLevel;
import de.bonprix.security.AuthKeyAuthenticator;
import de.bonprix.security.BasicAuthenticator;
import de.bonprix.security.RootAuthKeyProvider;
import de.bonprix.user.service.AuthenticationService;

/**
 * The jersey client factory is responsible for creating webservice proxies.
 *
 * @author cthiel
 *
 */
public class JerseyClientFactory implements JaxRsClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyClientFactory.class);

	private final String defaultAuthUsername;
	private final String defaultAuthPassword;
	private AuthenticationService defaultAuthenticationService;

	public JerseyClientFactory() {
		this(null, null);
	}

	public JerseyClientFactory(final String defaultAuthUsername, final String defaultAuthPassword) {
		super();
		this.defaultAuthUsername = defaultAuthUsername;
		this.defaultAuthPassword = defaultAuthPassword;
	}

	/**
	 * @param defaultAuthUsername
	 * @param localizeWsPassword
	 * @param authenticationService
	 */
	public JerseyClientFactory(final String defaultAuthUsername, final String defaultAuthPassword,
			final AuthenticationService authenticationService) {
		this(defaultAuthUsername, defaultAuthPassword);
		setDefaultAuthenticationService(authenticationService);
	}

	public AuthenticationService getDefaultAuthenticationService() {
		return this.defaultAuthenticationService;
	}

	public void setDefaultAuthenticationService(final AuthenticationService defaultAuthenticationService) {
		this.defaultAuthenticationService = defaultAuthenticationService;
	}

	@Override
	public <E> E createClient(final String uri, final Class<E> clazz, final ClientFactoryConfig config) {
		JerseyClientFactory.LOGGER.info("creating JAX-RS proxy client for class " + clazz + " targeting URL " + uri);

		final Configuration configuration = new ClientConfig().register(ObjectMapperProvider.class)
			.register(JacksonFeature.class)
			.register(WebserviceExceptionClientResponseFilter.class);

		final Client client = ClientBuilder.newClient(configuration);

		client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,
						config.isSuppressHttpComplianceValidation());

		// only add the authenticator filter to service proxies NOT proxying the
		// AuthenticationService. Would result in StackOverflowException..
		if (config.isAddAuthKeyAuthentication() && !clazz.equals(AuthenticationService.class)) {
			if (Strings.isNullOrEmpty(this.defaultAuthUsername) || Strings.isNullOrEmpty(this.defaultAuthPassword)) {
				throw new IllegalStateException(
						"AuthKeyAuthentication can only be used when the factory is initialized with a default username and password");
			}
			if (this.defaultAuthenticationService == null) {
				throw new IllegalStateException(
						"AuthKeyAuthentication can only be used when the factory is initialized with a default authenticationServcice");
			}
			client.register(new AuthKeyAuthenticator(this.defaultAuthUsername, this.defaultAuthPassword,
					this.defaultAuthenticationService));
		}
		if (config.isAddBasicAuthentication()) {
			if (Strings.isNullOrEmpty(this.defaultAuthUsername) || Strings.isNullOrEmpty(this.defaultAuthPassword)) {
				throw new IllegalStateException(
						"AuthKeyAuthentication can only be used when the factory is initialized with a default username and password");
			}
			client.register(new BasicAuthenticator(this.defaultAuthUsername, this.defaultAuthPassword));
		}

		return JerseyClientFactory.getSpringWrapper(uri, clazz, config, client);
	}

	private static <E> E getSpringWrapper(final String uri, final Class<E> clazz, final ClientFactoryConfig config,
			final Client client) {
		client.register(new RootAuthKeyProvider());
		client.register(new RequestIdProvider());

		final WebTarget webTarget = client.target(uri);

		return JerseyClientFactory.springWrapper(uri, WebResourceFactory.newResource(clazz, webTarget), clazz, config);
	}

	@SuppressWarnings({ "unchecked" })
	private static <E> E springWrapper(final String uri, final E proxy, final Class<E> serviceClazz,
			final ClientFactoryConfig config) {
		return (E) Proxy.newProxyInstance(proxy.getClass()
			.getClassLoader(), new Class[] { serviceClazz }, (proxy1, method, args) -> {
				if ("hashCode".equals(method.getName())) {
					return System.identityHashCode(proxy1);
				} else {
					final String methodName = serviceClazz.getName() + "." + method.getName();

					try {
						final Logger logger = LoggerFactory.getLogger(methodName);
						if (config.getClientSideLogging() == ClientSideLogLevel.METHOD_TIME) {
							final long start = System.currentTimeMillis();
							final Object result = method.invoke(proxy, args);
							final long duration = System.currentTimeMillis() - start;

							logger.debug(duration + "ms - " + methodName + "");
							return result;
						} else {
							logger.debug(methodName);
							return method.invoke(proxy, args);
						}
					} catch (final ProcessingException e) {
						JerseyClientFactory.LOGGER.error("Unable to process HTTP call to " + uri + " / " + methodName);

						throw e;
					} catch (final InvocationTargetException e) {
						JerseyClientFactory.LOGGER.error(e.getLocalizedMessage(), e);
						/*
						 * catch the invocation target exception, hide it and
						 * throw the inner exception which really occurred.
						 * Otherwise it would result in a more unreadable
						 * stacktrace and a UndeclaredThrowableException in the
						 * wrapping proxy.
						 */
						throw e.getTargetException();
					}
				}
			});
	}

}
