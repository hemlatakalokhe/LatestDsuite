package de.bonprix.model;

import de.bonprix.dto.AbstractI18NLanguageElement;

/**
 * Language Grid showcase element
 * 
 * @author d.kolev
 *
 */
public class LanguageI18NElementWithDescription extends AbstractI18NLanguageElement {

    /**
     * 
     */
    private static final long serialVersionUID = -8282194132482256158L;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
