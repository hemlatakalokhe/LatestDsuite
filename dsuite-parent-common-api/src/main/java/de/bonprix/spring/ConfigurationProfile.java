package de.bonprix.spring;

/**
 * This interface contains constants which controls which configurations are
 * valid in which contexts. Use one of the constants to assign one or more
 * profiles to a spring configuration class.
 */
public interface ConfigurationProfile {
	public static final String UNITTEST = "UNITTEST";
	public static final String INTEGRATIONTEST = "INTEGRATIONTEST";
	public static final String UI_TEST_TESTBENCH = "UI_TEST_TESTBENCH";
	public static final String PRODUCTION = "PRODUCTION";

}
