package de.bonprix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.IdsFilter;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.jpa.BasicRepositoryCustom;

/**
 * An abstract basic crud service with a filter for an entity and its
 * corresponding dto who has a _L (translation table). This service is using the
 * repository you provide and does the call of the repository and the converting
 * between dto and entity. (see {@link de.bonprix.service.AbstractBasicService
 * AbstractBasicService})
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
 * @param <REPOSITORY>
 */
public abstract class AbstractBasicLanguageContainerService<CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement, FILTER extends IdsFilter, REPOSITORY extends JpaRepository<CONTAINERENTITY, Long> & BasicRepositoryCustom<CONTAINERENTITY, FILTER>>
		extends AbstractBasicService<CONTAINERENTITY, CONTAINERDTO, FILTER, REPOSITORY> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasicLanguageContainerService.class);

	private final Class<ELEMENTENTITY> elementEntityClazz;
	private final Class<ELEMENTDTO> elementDtoClazz;

	public AbstractBasicLanguageContainerService(Class<CONTAINERENTITY> containerEntityClazz,
			Class<ELEMENTENTITY> elementEntityClazz, Class<CONTAINERDTO> containerDtoClazz,
			Class<ELEMENTDTO> elementDtoClazz, Class<FILTER> filterClazz) {
		super(containerEntityClazz, containerDtoClazz, filterClazz);
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
	protected CONTAINERDTO convertToDTO(CONTAINERENTITY containerEntity) {
		return DtoEntityConverterUtils.convertToLanguageContainerDTO(	containerEntity, getDtoClazz(),
																		this.elementDtoClazz);
	}
}
