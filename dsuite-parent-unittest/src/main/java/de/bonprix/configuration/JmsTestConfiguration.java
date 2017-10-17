package de.bonprix.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;

import de.bonprix.spring.ConfigurationProfile;

/**
 * Configuration for jms support used in unit tests
 */
@Configuration
@Profile(ConfigurationProfile.UNITTEST)
public class JmsTestConfiguration {

	@Bean
	public JmsTemplate jmsTemplate() {
		return Mockito.mock(JmsTemplate.class);
	}

}
