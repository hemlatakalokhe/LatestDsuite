package de.bonprix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import de.bonprix.dto.FetchAllOptions;
import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.IdsFilter;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.jpa.FetchRepositoryCustom;

/**
 * An abstract fetch crud service with a filter and fetchoptions for an entity
 * and its corresponding dto who has a _L (translation table). This service is
 * using the repository you provide and does the call of the repository and the
 * converting between dto and entity. (see
 * {@link de.bonprix.service.AbstractFetchService AbstractFetchService})
 * 
 * @author thacht
 *
 * @param <CONTAINERENTITY>
 *            jpa entity (f.e. entity for table MD_APPLICATIONTYPE)
 * @param <ELEMENTENTITY>
 *            jpa translation entity (f.e. entity for table
 *            MD_APPLICATIONTYPE_L)
 * @param <CONTAINERDTO>
 *            dto (f.e. dto for table MD_APPLICATIONTYPE)
 * @param <ELEMENTDTO>
 *            dto (f.e. dto for table MD_APPLICATIONTYPE_L)
 * @param <FILTER>
 * @param <FETCHOPTIONS>
 * @param <REPOSITORY>
 */
public abstract class AbstractFetchLanguageContainerService<CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & FetchTableDtoConverter<CONTAINERDTO, FETCHOPTIONS>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement, FILTER extends IdsFilter, FETCHOPTIONS extends FetchAllOptions, REPOSITORY extends JpaRepository<CONTAINERENTITY, Long> & FetchRepositoryCustom<CONTAINERENTITY, FILTER, FETCHOPTIONS>>
		extends AbstractFetchService<CONTAINERENTITY, CONTAINERDTO, FILTER, FETCHOPTIONS, REPOSITORY> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFetchLanguageContainerService.class);

	private final Class<ELEMENTENTITY> elementEntityClazz;
	private final Class<ELEMENTDTO> elementDtoClazz;

	public AbstractFetchLanguageContainerService(Class<CONTAINERENTITY> containerEntityClazz,
			Class<ELEMENTENTITY> elementEntityClazz, Class<CONTAINERDTO> containerDtoClazz,
			Class<ELEMENTDTO> elementDtoClazz, Class<FILTER> filterClazz, Class<FETCHOPTIONS> fetchOptionsClazz) {
		super(containerEntityClazz, containerDtoClazz, filterClazz, fetchOptionsClazz);
		this.elementEntityClazz = elementEntityClazz;
		this.elementDtoClazz = elementDtoClazz;
	}

	@Override
	protected CONTAINERENTITY convertToEntity(CONTAINERDTO containerDto) {
		try {
			return convertToEntity(getEntityClazz().newInstance(), containerDto);
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("couldn't create entity for container dto id: " + containerDto.getId(), e);
		}
		return null;
	}

	@Override
	protected CONTAINERENTITY convertToEntity(CONTAINERENTITY containerEntity, CONTAINERDTO containerDto) {
		return DtoEntityConverterUtils.convertToLanguageContainerEntity(containerEntity, containerDto,
																		this.elementEntityClazz);
	}

	@Override
	protected CONTAINERDTO convertToDTO(CONTAINERENTITY containerEntity, FETCHOPTIONS fetchOptions) {
		return DtoEntityConverterUtils.convertToLanguageContainerDTO(	containerEntity, fetchOptions, getDtoClazz(),
																		this.elementDtoClazz);
	}
}
