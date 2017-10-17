package de.bonprix.vaadin.bean.field.util;

import com.vaadin.event.FieldEvents.BlurNotifier;
import com.vaadin.event.FieldEvents.FocusNotifier;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Field;

/**
 * Default {@link ShortcutListener} for using Enter to switch between {@link Field}s
 */
public abstract class OnEnterKeyListener {

    final ShortcutListener enterShortCut = new ShortcutListener("EnterOnTextAreaShortcut", ShortcutAction.KeyCode.ENTER, null) {
		private static final long serialVersionUID = 1L;

		@Override
        public void handleAction(final Object sender, final Object target) {
            onEnterKeyPressed();
        }
    };

    public <T extends AbstractComponent & FocusNotifier & BlurNotifier> T installOn(final T component) {
        component.addFocusListener(event -> component.addShortcutListener(OnEnterKeyListener.this.enterShortCut));
        component.addBlurListener(event -> component.removeShortcutListener(OnEnterKeyListener.this.enterShortCut));

        return component;
    }

    public abstract void onEnterKeyPressed();
}
