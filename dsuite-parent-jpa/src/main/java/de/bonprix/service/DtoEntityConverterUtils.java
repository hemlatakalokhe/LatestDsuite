package de.bonprix.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.dto.I18NLanguageContainer;
import de.bonprix.dto.I18NLanguageElement;
import de.bonprix.exception.HasOptlockException;
import de.bonprix.dto.HasId;
import de.bonprix.dto.HasOptlock;

/**
 * Util class to convert between jpa entities and their dtos.
 * 
 * @author thacht
 *
 */
public final class DtoEntityConverterUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(DtoEntityConverterUtils.class);

	private DtoEntityConverterUtils() {

	}

	/**
	 * Helps to by the converting from dto to entity to convert the translation
	 * elements (*_L entities).
	 * 
	 * if dto translation elements are null, keep state from entity. otherwise
	 * add or remove translation elements from the entity corresponding to the
	 * dto provided.
	 * 
	 * @param containerEntity
	 * @param containerDto
	 * @param elementEntityClazz
	 */
	// updateLanguageElementEntities
	public static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> void updateLanguageElementEntities(
			CONTAINERENTITY containerEntity, CONTAINERDTO containerDto, Class<ELEMENTENTITY> elementEntityClazz) {
		if (containerDto.getLanguageElements() == null) {
			// if transmitted client country types where null,
			// keep state from database
			return;
		}

		if (containerEntity.getLanguageElementEntities() == null) {
			containerEntity.setLanguageElementEntities(new HashSet<>());
		}

		if (containerDto.getLanguageElements()
			.isEmpty()) {
			// clear the element entities, we dont have to do the expensive
			// stuff
			containerEntity.getLanguageElementEntities()
				.clear();
			return;
		}

		for (Iterator<ELEMENTENTITY> iterator = containerEntity.getLanguageElementEntities()
			.iterator(); iterator.hasNext();) {
			ELEMENTENTITY elementEntity = iterator.next();
			if (!DtoEntityConverterUtils.elementDtosContainElementEntity(	elementEntity,
																			containerDto.getLanguageElements())) {
				iterator.remove();
			}
		}

		// check for existing entities
		for (ELEMENTDTO elementDto : containerDto.getLanguageElements()) {
			boolean exists = false;
			for (ELEMENTENTITY elementEntity : containerEntity.getLanguageElementEntities()) {
				if (DtoEntityConverterUtils.compareElementEntityAndElementDto(elementEntity, elementDto)) {
					exists = true;
					// update if it exists
					elementEntity.updateEntity(elementDto);
					break;
				}
			}

			// create if doesn't exist
			if (!exists) {
				try {
					ELEMENTENTITY elementEntity = elementEntityClazz.newInstance();
					elementEntity.setId(elementDto.getId());
					elementEntity.setLanguageId(elementDto.getLanguageId());
					elementEntity.updateEntity(elementDto);
					elementEntity.setLanguageContainer(containerEntity);
					containerEntity.getLanguageElementEntities()
						.add(elementEntity);
				} catch (InstantiationException | IllegalAccessException e) {
					DtoEntityConverterUtils.LOGGER.error("couldn't create entity for dto id: " + elementDto.getId(), e);
				}

			}
		}
	}

	/**
	 * Compares if a translation dto and translation entity are the same.
	 * 
	 * @param elementEntity
	 * @param elementDto
	 * @return
	 */
	// compareElementEntityAndElementDto
	private static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> boolean compareElementEntityAndElementDto(
			ELEMENTENTITY elementEntity, ELEMENTDTO elementDto) {
		return elementEntity.getId() != null && elementEntity.getId()
			.equals(elementDto.getId())
				|| elementEntity.getLanguageId()
					.equals(elementDto.getLanguageId());
	}

	/**
	 * if the entity contains the translation dto
	 * 
	 * @param elementEntity
	 * @param elementDtos
	 * @return
	 */
	// elementDtosContainElementEntity
	private static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> boolean elementDtosContainElementEntity(
			ELEMENTENTITY elementEntity, List<ELEMENTDTO> elementDtos) {
		for (ELEMENTDTO elementDto : elementDtos) {
			if (DtoEntityConverterUtils.compareElementEntityAndElementDto(elementEntity, elementDto)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Converts entity translations to dto translations
	 * 
	 * @param containerEntity
	 * @param containerDto
	 * @param elementDtoClazz
	 */
	// convertToLanguageElementDTOs
	public static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> void convertToLanguageElementDTOs(
			CONTAINERENTITY containerEntity, CONTAINERDTO containerDto, Class<ELEMENTDTO> elementDtoClazz) {
		List<ELEMENTDTO> elementDtos = new ArrayList<>();

		if (containerEntity.getLanguageElementEntities() != null) {
			for (ELEMENTENTITY elementEntity : containerEntity.getLanguageElementEntities()) {
				try {
					ELEMENTDTO elementDto = elementDtoClazz.newInstance();
					elementDto.setId(elementEntity.getId());
					elementDto.setLanguageId(elementEntity.getLanguageId());
					elementEntity.updateDto(elementDto);
					elementDtos.add(elementDto);
				} catch (InstantiationException | IllegalAccessException e) {
					DtoEntityConverterUtils.LOGGER.error(	"couldn't create dto for entity id: " + elementEntity.getId(),
															e);
				}
			}
		}

		containerDto.setLanguageElements(elementDtos);
	}

	/**
	 * creates the instance of the container dto and sets id, optlock and the
	 * translation dtos
	 * 
	 * @param containerEntity
	 * @param containerDtoClazz
	 * @param elementDtoClazz
	 * @return
	 */
	// convertToLanguageContainerDTODefaults
	private static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> CONTAINERDTO convertToLanguageContainerDTODefaults(
			final CONTAINERENTITY containerEntity, Class<CONTAINERDTO> containerDtoClazz,
			Class<ELEMENTDTO> elementDtoClazz) {
		CONTAINERDTO containerDto = DtoEntityConverterUtils.convertToDtoDefaults(containerEntity, containerDtoClazz);
		if (containerDto != null) {
			DtoEntityConverterUtils.convertToLanguageElementDTOs(containerEntity, containerDto, elementDtoClazz);
		}
		return containerDto;
	}

	/**
	 * Converts entity (who contains translation entities) to dto with
	 * translation dtos
	 * 
	 * @param containerEntity
	 * @param containerDtoClazz
	 * @param elementDtoClazz
	 * @return
	 */
	// convertToLanguageContainerDTO
	public static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> CONTAINERDTO convertToLanguageContainerDTO(
			final CONTAINERENTITY containerEntity, Class<CONTAINERDTO> containerDtoClazz,
			Class<ELEMENTDTO> elementDtoClazz) {
		CONTAINERDTO containerDto = DtoEntityConverterUtils
			.convertToLanguageContainerDTODefaults(containerEntity, containerDtoClazz, elementDtoClazz);
		if (containerDto != null) {
			containerEntity.updateDto(containerDto);
		}
		return containerDto;
	}

	/**
	 * Converts entity (who contains translation entities) to dto with
	 * translation dtos
	 * 
	 * @param containerEntity
	 * @param fetchOptions
	 * @param containerDtoClazz
	 * @param elementDtoClazz
	 * @return
	 */
	// convertToLanguageContainerDTO
	public static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & FetchTableDtoConverter<CONTAINERDTO, FETCHOPTIONS>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement, FETCHOPTIONS> CONTAINERDTO convertToLanguageContainerDTO(
			final CONTAINERENTITY containerEntity, FETCHOPTIONS fetchOptions, Class<CONTAINERDTO> containerDtoClazz,
			Class<ELEMENTDTO> elementDtoClazz) {
		CONTAINERDTO containerDto = DtoEntityConverterUtils
			.convertToLanguageContainerDTODefaults(containerEntity, containerDtoClazz, elementDtoClazz);
		if (containerDto != null) {
			containerEntity.updateDto(containerDto, fetchOptions);
		}
		return containerDto;
	}

	/**
	 * Converts dto (who contains translation dtos) to entity with translation
	 * entities
	 * 
	 * @param containerEntity
	 * @param containerDto
	 * @param elementEntityClazz
	 * @return
	 */
	// convertToLanguageContainerEntity
	public static <CONTAINERENTITY extends I18NLanguageContainerEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO> & BasicTableDtoConverter<CONTAINERDTO>, ELEMENTENTITY extends I18NLanguageElementEntity<CONTAINERENTITY, ELEMENTENTITY, CONTAINERDTO, ELEMENTDTO>, CONTAINERDTO extends I18NLanguageContainer<ELEMENTDTO>, ELEMENTDTO extends I18NLanguageElement> CONTAINERENTITY convertToLanguageContainerEntity(
			CONTAINERENTITY containerEntity, CONTAINERDTO containerDto, Class<ELEMENTENTITY> elementEntityClazz) {
		CONTAINERENTITY convertedContainerEntity = DtoEntityConverterUtils.convertToEntity(	containerEntity,
																							containerDto);
		DtoEntityConverterUtils.updateLanguageElementEntities(	convertedContainerEntity, containerDto,
																elementEntityClazz);
		return convertedContainerEntity;
	}

	/**
	 * creates the entity of the dto and sets id, optlock
	 * 
	 * @param entity
	 * @param dtoClazz
	 * @return
	 */
	private static <ENTITY extends BasicTableDtoConverter<DTO>, DTO extends HasId> DTO convertToDtoDefaults(
			ENTITY entity, Class<DTO> dtoClazz) {
		if (entity == null) {
			return null;
		}

		try {
			DTO dto = dtoClazz.newInstance();
			dto.setId(entity.getId());
			if ((dto instanceof HasOptlock && !(entity instanceof HasOptlock))
					|| (!(dto instanceof HasOptlock) && entity instanceof HasOptlock)) {
				throw new HasOptlockException(
						"dto and entity do not both implement HasOptlock interface, please fix either way");
			}
			if (dto instanceof HasOptlock && entity instanceof HasOptlock) {
				((HasOptlock) dto).setOptlock(((HasOptlock) entity).getOptlock());
			}
			return dto;
		} catch (InstantiationException | IllegalAccessException e) {
			DtoEntityConverterUtils.LOGGER.error("couldn't create dto for entity id: " + entity.getId(), e);
		} catch (HasOptlockException e) {
			DtoEntityConverterUtils.LOGGER.error(e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * converts an entity to a dto
	 * 
	 * @param entity
	 * @param dtoClazz
	 * @return
	 */
	public static <ENTITY extends BasicTableDtoConverter<DTO>, DTO extends HasId> DTO convertToDto(ENTITY entity,
			Class<DTO> dtoClazz) {
		DTO dto = DtoEntityConverterUtils.convertToDtoDefaults(entity, dtoClazz);
		if (dto != null) {
			entity.updateDto(dto);
		}
		return dto;
	}

	/**
	 * Converts entity to dto
	 * 
	 * @param entity
	 * @param fetchOptions
	 * @param dtoClazz
	 * @return
	 */
	public static <ENTITY extends FetchTableDtoConverter<DTO, FETCHOPTIONS>, DTO extends HasId, FETCHOPTIONS> DTO convertToDto(
			ENTITY entity, FETCHOPTIONS fetchOptions, Class<DTO> dtoClazz) {
		DTO dto = DtoEntityConverterUtils.convertToDtoDefaults(entity, dtoClazz);
		if (dto != null) {
			entity.updateDto(dto, fetchOptions);
		}
		return dto;
	}

	/**
	 * Converts dto to entity
	 * 
	 * @param dto
	 * @param entityClazz
	 * @return
	 */
	public static <ENTITY extends BasicTableDtoConverter<DTO>, DTO extends HasId> ENTITY convertToEntity(DTO dto,
			Class<ENTITY> entityClazz) {
		try {
			return DtoEntityConverterUtils.convertToEntity(entityClazz.newInstance(), dto);
		} catch (InstantiationException | IllegalAccessException e) {
			DtoEntityConverterUtils.LOGGER.error("couldn't create entity for dto id: " + dto.getId(), e);
		}
		return null;
	}

	/**
	 * converts entity to dto.
	 * 
	 * @param containerEntity
	 * @param containerDto
	 * @return
	 */
	public static <ENTITY extends BasicTableDtoConverter<DTO>, DTO extends HasId> ENTITY convertToEntity(
			ENTITY containerEntity, DTO containerDto) {
		ENTITY convertedContainerEntity = DtoEntityConverterUtils.convertToEntityDefaults(	containerEntity,
																							containerDto);
		containerEntity.updateEntity(containerDto);
		return convertedContainerEntity;
	}

	/**
	 * converts entity to dto with setting id and optlock;
	 * 
	 * @param containerEntity
	 * @param containerDto
	 * @return
	 */
	private static <ENTITY extends BasicTableDtoConverter<DTO>, DTO extends HasId> ENTITY convertToEntityDefaults(
			ENTITY containerEntity, DTO containerDto) {
		containerEntity.setId(containerDto.getId());
		if ((containerDto instanceof HasOptlock && !(containerEntity instanceof HasOptlock))
				|| (!(containerDto instanceof HasOptlock) && containerEntity instanceof HasOptlock)) {
			try {
				throw new HasOptlockException(
						"dto and entity do not both implement HasOptlock interface, please fix either way");
			} catch (HasOptlockException e) {
				DtoEntityConverterUtils.LOGGER.error(e.getLocalizedMessage(), e);
			}
		}
		if (containerDto instanceof HasOptlock && containerEntity instanceof HasOptlock) {
			((HasOptlock) containerEntity).setOptlock(((HasOptlock) containerDto).getOptlock());
		}
		return containerEntity;
	}

}
