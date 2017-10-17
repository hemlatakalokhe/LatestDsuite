/**
 * 
 */
package de.bonprix.model.enums;

public enum Mode {
    ADD("ADD"),
    REGISTER("REGISTER"),
    DELETE("DELETE"),
    EDIT("EDIT");

    private final String i18nkey;

    private Mode(final String i18nkey) {
        this.i18nkey = i18nkey;
    }

    public String getI18nkey() {
        return this.i18nkey;
    }
}
