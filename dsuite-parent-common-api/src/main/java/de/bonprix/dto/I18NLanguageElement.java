package de.bonprix.dto;

/**
 * interface for getting a languageId from a dto
 * 
 * @author thacht
 *
 */
public interface I18NLanguageElement extends HasId {

	/**
	 * returns the languageId of the dto
	 * 
	 * @return languageId
	 */
	Long getLanguageId();

	/**
	 * sets the languageId of the dto
	 * 
	 * @param languageId
	 */
	void setLanguageId(Long languageId);

	/**
	 * returns the name of the element
	 * 
	 * @return name
	 */
	String getName();
}
