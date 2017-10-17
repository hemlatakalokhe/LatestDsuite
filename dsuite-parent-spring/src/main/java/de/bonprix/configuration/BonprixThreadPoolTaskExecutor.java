package de.bonprix.configuration;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;

import de.bonprix.GeneralApplicationInformation;
import de.bonprix.logging.LogUtil;
import de.bonprix.security.AuthenticationUtil;
import de.bonprix.security.AuthorizationKeyStorage;
import de.bonprix.security.BonprixAuthentication;
import de.bonprix.security.PrincipalSecurityContext;

/**
 * Customized @ThreadPoolTaskExecutor class for setting spring security context
 * for @Async annotated methods
 *
 * @author vbaghdas
 *
 */
@SuppressWarnings("serial")
public class BonprixThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	private static final Logger LOGGER = LoggerFactory.getLogger(BonprixThreadPoolTaskExecutor.class);

	private final String loggingConfigurationApp;

	private final String loggingConfigurationEnvironment;

	private final GeneralApplicationInformation generalApplicationInformation;

	public BonprixThreadPoolTaskExecutor(String loggingConfigurationApp, String loggingConfigurationEnvironment,
			GeneralApplicationInformation generalApplicationInformation) {
		this.loggingConfigurationApp = loggingConfigurationApp;
		this.loggingConfigurationEnvironment = loggingConfigurationEnvironment;
		this.generalApplicationInformation = generalApplicationInformation;
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		ExecutorService executor = getThreadPoolExecutor();

		final BonprixAuthentication token = new BonprixAuthentication(
				PrincipalSecurityContext.getAuthenticatedPrincipal(), AuthorizationKeyStorage.getAuthorizationKey(),
				null);
		try {
			return executor.submit(new Callable<T>() {
				@Override
				public T call() throws Exception {
					try {
						token.setAuthenticated(true);
						SecurityContextHolder.getContext()
							.setAuthentication(token);

						LogUtil.setLoggingProperties(	BonprixThreadPoolTaskExecutor.this.loggingConfigurationApp,
														BonprixThreadPoolTaskExecutor.this.loggingConfigurationEnvironment,
														BonprixThreadPoolTaskExecutor.this.generalApplicationInformation
															.getApplicationContextPath());

						return task.call();
					} catch (Exception e) {
						BonprixThreadPoolTaskExecutor.LOGGER.error(e.getLocalizedMessage(), e);
						return null;
					} finally {
						AuthenticationUtil.unsetPrincipal();

						LogUtil
							.unsetLoggingProperties(BonprixThreadPoolTaskExecutor.this.loggingConfigurationApp,
													BonprixThreadPoolTaskExecutor.this.loggingConfigurationEnvironment);
					}
				}
			});
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
		}
	}
}
