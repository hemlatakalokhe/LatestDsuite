/**
 *
 */
package de.bonprix.configuration;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.jms.BonprixJmsListenerContainerFactory;
import de.bonprix.jms.BonprixMessageConverter;
import de.bonprix.user.service.AuthenticationService;

/**
 * Main JMS configuration class.
 *
 * @author cthiel
 * @date 11.11.2016
 *
 */
@Configuration
@EnableJms
@EnableConfigurationProperties(ActiveMQProperties.class)
@PropertySource("classpath:/default-jms.properties")
public class JmsConfiguration {

	public static final String DEFAULT_QUEUE_CONTAINER_FACTORY = "defaultQueueContainerFactory";
	public static final String DEFAULT_TOPIC_CONTAINER_FACTORY = "defaultTopicContainerFactory";

	@Value("${jms.cachedConnectionPool.cacheSize}")
	private Integer cacheSize;

	@Bean(name = JmsConfiguration.DEFAULT_QUEUE_CONTAINER_FACTORY)
	JmsListenerContainerFactory<?> defaultQueueContainerFactory(final Environment environment,
			@Qualifier("connectionFactory") final ConnectionFactory connectionFactory,
			final AuthenticationService authenticationService, final GeneralApplicationInformation appInfo) {
		final String systemuserUsername = environment.getProperty("application.systemuser.username");
		final String systemuserPassword = environment.getProperty("application.systemuser.password");

		final BonprixJmsListenerContainerFactory factory = new BonprixJmsListenerContainerFactory(appInfo);
		factory.setConnectionFactory(connectionFactory);
		factory.setAuthenticationDetails(authenticationService, systemuserUsername, systemuserPassword);
		return factory;
	}

	@Bean(name = JmsConfiguration.DEFAULT_TOPIC_CONTAINER_FACTORY)
	JmsListenerContainerFactory<?> defaultTopicContainerFactory(final Environment environment,
			@Qualifier("connectionFactory") final ConnectionFactory connectionFactory,
			final AuthenticationService authenticationService, final GeneralApplicationInformation appInfo) {
		final String systemuserUsername = environment.getProperty("application.systemuser.username");
		final String systemuserPassword = environment.getProperty("application.systemuser.password");

		final BonprixJmsListenerContainerFactory factory = new BonprixJmsListenerContainerFactory(appInfo);
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(connectionFactory);
		factory.setAuthenticationDetails(authenticationService, systemuserUsername, systemuserPassword);
		return factory;
	}

	@Bean(name = "connectionFactory")
	public CachingConnectionFactory cachedConnectionFactory(final ActiveMQProperties activeMQProperties,
			final Environment environment) {
		final CachingConnectionFactory cachingConnFactory = new CachingConnectionFactory();
		cachingConnFactory.setTargetConnectionFactory(connectionFactory(activeMQProperties, environment));
		cachingConnFactory.setSessionCacheSize(this.cacheSize);
		return cachingConnFactory;
	}

	public ConnectionFactory connectionFactory(final ActiveMQProperties activeMQProperties,
			final Environment environment) {
		final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(activeMQProperties.getUser(),
				activeMQProperties.getPassword(), activeMQProperties.getBrokerUrl());
		factory.setTrustedPackages(new ArrayList<>(
				Arrays.asList(environment.getProperty("org.apache.activemq.SERIALIZABLE_PACKAGES")
					.split(","))));
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate(@Qualifier("connectionFactory") final ConnectionFactory connectionFactory) {
		final JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory);
		template.setSessionTransacted(true);
		template.setMessageConverter(new BonprixMessageConverter()); // set our
																		// own
																		// message
																		// converter
																		// to
																		// add
																		// some
																		// BP
																		// specific
																		// meta
																		// fields
		return template;
	}

}
