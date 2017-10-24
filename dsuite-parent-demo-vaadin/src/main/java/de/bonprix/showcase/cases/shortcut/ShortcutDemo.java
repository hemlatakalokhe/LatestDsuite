package de.bonprix.showcase.cases.shortcut;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import de.bonprix.I18N;
import de.bonprix.showcase.ShowCaseViewImpl;
import de.bonprix.showcase.ShowcaseWrapper;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.shortcut.ShortcutHandler;

@SuppressWarnings("serial")
@SpringViewComponent
public class ShortcutDemo extends ShowcaseWrapper {

    @Autowired
    private ShortcutHandler shortcutHandler;

    private ShortcutListener action;

    private ShortcutListener action2;

    public ShortcutDemo() {
        super("INTERACTION", "SHORTCUTDEMO");
    }

    @PostConstruct
    public void init() {
        this.shortcutHandler.createShortcut(this.action, ShowCaseViewImpl.VIEW_NAME, I18N.get("SHORTCUT_DESCRIPTION_FIRST"));

        this.shortcutHandler.createShortcut(this.action2, ShowCaseViewImpl.VIEW_NAME, I18N.get("SHORTCUT_DESCRIPTION_FIRST"));
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected Component createLayout() {
        TextField textField;

        TextField textField2;
        textField = new TextField("Focus by Alt+j");
        textField2 = new TextField("Focus by Alt+k");

        this.action = new AbstractField.FocusShortcut(textField, KeyCode.J, ModifierKey.ALT);
        this.action2 = new AbstractField.FocusShortcut(textField2, KeyCode.K, ModifierKey.ALT);

        textField.addShortcutListener(this.action);
        textField2.addShortcutListener(this.action2);

        return FluentUI.horizontal()
            .margin()
            .spacing()
            .add(textField)
            .add(textField2)
            .get();
    }

}
