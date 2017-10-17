package de.bonprix.configuration;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.concurrent.DelegatingSecurityContextScheduledExecutorService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.logging.LogUtil;
import de.bonprix.security.AuthenticationUtil;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.user.service.AuthenticationService;

@Configuration
@EnableScheduling
@EnableAsync
public class SchedulingConfiguration implements SchedulingConfigurer, AsyncConfigurer {

	@Value("${scheduler.corePoolSize}")
	protected Integer corePoolSize;

	@Value("${scheduler.maxPoolSize}")
	protected Integer maxPoolSize;

	@Value("${application.systemuser.username}")
	private String sysUserName;

	@Value("${application.systemuser.password}")
	private String sysUserPswd;

	private static final Long AUTH_KEY_CACHE_TTL = 300000L;

	private LocalDateTime authKeyCacheExpireDate = null;

	private BonprixAuthentication token = null;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private GeneralApplicationInformation generalApplicationInformation;

	@Override
	public Executor getAsyncExecutor() {
		final BonprixThreadPoolTaskExecutor executor = createExecutor();
		initExecutor(executor);

		return executor;
	}

	void initExecutor(final BonprixThreadPoolTaskExecutor executor) {
		executor.setCorePoolSize(this.corePoolSize);
		executor.setMaxPoolSize(this.maxPoolSize);
		executor.setQueueCapacity(1000);
		executor.setThreadNamePrefix("MyExecutor-");
		executor.initialize();
	}

	BonprixThreadPoolTaskExecutor createExecutor() {
		final BonprixThreadPoolTaskExecutor executor = new BonprixThreadPoolTaskExecutor(
				LoggingConfiguration.APPLICATION, LoggingConfiguration.ENVIRONMENT, this.generalApplicationInformation);
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

	@Override
	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(executeTaskExecutor());
	}

	Executor executeTaskExecutor() {
		return taskExecutor();
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		AuthenticationUtil.unsetPrincipal();
		LogUtil.unsetLoggingProperties(LoggingConfiguration.APPLICATION, LoggingConfiguration.ENVIRONMENT);

		LogUtil.setLoggingProperties(	LoggingConfiguration.APPLICATION, LoggingConfiguration.ENVIRONMENT,
										this.generalApplicationInformation.getApplicationContextPath());
		return new DelegatingSecurityContextScheduledExecutorService(Executors.newSingleThreadScheduledExecutor(),
				createSchedulerSecurityContext());
	}

	private SecurityContext createSchedulerSecurityContext() {
		executeSetPrincipal();
		// set instance variable to created @BonprixAuthentication to avoid
		// create it again, because this is redundant
		this.token = (BonprixAuthentication) SecurityContextHolder.getContext()
			.getAuthentication();
		return SecurityContextHolder.getContext();
	}

	void executeSetPrincipal() {
		AuthenticationUtil.setPrincipal(this.sysUserName, this.sysUserPswd, this.authenticationService,
										this.generalApplicationInformation.getApplicationId(), AUTH_KEY_CACHE_TTL,
										this.token, this.authKeyCacheExpireDate);
	}

}