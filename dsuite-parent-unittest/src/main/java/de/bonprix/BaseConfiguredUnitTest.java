package de.bonprix;

import java.lang.reflect.InvocationTargetException;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.bonprix.configuration.BaseTestConfiguration;
import de.bonprix.spring.ConfigurationProfile;

/**
 * Class for minimal needed setup done for unit test execution; a dummy security
 * context is used and no database connectivity provided; use this class in case
 * of no transactionality in database change operations is needed
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
@ContextConfiguration(classes = { BaseTestConfiguration.class })
public abstract class BaseConfiguredUnitTest extends AbstractTestNGSpringContextTests {

	@BeforeMethod
	public void initTests() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BaseTestConfigurator.initTests(this);
	}

}
