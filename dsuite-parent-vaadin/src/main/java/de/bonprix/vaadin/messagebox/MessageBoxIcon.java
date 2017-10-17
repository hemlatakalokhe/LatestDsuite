package de.bonprix.vaadin.messagebox;

import com.vaadin.server.Resource;

import de.bonprix.vaadin.FontBonprix;
import de.bonprix.vaadin.FontIconColor;

/**
 * @author k.suba
 *
 */
public enum MessageBoxIcon {

    QUESTION(FontBonprix.HELP_CIRCLE.getSvg(FontIconColor.LIGHTBLUE)),
    INFO(FontBonprix.INFO_CIRCLE.getSvg(FontIconColor.LIGHTBLUE)),
    WARNING(FontBonprix.ERROR.getSvg(FontIconColor.YELLOW)),
    ERROR(FontBonprix.ERROR.getSvg(FontIconColor.RED)),
    NONE(null);

    private final Resource icon;

    private MessageBoxIcon(final Resource icon) {
        this.icon = icon;
    }

    public Resource getIcon() {
        return this.icon;
    }

}