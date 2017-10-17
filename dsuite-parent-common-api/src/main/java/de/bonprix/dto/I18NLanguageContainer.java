package de.bonprix.dto;

import java.util.List;

/**
 * interface for getting a languageId from a dto
 * 
 * @author thacht
 *
 */
public interface I18NLanguageContainer<ELEMENT extends I18NLanguageElement> extends HasId {

	/**
	 * returns found bean for languageId
	 * 
	 * @return found bean for languageId, null if nothing was found
	 */
	ELEMENT getLanguageElement(long languageId);

	/**
	 * returns all language elements
	 * 
	 * @return language elements
	 */
	List<ELEMENT> getLanguageElements();

	/**
	 * sets the language elements
	 * 
	 * @param languageElements
	 */
	void setLanguageElements(List<ELEMENT> languageElements);

	/**
	 * adds a bean to the list of languageElements
	 * 
	 * @param i18NLanguageElement
	 *            to be added
	 */
	void addI18NLanguageElement(ELEMENT bean);

}