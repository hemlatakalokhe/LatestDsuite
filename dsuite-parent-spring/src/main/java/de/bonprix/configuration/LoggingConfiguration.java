package de.bonprix.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/default-logging.properties")
public class LoggingConfiguration {

    public static final String APPLICATION = "APPLICATION";
    public static final String REQUEST_ID = "REQUEST_ID";
    public static final String REQUEST_URL = "REQUESTURL";
    public static final String ENVIRONMENT = "ENV";
    public static final String AUTH_PRINCIPAL = "AUTH_PRINCIPAL";
    public static final String ROOT_PRINCIPAL = "ROOT_PRINCIPAL";

}
