package de.bonprix.model;

import de.bonprix.model.enums.Mode;

public class DialogModeEvent {

    private final Mode mode;

    public DialogModeEvent(final Mode mode) {
        this.mode=mode;
    }

    public Mode getMode() {
        return this.mode;
    }

}
