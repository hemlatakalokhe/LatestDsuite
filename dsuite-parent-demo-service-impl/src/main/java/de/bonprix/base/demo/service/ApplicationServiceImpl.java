package de.bonprix.base.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import de.bonprix.annotation.RestService;
import de.bonprix.base.demo.dto.Application;
import de.bonprix.base.demo.dto.builder.ApplicationBuilder;
import de.bonprix.base.demo.jparepository.ApplicationFilterRepository;
import de.bonprix.base.demo.jparepository.ApplicationRepository;
import de.bonprix.base.demo.service.ApplicationService;
import de.bonprix.model.ComplexFilter;
import de.bonprix.model.Paged;

@RestService
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

	@Resource
	private ApplicationRepository applicationRepository;

	@Resource
	private ApplicationFilterRepository applicationFilterRepository;

	@Override
	@Transactional
	public List<Application> findAll(final Paged pageable) {
		final List<Application> result = new ArrayList<>();

		for (final de.bonprix.base.demo.model.Application c : this.applicationRepository
			.findAll(new PageRequest(pageable.getPage(), pageable.getPageSize()))) {
			result.add(convertToDTO(c));
		}
		return result;
	}

	@Override
	@Transactional
	public List<Application> findByFilter(final ComplexFilter complexFilter) {

		final List<Application> result = new ArrayList<>();

		for (final de.bonprix.base.demo.model.Application c : this.applicationFilterRepository
			.findByFilter(complexFilter)) {
			result.add(convertToDTO(c));
		}

		return result;
	}

	@Override
	public Application findById(final Long id) {
		return convertToDTO(this.applicationRepository.findOne(id));
	}

	private Application convertToDTO(final de.bonprix.base.demo.model.Application modelApplication) {

		return new ApplicationBuilder().withId(modelApplication.getId())
			.withName(modelApplication.getName())
			.build();
	}

	private de.bonprix.base.demo.model.Application convertToModel(final Application dtoApplication) {
		return new de.bonprix.base.demo.model.builder.ApplicationBuilder().withId(dtoApplication.getId())
			.withName(dtoApplication.getName())
			.build();
	}

	@Override
	public void deleteById(final Long id) {
		this.applicationRepository.delete(id);
	}

	@Override
	public Application saveApplication(final Application dtoApplication) {
		return convertToDTO(this.applicationRepository.saveAndFlush(convertToModel(dtoApplication)));
	}

}
