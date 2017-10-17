package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageContainer;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractApplicationStatus<TYPELANGUAGE extends SimpleApplicationStatusLanguage>
		extends AbstractI18NLanguageContainer<TYPELANGUAGE> {

	private static final long serialVersionUID = 641040914541745025L;

}
