package de.bonprix.model;

import de.bonprix.base.demo.dto.Style;
import de.bonprix.model.enums.Mode;

public class DialogEvent {
    private Mode mode;
    private final Style bean;

    public DialogEvent(final Mode mode, final Style bean) {
        super();
        this.mode = mode;
        this.bean = bean;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(final Mode mode) {
        this.mode = mode;
    }

    public Style getBean() {
        return this.bean;
    }
}
