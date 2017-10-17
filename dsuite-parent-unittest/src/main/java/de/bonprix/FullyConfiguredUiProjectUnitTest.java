package de.bonprix;

import org.springframework.test.context.ContextConfiguration;

import de.bonprix.configuration.I18NTestConfiguration;
import de.bonprix.configuration.JmsTestConfiguration;

/**
 * Full base setup for unit tests needed for UI maven projects; provides
 * additionally to base unit test setup in @BaseConfiguredUnitTest i18n support
 * implemented in @I18NTestConfiguration and JMS support implemented
 * in @JmsTestConfiguration
 * 
 * @author vbaghdas
 * 
 */

@ContextConfiguration(classes = { I18NTestConfiguration.class, JmsTestConfiguration.class })
public class FullyConfiguredUiProjectUnitTest extends BaseConfiguredUnitTest {

}
