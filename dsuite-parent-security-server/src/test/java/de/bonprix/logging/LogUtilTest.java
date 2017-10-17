package de.bonprix.logging;

import org.apache.logging.log4j.ThreadContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogUtilTest {

	@Test
	public void testSetLoggingProperties() {
		LogUtil.setLoggingProperties(	"testLoggingConfigurationApp", "testLoggingConfigurationEnvironment",
										"testApplicationContextPath");

		Assert.assertEquals("testApplicationContextPath", ThreadContext.get("testLoggingConfigurationApp"));
		Assert.assertEquals("DEV", ThreadContext.get("testLoggingConfigurationEnvironment"));
	}

	@Test
	public void testUnsetLoggingProperties() {
		// set first logging properties
		LogUtil.setLoggingProperties(	"testLoggingConfigurationApp", "testLoggingConfigurationEnvironment",
										"testApplicationContextPath");

		// unset and check
		LogUtil.unsetLoggingProperties("testLoggingConfigurationApp", "testLoggingConfigurationEnvironment");

		Assert.assertFalse(ThreadContext.containsKey("testLoggingConfigurationApp"));
		Assert.assertFalse(ThreadContext.containsKey("testLoggingConfigurationEnvironment"));
	}
}
