package de.bonprix;

import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.vaadin.server.VaadinServlet;

import de.bonprix.information.ClientProvider;
import de.bonprix.security.PrincipalProvider;
import de.bonprix.vaadin.layout.BpFooter;
import de.bonprix.vaadin.layout.BpMenu;
import de.bonprix.vaadin.layout.BpMenuLayout;
import de.bonprix.vaadin.spring.BpUI;
import de.bonprix.vaadin.spring.BpVaadinServlet;

@SpringBootApplication
@PropertySource("classpath:/default-vaadin.properties")
@EnableAsync
public class VaadinApplication extends SpringBootServletInitializer {

	@Value("${forkjoinpool.parallelism}")
	private int forkjoinpoolParallelism;

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(VaadinApplication.class);
	}

	public static void main(final String[] args) throws Exception {
		SpringApplication.run(VaadinApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	public VaadinServlet vaadinServlet() {
		return new BpVaadinServlet();
	}

	@Bean
	@com.vaadin.spring.annotation.UIScope
	BpUI bpUI() {
		return new BpUI();
	}

	@Bean
	@com.vaadin.spring.annotation.UIScope
	BpMenuLayout bpMenuLayout() {
		return new BpMenuLayout();
	}

	@Bean
	@com.vaadin.spring.annotation.UIScope
	BpMenu bpMenu() {
		return new BpMenu();
	}

	@Bean
	@com.vaadin.spring.annotation.UIScope
	BpFooter bpFooter(final GeneralApplicationInformation generalApplicationInformation,
			PrincipalProvider principalProvider, ClientProvider clientProvider) {
		return new BpFooter(generalApplicationInformation, principalProvider, clientProvider);
	}

	@Bean
	ForkJoinPool forkJoinPool() {
		return new ForkJoinPool(this.forkjoinpoolParallelism);
	}

}