package de.bonprix.configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.GeneralApplicationInformation;

public class BonprixThreadPoolTaskExecutorTest extends BaseConfiguredUnitTest {

	private BonprixThreadPoolTaskExecutor spyExecutor;

	private BonprixThreadPoolTaskExecutor executor;

	private final String loggingConfigurationApp = "testLoggingConfigurationApp";

	private final String loggingConfigurationEnvironment = "testLoggingConfigurationEnvironment";

	private GeneralApplicationInformation mockGeneralApplicationInformation;

	@BeforeMethod
	public void setUp() {
		this.mockGeneralApplicationInformation = Mockito.mock(GeneralApplicationInformation.class);
		this.executor = new BonprixThreadPoolTaskExecutor(this.loggingConfigurationApp,
				this.loggingConfigurationEnvironment, this.mockGeneralApplicationInformation);
		this.spyExecutor = Mockito.spy(this.executor);

	}

	@Test
	public void testSubmit() throws Exception {
		Callable<?> mockCallable = Mockito.mock(Callable.class);
		ThreadPoolExecutor mockedThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.DAYS,
				new ArrayBlockingQueue<Runnable>(1));

		Mockito	.doReturn(mockedThreadPoolExecutor)
				.when(this.spyExecutor)
				.getThreadPoolExecutor();

		Assert.assertNotNull(this.spyExecutor.submit(mockCallable));
	}

	@Test
	public void smokeTestCallWithinSubmit() throws Exception {
		Callable<?> mockCallable = Mockito.mock(Callable.class);
		ThreadPoolExecutor mockedThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.DAYS,
				new ArrayBlockingQueue<Runnable>(1));

		Mockito	.doReturn(mockedThreadPoolExecutor)
				.when(this.spyExecutor)
				.getThreadPoolExecutor();

		this.spyExecutor.submit(mockCallable)
						.get();

	}
}
