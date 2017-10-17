package de.bonprix.languagegrid;

import de.bonprix.dto.AbstractI18NLanguageElement;

public class LanguageElement extends AbstractI18NLanguageElement{
    /**
     * 
     */
    private static final long serialVersionUID = 5628332077562589287L;
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
