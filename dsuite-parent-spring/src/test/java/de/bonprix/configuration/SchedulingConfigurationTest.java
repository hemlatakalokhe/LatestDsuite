package de.bonprix.configuration;

import java.util.concurrent.Executor;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.GeneralApplicationInformation;
import de.bonprix.user.service.AuthenticationService;

public class SchedulingConfigurationTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private SchedulingConfiguration schedulingConfiguration;

	@Mock
	private AuthenticationService authenticationService;

	@Mock
	private GeneralApplicationInformation generalApplicationInformation;

	private BonprixThreadPoolTaskExecutor spyExecutor;

	private BonprixThreadPoolTaskExecutor executor;

	private final String loggingConfigurationApp = "testLoggingConfigurationApp";

	private final String loggingConfigurationEnvironment = "testLoggingConfigurationEnvironment";

	private GeneralApplicationInformation mockGeneralApplicationInformation;

	private SchedulingConfiguration spySchedulingConfiguration;

	@BeforeMethod
	public void setUp() {
		this.mockGeneralApplicationInformation = Mockito.mock(GeneralApplicationInformation.class);
		this.executor = new BonprixThreadPoolTaskExecutor(this.loggingConfigurationApp,
				this.loggingConfigurationEnvironment, this.mockGeneralApplicationInformation);
		this.spyExecutor = Mockito.spy(this.executor);
		this.spySchedulingConfiguration = Mockito.spy(this.schedulingConfiguration);
	}

	@Test
	public void testGetAsyncExecutor() throws Exception {
		PowerMockito.doReturn(this.spyExecutor)
			.when(this.spySchedulingConfiguration)
			.createExecutor();

		PowerMockito.doNothing()
			.when(this.spySchedulingConfiguration)
			.initExecutor(this.spyExecutor);

		Assert.assertEquals(this.spyExecutor, this.spySchedulingConfiguration.getAsyncExecutor());
	}

	@Test
	public void testTaskExecutor() throws Exception {
		PowerMockito.doNothing()
			.when(this.spySchedulingConfiguration)
			.executeSetPrincipal();

		Assert.assertNotNull(this.spySchedulingConfiguration.taskExecutor());

		PowerMockito.verifyPrivate(this.spySchedulingConfiguration)
			.invoke("createSchedulerSecurityContext");
		PowerMockito.verifyPrivate(this.spySchedulingConfiguration)
			.invoke("executeSetPrincipal");
	}

	@Test
	public void testGetAsyncUncaughtExceptionHandler() {
		Assert.assertNull(this.spySchedulingConfiguration.getAsyncUncaughtExceptionHandler());
	}

	@Test
	public void testConfigureTasks() throws Exception {
		ScheduledTaskRegistrar mockScheduledTaskRegistrar = Mockito.mock(ScheduledTaskRegistrar.class);
		Executor executor = Mockito.mock(Executor.class);

		PowerMockito.doReturn(executor)
			.when(this.spySchedulingConfiguration)
			.executeTaskExecutor();

		this.spySchedulingConfiguration.configureTasks(mockScheduledTaskRegistrar);

		PowerMockito.verifyPrivate(this.spySchedulingConfiguration)
			.invoke("executeTaskExecutor");
		Mockito.verify(mockScheduledTaskRegistrar)
			.setScheduler(executor);
	}
}
