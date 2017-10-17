package de.bonprix.vaadin.shortcut;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;

public class KeyCodeHelper {
	private static final Map<Integer, String> keyCodeMap = new HashMap<>();
	static {
		KeyCodeHelper.keyCodeMap.put(KeyCode.ENTER, "ENTER");
		KeyCodeHelper.keyCodeMap.put(KeyCode.ESCAPE, "ESCAPE");
		KeyCodeHelper.keyCodeMap.put(KeyCode.PAGE_UP, "PAGE_UP");
		KeyCodeHelper.keyCodeMap.put(KeyCode.PAGE_DOWN, "PAGE_DOWN");
		KeyCodeHelper.keyCodeMap.put(KeyCode.TAB, "TAB");
		KeyCodeHelper.keyCodeMap.put(KeyCode.ARROW_LEFT, "ARROW_LEFT");
		KeyCodeHelper.keyCodeMap.put(KeyCode.ARROW_UP, "ARROW_UP");
		KeyCodeHelper.keyCodeMap.put(KeyCode.ARROW_RIGHT, "ARROW_RIGHT");
		KeyCodeHelper.keyCodeMap.put(KeyCode.ARROW_DOWN, "ARROW_DOWN");
		KeyCodeHelper.keyCodeMap.put(KeyCode.BACKSPACE, "BACKSPACE");
		KeyCodeHelper.keyCodeMap.put(KeyCode.DELETE, "DELETE");
		KeyCodeHelper.keyCodeMap.put(KeyCode.INSERT, "INSERT");
		KeyCodeHelper.keyCodeMap.put(KeyCode.END, "END");
		KeyCodeHelper.keyCodeMap.put(KeyCode.HOME, "HOME");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F1, "F1");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F2, "F2");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F3, "F3");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F4, "F4");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F5, "F5");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F6, "F6");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F7, "F7");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F8, "F8");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F9, "F9");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F10, "F10");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F11, "F11");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F12, "F12");
		KeyCodeHelper.keyCodeMap.put(KeyCode.A, "A");
		KeyCodeHelper.keyCodeMap.put(KeyCode.B, "B");
		KeyCodeHelper.keyCodeMap.put(KeyCode.C, "C");
		KeyCodeHelper.keyCodeMap.put(KeyCode.D, "D");
		KeyCodeHelper.keyCodeMap.put(KeyCode.E, "E");
		KeyCodeHelper.keyCodeMap.put(KeyCode.F, "F");
		KeyCodeHelper.keyCodeMap.put(KeyCode.G, "G");
		KeyCodeHelper.keyCodeMap.put(KeyCode.H, "H");
		KeyCodeHelper.keyCodeMap.put(KeyCode.I, "I");
		KeyCodeHelper.keyCodeMap.put(KeyCode.J, "J");
		KeyCodeHelper.keyCodeMap.put(KeyCode.K, "K");
		KeyCodeHelper.keyCodeMap.put(KeyCode.L, "L");
		KeyCodeHelper.keyCodeMap.put(KeyCode.M, "M");
		KeyCodeHelper.keyCodeMap.put(KeyCode.N, "N");
		KeyCodeHelper.keyCodeMap.put(KeyCode.O, "O");
		KeyCodeHelper.keyCodeMap.put(KeyCode.P, "P");
		KeyCodeHelper.keyCodeMap.put(KeyCode.Q, "Q");
		KeyCodeHelper.keyCodeMap.put(KeyCode.R, "R");
		KeyCodeHelper.keyCodeMap.put(KeyCode.S, "S");
		KeyCodeHelper.keyCodeMap.put(KeyCode.T, "T");
		KeyCodeHelper.keyCodeMap.put(KeyCode.U, "U");
		KeyCodeHelper.keyCodeMap.put(KeyCode.V, "V");
		KeyCodeHelper.keyCodeMap.put(KeyCode.W, "W");
		KeyCodeHelper.keyCodeMap.put(KeyCode.X, "X");
		KeyCodeHelper.keyCodeMap.put(KeyCode.Y, "Y");
		KeyCodeHelper.keyCodeMap.put(KeyCode.Z, "Z");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM0, "NUM0");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM1, "NUM1");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM2, "NUM2");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM3, "NUM2");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM4, "NUM4");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM5, "NUM5");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM6, "NUM6");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM7, "NUM7");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM8, "NUM8");
		KeyCodeHelper.keyCodeMap.put(KeyCode.NUM9, "NUM9");
		KeyCodeHelper.keyCodeMap.put(KeyCode.SPACEBAR, "SPACEBAR");
	}
	private static final Map<Integer, String> modifierMap = new HashMap<>();

	static {
		KeyCodeHelper.modifierMap.put(ModifierKey.SHIFT, "SHIFT");
		KeyCodeHelper.modifierMap.put(ModifierKey.CTRL, "CTRL");
		KeyCodeHelper.modifierMap.put(ModifierKey.ALT, "ALT");
		KeyCodeHelper.modifierMap.put(ModifierKey.META, "META");
	}

	public static String getKeyCodeKey(Integer keyCode) {
		return KeyCodeHelper.keyCodeMap.get(keyCode);
	}

	public static String getModifier(Integer modifierKey) {
		return KeyCodeHelper.modifierMap.get(modifierKey);
	}

}
