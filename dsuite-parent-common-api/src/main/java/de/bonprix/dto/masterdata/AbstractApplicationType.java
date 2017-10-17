package de.bonprix.dto.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.AbstractI18NLanguageContainer;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractApplicationType<TYPELANGUAGE extends SimpleApplicationTypeLanguage>
		extends AbstractI18NLanguageContainer<TYPELANGUAGE> {

	private static final long serialVersionUID = 8134350011691395971L;

}
