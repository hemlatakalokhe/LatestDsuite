package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageContainer;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractApplicationGroup<GROUPLANGUAGE extends SimpleApplicationGroupLanguage>
		extends AbstractI18NLanguageContainer<GROUPLANGUAGE> {

	private static final long serialVersionUID = 4869333337433450523L;

}
