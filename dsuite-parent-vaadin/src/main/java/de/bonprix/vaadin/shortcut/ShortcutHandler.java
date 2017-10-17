package de.bonprix.vaadin.shortcut;

import java.util.Set;

import com.vaadin.event.ShortcutAction;

public interface ShortcutHandler {

	/**
	 * Adds a Shortcut to the handler
	 * 
	 * @param shortcut
	 */
	void addShortcut(Shortcut shortcut);

	Set<Shortcut> getShortcuts();

	void createShortcut(ShortcutAction action, String viewName, String description);
}
