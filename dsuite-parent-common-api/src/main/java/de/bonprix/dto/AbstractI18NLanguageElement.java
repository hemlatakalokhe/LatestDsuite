package de.bonprix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.bonprix.dto.Entity;
import de.bonprix.dto.I18NLanguageElement;

/**
 * Language translation element for specific translations
 * 
 * @author thacht
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractI18NLanguageElement extends Entity implements I18NLanguageElement {
	private static final long serialVersionUID = 1L;

	private Long languageId;

	private String name;

	@Override
	public Long getLanguageId() {
		return this.languageId;
	}

	@Override
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
