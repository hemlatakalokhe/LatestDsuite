/**
 *
 */
package de.bonprix.vaadin.provider;

import org.springframework.stereotype.Component;

import com.vaadin.ui.UI;

/**
 * @author cthiel
 * @date 18.01.2017
 *
 */
@Component
public class UiNavigationProviderImpl implements UiNavigationProvider {

    @Override
    public void navigateTo(final String screenName) {
        UI.getCurrent()
            .getNavigator()
            .navigateTo(screenName);
    }

    @Override
    public String getCurrentView() {
        return UI.getCurrent()
            .getNavigator()
            .getState();
    }

}
