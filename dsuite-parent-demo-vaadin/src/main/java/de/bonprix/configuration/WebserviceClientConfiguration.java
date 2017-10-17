package de.bonprix.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.bonprix.base.demo.service.ApplicationService;
import de.bonprix.base.demo.service.ClientService;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.LoginService;
import de.bonprix.base.demo.service.PlanperiodService;
import de.bonprix.base.demo.service.SeasonService;
import de.bonprix.base.demo.service.SecurityDemoService;
import de.bonprix.base.demo.service.StyleOverViewFilterService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.jersey.ClientFactoryConfig;
import de.bonprix.jersey.ClientFactoryConfig.ClientSideLogLevel;
import de.bonprix.jersey.JaxRsClientFactory;

@Configuration
public class WebserviceClientConfiguration {

    @Value("${webservice.url.demo}")
    private String webserviceDemoUrl;

    @Bean
    public SecurityDemoService securityDemoService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, SecurityDemoService.class, config);
    }

    @Bean
    public ApplicationService applicationService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, ApplicationService.class, config);
    }

    @Bean
    public PlanperiodService planperiodService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, PlanperiodService.class, config);
    }

    @Bean
    public StyleService styleService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, StyleService.class, config);
    }

    @Bean
    public CountryService countryService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, CountryService.class, config);
    }

    @Bean
    public LoginService loginService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, LoginService.class, config);
    }

    @Bean
    public SeasonService seasonService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, SeasonService.class, config);
    }

    @Bean
    public ClientService clientService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, ClientService.class, config);
    }

    @Bean
    public StyleOverViewFilterService styleOverViewFilterService(final JaxRsClientFactory jaxRsClientFactory) {
        final ClientFactoryConfig config = new ClientFactoryConfig();
        config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

        return jaxRsClientFactory.createClient(this.webserviceDemoUrl, StyleOverViewFilterService.class, config);
    }

}
