package de.bonprix.vaadin.shortcut;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;

public class Shortcut {

	private String viewName;
	private ShortcutAction action;
	private String shortcut;
	private String description;

	@Autowired
	ShortcutHandler shortcutHandler;

	public Shortcut(ShortcutAction action, String viewName, String description) {
		this.setAction(action);
		this.setViewName(viewName);
		this.setDescription(description);

	}

	public String getViewName() {
		return this.viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public ShortcutListener getAction() {
		return (ShortcutListener) this.action;
	}

	public void setAction(ShortcutAction action) {
		this.action = action;
		createShortcut(action);
	}

	private void createShortcut(ShortcutAction action) {
		StringBuilder builder = new StringBuilder();
		for (int modifier : action.getModifiers()) {
			builder.append(KeyCodeHelper.getModifier(modifier));
			builder.append(" + ");
		}
		this.setShortcut(builder.append(KeyCodeHelper.getKeyCodeKey(this.action.getKeyCode()))
			.toString());
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
