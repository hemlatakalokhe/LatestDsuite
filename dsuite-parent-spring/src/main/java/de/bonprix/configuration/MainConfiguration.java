/**
 *
 */
package de.bonprix.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author cthiel
 * @date 07.11.2016
 *
 */
@Configuration
@PropertySources({ @PropertySource("classpath:/default-spring.properties"), @PropertySource("classpath:/default-build.properties") })
public class MainConfiguration {

}
