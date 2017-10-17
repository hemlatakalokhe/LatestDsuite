package de.bonprix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import de.bonprix.dto.FetchAllOptions;
import de.bonprix.dto.HasId;
import de.bonprix.dto.IdsFilter;
import de.bonprix.jpa.FetchRepositoryCustom;

/**
 * An abstract fetch crud service with a filter and a fetchoptions for an entity
 * and its corresponding dto. This service is using the repository you provide
 * and does the call of the repository and the converting between dto and
 * entity. (
 * {@link de.bonprix.service.AbstractFetchService#findAll(IdsFilter, FetchAllOptions)
 * findAll}, {@link de.bonprix.service.AbstractFetchService#findById(Long)
 * findById}, {@link de.bonprix.service.AbstractFetchService#create(HasId)
 * create}, {@link de.bonprix.service.AbstractFetchService#update(Long, HasId)},
 * {@link de.bonprix.service.AbstractFetchService#count(IdsFilter) count})
 * 
 * @author thacht
 *
 * @param <ENTITY>
 * @param <DTO>
 * @param <FILTER>
 * @param <FETCHOPTIONS>
 * @param <REPOSITORY>
 */
public abstract class AbstractFetchService<ENTITY extends FetchTableDtoConverter<DTO, FETCHOPTIONS>, DTO extends HasId, FILTER extends IdsFilter, FETCHOPTIONS extends FetchAllOptions, REPOSITORY extends JpaRepository<ENTITY, Long> & FetchRepositoryCustom<ENTITY, FILTER, FETCHOPTIONS>>
		extends AbstractBasicService<ENTITY, DTO, FILTER, REPOSITORY> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFetchService.class);

	private final Class<FETCHOPTIONS> fetchOptionsClazz;

	public AbstractFetchService(Class<ENTITY> entityClazz, Class<DTO> dtoClazz, Class<FILTER> filterClazz,
			Class<FETCHOPTIONS> fetchOptionsClazz) {
		super(entityClazz, dtoClazz, filterClazz);
		this.fetchOptionsClazz = fetchOptionsClazz;
	}

	public Class<FETCHOPTIONS> getFetchOptionsClazz() {
		return this.fetchOptionsClazz;
	}

	public List<DTO> findAllDefault(FILTER filter, FETCHOPTIONS fetchOptions) {
		final List<DTO> result = new ArrayList<>();

		for (final ENTITY entity : getRepository().findAll(filter, fetchOptions)) {
			result.add(convertToDTO(entity, fetchOptions));
		}
		return result;
	}

	public DTO findByIdDefault(Long id, FETCHOPTIONS fetchOptions) {
		ENTITY entity = getEntityById(id, fetchOptions);

		return convertToDTO(entity, fetchOptions);
	}

	@Override
	public void updateDefault(Long id, DTO dto) {
		dto.setId(id);
		if (dto.getId() == null) {
			throw new javax.ws.rs.BadRequestException("Can't update entity without id");
		}

		ENTITY entity = getEntityById(dto.getId(), getFetchOptionsWithFetchAll());

		getRepository().save(convertToEntity(entity, dto));
	}

	protected ENTITY getEntityById(Long id, FETCHOPTIONS fetchOptions) {
		FILTER filter = null;
		try {
			filter = getFilterClazz().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("couldn't create filter for id: " + id, e);
			throw new InternalServerErrorException("couldn't create filter for id: " + id);
		}
		filter.setIds(Arrays.asList(id));
		ENTITY containerEntity = getRepository().findOne(filter, fetchOptions);

		if (containerEntity == null) {
			throw new javax.ws.rs.NotFoundException();
		}

		return containerEntity;
	}

	protected FETCHOPTIONS getFetchOptionsWithFetchAll() {
		FETCHOPTIONS fetchOptions = null;
		try {
			fetchOptions = getFetchOptionsClazz().newInstance();
			fetchOptions.setFetchAll();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("couldn't create fetchtopions with fetch all", e);
			throw new InternalServerErrorException("couldn't create fetchtopions with fetch all");
		}
		return fetchOptions;
	}

	protected DTO convertToDTO(ENTITY entity, FETCHOPTIONS fetchOptions) {
		return DtoEntityConverterUtils.convertToDto(entity, fetchOptions, getDtoClazz());
	}

}
