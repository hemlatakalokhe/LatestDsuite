/**
 *
 */
package de.bonprix.vaadin.provider;

/**
 * @author cthiel
 * @date 18.11.2016
 *
 */
public interface UiSessionProvider {

    /**
     * Performs a logout of the user and invalidates all known session objects (HttpSession, VaadinSession) and forwards the browser to the user-ws' logout page
     * to also delete all known authKeys.
     */
    void logout();

}
