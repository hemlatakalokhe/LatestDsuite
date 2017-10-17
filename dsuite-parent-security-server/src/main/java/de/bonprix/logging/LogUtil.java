package de.bonprix.logging;

import org.apache.logging.log4j.ThreadContext;

/**
 * Utility class for setting / unsetting logging properties in async contexts
 *
 * @author vbaghdas
 *
 */
public final class LogUtil {

	/**
	 * Sets some logging properties to {@link ThreadContext}
	 */
	public static void setLoggingProperties(String loggingConfigurationApp, String loggingConfigurationEnvironment,
			String applicationContextPath) {
		ThreadContext.put(loggingConfigurationApp, applicationContextPath);
		ThreadContext.put(loggingConfigurationEnvironment, "DEV");
	}

	/**
	 * Unset the previously set logging properties.
	 */
	public static void unsetLoggingProperties(String loggingConfigurationApp, String loggingConfigurationEnvironment) {
		ThreadContext.remove(loggingConfigurationApp);
		ThreadContext.remove(loggingConfigurationEnvironment);
	}

}
