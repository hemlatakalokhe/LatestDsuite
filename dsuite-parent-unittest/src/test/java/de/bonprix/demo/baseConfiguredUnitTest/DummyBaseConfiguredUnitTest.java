package de.bonprix.demo.baseConfiguredUnitTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;

/**
 * @author vbaghdas
 */
public class DummyBaseConfiguredUnitTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private BeanIntoWhichInject beanIntoWhichInject;

	@Mock
	private BeanToInject beanToInject;

	public BeanIntoWhichInject getBeanIntoWhichInject() {
		return this.beanIntoWhichInject;
	}

	public BeanToInject getBeanToInject() {
		return this.beanToInject;
	}

	@Test
	public void someTest() {
	}

}
