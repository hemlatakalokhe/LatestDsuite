package de.bonprix;

import org.springframework.test.context.ContextConfiguration;

import de.bonprix.configuration.JmsTestConfiguration;

/**
 * Class providing additionally to base unit test setup
 * in @BaseConfiguredUnitTest jms support implemented in @JmsTestConfiguration
 * 
 * @author vbaghdas
 * 
 */
@ContextConfiguration(classes = { JmsTestConfiguration.class })
public abstract class JmsAwareUnitTest extends BaseConfiguredUnitTest {

}
