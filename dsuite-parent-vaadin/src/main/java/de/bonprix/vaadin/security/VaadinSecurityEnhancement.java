package de.bonprix.vaadin.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;

import de.bonprix.configuration.SecurityConfigurationEnhancement;
import de.bonprix.exception.SecurityConfigurationEnhancementException;

@Component
public class VaadinSecurityEnhancement implements SecurityConfigurationEnhancement {

	@Override
	public void configure(final HttpSecurity http) throws SecurityConfigurationEnhancementException {
	}

	@Override
	public void configure(final WebSecurity web) throws SecurityConfigurationEnhancementException {
		web.ignoring()
			.antMatchers("/HEARTBEAT/**")
			.antMatchers("/VAADIN/**");
	}

}
