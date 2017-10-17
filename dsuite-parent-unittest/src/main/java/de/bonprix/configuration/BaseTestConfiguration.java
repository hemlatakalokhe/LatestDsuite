package de.bonprix.configuration;

import java.util.concurrent.ForkJoinPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.bonprix.spring.ConfigurationProfile;

/**
 * Base configuration for all unit tests classes; contains all beans, which can
 * be potentially needed by each test class
 */
@Configuration
@Profile(ConfigurationProfile.UNITTEST)
public class BaseTestConfiguration {

	@Bean
	public ForkJoinPool forkJoinPool() {
		return new ForkJoinPool(5);
	}
}
