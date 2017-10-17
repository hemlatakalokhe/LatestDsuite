package de.bonprix.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import de.bonprix.exception.SecurityConfigurationEnhancementException;

/**
 * Interface for custom security configuration enhancements, which are applied
 * to client calls; main configuration is defined in
 * {@link AbstractSecurityConfiguration}
 */
public interface SecurityConfigurationEnhancement {

	/**
	 * Appends custom configuration from http parameter to the main http in
	 * {@link AbstractSecurityConfiguration#configure}
	 */
	public void configure(final HttpSecurity http) throws SecurityConfigurationEnhancementException;

	/**
	 * Appends custom configuration from http parameter to the main http in
	 * {@link AbstractSecurityConfiguration#configure}
	 */
	public void configure(final WebSecurity web) throws SecurityConfigurationEnhancementException;

}
