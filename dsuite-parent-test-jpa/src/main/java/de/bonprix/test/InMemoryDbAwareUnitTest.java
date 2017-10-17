package de.bonprix.test;

import java.lang.reflect.InvocationTargetException;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import de.bonprix.BaseConfiguredTransactionalUnitTest;
import de.bonprix.configuration.InMemoryJpaConfig;
import de.bonprix.sqlcount.SQLStatementCountValidator;

/**
 * Class providing additionally to base unit test setup
 * in @BaseConfiguredTransactionalUnitTest in-memory database support
 * implemented in @InMemoryJpaConfig
 * 
 * @author vbaghdas
 * 
 */

@ContextConfiguration(classes = { InMemoryJpaConfig.class })
public abstract class InMemoryDbAwareUnitTest extends BaseConfiguredTransactionalUnitTest {

	@Override
	@BeforeMethod
	public void initTests() throws IllegalAccessException, InvocationTargetException {
		super.initTests();
		SQLStatementCountValidator.reset();
	}
}
