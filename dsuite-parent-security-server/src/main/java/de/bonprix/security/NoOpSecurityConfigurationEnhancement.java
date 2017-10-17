package de.bonprix.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;

import de.bonprix.configuration.SecurityConfigurationEnhancement;
import de.bonprix.exception.SecurityConfigurationEnhancementException;

/**
 *
 * @author cthiel
 * @date 28.10.2016
 *
 */
@Component
public class NoOpSecurityConfigurationEnhancement implements SecurityConfigurationEnhancement {

	@Override
	public void configure(final HttpSecurity http) throws SecurityConfigurationEnhancementException {
	}

	@Override
	public void configure(final WebSecurity web) throws SecurityConfigurationEnhancementException {
	}

}
