package de.bonprix.vaadin.shortcut;

import java.util.Set;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;

import de.bonprix.BaseConfiguredUnitTest;

public class ShortcutHandlerImplTest extends BaseConfiguredUnitTest {

	private ShortcutHandlerImpl shortcutHandler;

	@Mock
	Set<Shortcut> shortcutsMock;

	@Mock
	private TextField textField;

	@Mock
	private TextField textField2;

	Shortcut shortcut;

	Shortcut shortcut1;

	@BeforeMethod
	public void init() {
		this.textField = Mockito.mock(TextField.class);
		this.textField2 = Mockito.mock(TextField.class);

		this.shortcutHandler = new ShortcutHandlerImpl();

		this.shortcut = new Shortcut(new AbstractField.FocusShortcut(this.textField, KeyCode.J, ModifierKey.ALT),
				"testView1", "SHORTCUT_DESCRIPTION_FIRST");

		this.shortcut1 = new Shortcut(new AbstractField.FocusShortcut(this.textField2, KeyCode.K, ModifierKey.ALT),
				"testView2", "SHORTCUT_DESCRIPTION_SECOND");

		this.shortcutHandler.addShortcut(this.shortcut);
		this.shortcutHandler.addShortcut(this.shortcut1);

	}

	@Test
	public void AddShortcutAllreadyListedFalseTest() {

		Shortcut newShortcut = new Shortcut(new AbstractField.FocusShortcut(this.textField, KeyCode.L, ModifierKey.ALT),
				"newTestView", "new");

		this.shortcutHandler.addShortcut(newShortcut);
		Assert.assertEquals(this.shortcutHandler.getShortcuts()
			.size(), 3);
	}

	@Test
	public void AddShortcutAllreadyListedTrueTest() {

		this.shortcutHandler.addShortcut(this.shortcut);
		Assert.assertEquals(this.shortcutHandler.getShortcuts()
			.size(), 2);
	}
}
