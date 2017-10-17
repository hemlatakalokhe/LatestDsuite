package de.bonprix.service;

import de.bonprix.dto.HasId;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;

/**
 * Interface for getting and setting the language id to use this in
 * {@link de.bonprix.service.DtoEntityConverterUtils DtoEntityConverterUtils}.
 * 
 * @author thacht
 *
 * @param <LANGUAGEELEMENTDTO>
 */
public interface I18NLanguageElementEntity<LANGUAGECONTAINERENTITY extends I18NLanguageContainerEntity<LANGUAGECONTAINERENTITY, LANGUAGEELEMENTENTITY, LANGUAGECONTAINERDTO, LANGUAGEELEMENTDTO> & BasicTableDtoConverter<LANGUAGECONTAINERDTO>, LANGUAGEELEMENTENTITY extends I18NLanguageElementEntity<LANGUAGECONTAINERENTITY, LANGUAGEELEMENTENTITY, LANGUAGECONTAINERDTO, LANGUAGEELEMENTDTO>, LANGUAGECONTAINERDTO extends I18NLanguageContainer<LANGUAGEELEMENTDTO>, LANGUAGEELEMENTDTO extends I18NLanguageElement>
		extends BasicTableDtoConverter<LANGUAGEELEMENTDTO>, HasId {

	Long getLanguageId();

	void setLanguageId(Long languageId);

	void setLanguageContainer(LANGUAGECONTAINERENTITY languageContainer);

}
