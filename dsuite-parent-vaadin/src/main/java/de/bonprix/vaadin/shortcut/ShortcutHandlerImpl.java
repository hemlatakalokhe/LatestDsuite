package de.bonprix.vaadin.shortcut;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.vaadin.event.ShortcutAction;

@Component
public class ShortcutHandlerImpl implements ShortcutHandler {

	private Set<Shortcut> shortcuts = new HashSet<>();

	@Override
	public void addShortcut(Shortcut shortcut) {
		if (!this.allreadyListed(shortcut)) {
			this.shortcuts.add(shortcut);
		}
	}

	@Override
	public Set<Shortcut> getShortcuts() {
		return this.shortcuts;
	}

	private boolean allreadyListed(Shortcut shortcut2) {
		return this.shortcuts.stream()
			.anyMatch(shortcut -> shortcut.getShortcut()
				.equals(shortcut2.getShortcut())
					&& shortcut.getViewName()
						.equals(shortcut2.getViewName()));
	}

	@Override
	public void createShortcut(ShortcutAction action, String viewName, String description) {
		this.addShortcut(new Shortcut(action, viewName, description));
	}

}
