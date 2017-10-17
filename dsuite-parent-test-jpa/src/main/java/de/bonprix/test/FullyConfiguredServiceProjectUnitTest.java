package de.bonprix.test;

import java.lang.reflect.InvocationTargetException;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import de.bonprix.BaseConfiguredTransactionalUnitTest;
import de.bonprix.configuration.InMemoryJpaConfig;
import de.bonprix.configuration.JmsTestConfiguration;
import de.bonprix.sqlcount.SQLStatementCountValidator;

/**
 * Full base setup for unit tests needed for service maven projects; provides
 * additionally to base unit test setup in @BaseConfiguredTransactionalUnitTest
 * JMS support implemented in @JmsTestConfiguration and in-memory database
 * support implemented in @InMemoryJpaConfig
 * 
 * @author vbaghdas
 * 
 */

@ContextConfiguration(classes = { InMemoryJpaConfig.class, JmsTestConfiguration.class })
public class FullyConfiguredServiceProjectUnitTest extends BaseConfiguredTransactionalUnitTest {

	@Override
	@BeforeMethod
	public void initTests() throws IllegalAccessException, InvocationTargetException {
		super.initTests();
		SQLStatementCountValidator.reset();
	}

}
