/**
 *
 */
package de.bonprix.vaadin.provider;

/**
 * @author cthiel
 * @date 18.01.2017
 *
 */
public interface UiNavigationProvider {

    /**
     * Navigates the UI to the view with the given screenName.
     *
     * @param viewName the view name to be navigated to
     * @throws IllegalArgumentException then the navigator does not know of any view with this name
     */
    void navigateTo(String viewName);

    /**
     * Returns the name of the current view of the navigator.
     *
     * @return the current view
     */
    String getCurrentView();

}
