package de.bonprix;

import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.Test;

/**
 * @author vbaghdas
 */
public class StaticMethodAwareTestTest {
	@Test
	public void getObjectFactoryTest() {
		DummyStaticMethodAwareTest dummy = new DummyStaticMethodAwareTest();
		IObjectFactory actual = dummy.getObjectFactory();

		Assert.assertNotNull(actual);
		Assert.assertTrue(actual instanceof PowerMockObjectFactory);
	}
}
