package de.bonprix.service;

import java.util.Set;

import de.bonprix.dto.HasId;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;

/**
 * Interface for getting and setting the language elements to use this in
 * {@link de.bonprix.service.DtoEntityConverterUtils DtoEntityConverterUtils}.
 * 
 * @author thacht
 *
 * @param <ELEMENTENTITY>
 * @param <ELEMENTDTO>
 */
public interface I18NLanguageContainerEntity<CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement>
		extends HasId {

	Set<ELEMENTENTITY> getLanguageElementEntities();

	void setLanguageElementEntities(Set<ELEMENTENTITY> languageElementEntities);

}
