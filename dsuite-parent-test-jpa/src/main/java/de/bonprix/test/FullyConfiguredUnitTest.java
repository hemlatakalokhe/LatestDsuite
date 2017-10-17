package de.bonprix.test;

import java.lang.reflect.InvocationTargetException;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import de.bonprix.BaseConfiguredTransactionalUnitTest;
import de.bonprix.configuration.I18NTestConfiguration;
import de.bonprix.configuration.InMemoryJpaConfig;
import de.bonprix.sqlcount.SQLStatementCountValidator;

/**
 * @deprecated; please use @FullyConfiguredServiceProjectUnitTest
 * or @FullyConfiguredUiProjectUnitTest instead
 * 
 * @author vbaghdas
 * 
 */

@Deprecated
@ContextConfiguration(classes = { InMemoryJpaConfig.class, I18NTestConfiguration.class })
public abstract class FullyConfiguredUnitTest extends BaseConfiguredTransactionalUnitTest {

	@Override
	@BeforeMethod
	public void initTests() throws IllegalAccessException, InvocationTargetException {
		super.initTests();
		SQLStatementCountValidator.reset();
	}

}
