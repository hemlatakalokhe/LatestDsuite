package de.bonprix.configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.bonprix.jpa.BpTransactionManager;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:/jpa-default.properties")
public class JpaConfiguration {

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new BpTransactionManager(emf);
	}

}
