package de.bonprix;

import org.springframework.test.context.ContextConfiguration;

import de.bonprix.configuration.I18NTestConfiguration;

/**
 * Class providing additionally to base unit test setup
 * in @BaseConfiguredUnitTest i18n support implemented in @I18NTestConfiguration
 * 
 * @author vbaghdas
 * 
 */
@ContextConfiguration(classes = { I18NTestConfiguration.class })
public abstract class I18NAwareUnitTest extends BaseConfiguredUnitTest {

}
