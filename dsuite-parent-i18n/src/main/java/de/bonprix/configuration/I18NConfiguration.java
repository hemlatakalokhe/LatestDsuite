package de.bonprix.configuration;

import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import de.bonprix.LocalizeRepo;
import de.bonprix.i18n.factory.I18NLocalizerFactory;
import de.bonprix.i18n.service.SimpleApplicationLanguageService;
import de.bonprix.i18n.service.SimpleI18NKeyService;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.jersey.ClientFactoryConfig;
import de.bonprix.jersey.ClientFactoryConfig.ClientSideLogLevel;
import de.bonprix.jersey.JaxRsClientFactory;
import de.bonprix.localizer.LiveI18NLocalizer;

/**
 * Main configuration for i18n
 */
@Configuration
@PropertySource("classpath:/default-i18n.properties")
public class I18NConfiguration {

	@Value("${localize.ws.url}")
	private String localizeWsUrl;

	@Value("${masterdata.ws.url}")
	private String masterdataWsUrl;

	@Resource
	private Environment environment;

	@Bean
	public I18NLocalizerFactory i18NLocalizerFactory() {
		return () -> new LiveI18NLocalizer();
	}

	@Bean
	public MethodInvokingBean i18NLocalizerMethodInvokingBean(final I18NLocalizerFactory i18NLocalizerFactory) {
		final MethodInvokingBean result = new MethodInvokingBean();
		result.setStaticMethod("de.bonprix.I18N.setI18NLocalizerFactory");
		final Object[] newObjs = new Object[1];
		newObjs[0] = i18NLocalizerFactory;
		result.setArguments(newObjs);
		return result;
	}

	@Bean
	public MethodInvokingBean localizeFindServiceMethodInvokingBean(
			@Qualifier("dsuiteI18NKeyService") SimpleI18NKeyService i18nKeyService,
			@Qualifier("dsuiteApplicationLanguageService") SimpleApplicationLanguageService applicationLanguageService,
			ApplicationProvider applicationProvider) throws URISyntaxException {
		final MethodInvokingBean result = new MethodInvokingBean();
		result.setStaticMethod("de.bonprix.localizer.LiveI18NLocalizer.setTranslationRepo");

		final LocalizeRepo tRepo = new LocalizeRepo(i18nKeyService, applicationLanguageService, applicationProvider);

		final Object[] newObjs = new Object[1];
		newObjs[0] = tRepo;
		result.setArguments(newObjs);
		return result;
	}

	@Bean
	public SimpleI18NKeyService dsuiteI18NKeyService(
			@Qualifier("dsuiteParentJaxRsClientFactory") final JaxRsClientFactory dsuiteParentJaxRsClientFactory) {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

		return dsuiteParentJaxRsClientFactory.createClient(this.localizeWsUrl, SimpleI18NKeyService.class, config);
	}

	@Bean
	public SimpleApplicationLanguageService dsuiteApplicationLanguageService(
			@Qualifier("dsuiteParentJaxRsClientFactory") final JaxRsClientFactory dsuiteParentJaxRsClientFactory) {
		final ClientFactoryConfig config = new ClientFactoryConfig();
		config.setClientSideLogging(ClientSideLogLevel.METHOD_TIME);

		return dsuiteParentJaxRsClientFactory.createClient(	this.localizeWsUrl, SimpleApplicationLanguageService.class,
															config);
	}

}
