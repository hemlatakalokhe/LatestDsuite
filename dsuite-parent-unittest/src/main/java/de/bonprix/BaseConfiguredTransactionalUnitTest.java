package de.bonprix;

import java.lang.reflect.InvocationTargetException;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.configuration.BaseTestConfiguration;
import de.bonprix.spring.ConfigurationProfile;

/**
 * Class for minimal needed setup done for unit test execution; a dummy security
 * context is used and database connectivity is provided; use this class in case
 * of transactionality in database change operations is needed
 *
 * 1) setting of dummy client security
 *
 * 2) injection of mocks (marked as @Mock in the test class) into the object
 * under test (marked as @InjectMocks in the test class)
 *
 * 3) For all fields annotated with @InjectMocks in the test case (also super
 * classes) and in proper classes any method annotated with @PostConstruct are
 * search and executed
 *
 * @author vbaghdas
 */

@ActiveProfiles({ ConfigurationProfile.UNITTEST })
@Test(groups = "UNITTEST")
@Rollback
@ContextConfiguration(classes = { BaseTestConfiguration.class })
public abstract class BaseConfiguredTransactionalUnitTest extends AbstractTransactionalTestNGSpringContextTests {

	@BeforeMethod
	public void initTests() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BaseTestConfigurator.initTests(this);
	}
}
