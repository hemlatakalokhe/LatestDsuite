package de.bonprix;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import de.bonprix.annotation.RestService;
import de.bonprix.exception.WebserviceExceptionMapper;
import de.bonprix.jersey.ObjectMapperProvider;

@Configuration
@PropertySource("classpath:/default-jersey.properties")
public class JerseyServlet extends ResourceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyServlet.class);

	@Resource
	private ApplicationContext context;

	public JerseyServlet() {
		// empty
	}

	@PostConstruct
	public void postConstruct() {
		JerseyServlet.LOGGER.info("Starting Rest-Service context");

		for (final Map.Entry<String, Object> entry : this.context.getBeansWithAnnotation(RestService.class)
			.entrySet()) {
			JerseyServlet.LOGGER.info("Registered Rest-Service " + entry.getValue()
				.getClass());
			register(entry.getValue());
		}
		register(ObjectMapperProvider.class);
		register(WebserviceExceptionMapper.class);

		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}