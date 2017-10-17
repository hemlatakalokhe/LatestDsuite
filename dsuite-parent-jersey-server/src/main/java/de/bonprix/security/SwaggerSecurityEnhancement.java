/**
 *
 */
package de.bonprix.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;

import de.bonprix.configuration.SecurityConfigurationEnhancement;
import de.bonprix.exception.SecurityConfigurationEnhancementException;

/**
 * @author cthiel
 * @date 29.10.2016
 *
 */
@Component
public class SwaggerSecurityEnhancement implements SecurityConfigurationEnhancement {

	@Override
	public void configure(final HttpSecurity http) throws SecurityConfigurationEnhancementException {
		// empty
	}

	@Override
	public void configure(final WebSecurity web) throws SecurityConfigurationEnhancementException {
		web.ignoring()
			.antMatchers("/apidocs/**");
	}

}
