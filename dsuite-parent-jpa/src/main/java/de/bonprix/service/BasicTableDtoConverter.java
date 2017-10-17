package de.bonprix.service;

import de.bonprix.dto.HasId;

/**
 * Interface for a jpa entity to help with the converting between dto and jpa.
 * 
 * @author thacht
 *
 * @param <DTO>
 */
public interface BasicTableDtoConverter<DTO extends HasId> extends HasId {

	/**
	 * This method should update the dto (f.e. dto.setName(getName()))). id and
	 * optlock are updated automatically by using the correct interfaces (
	 * {@link de.bonprix.dto.HasId HasId}, {@link de.bonprix.dto.HasOptlock
	 * HasOptlock}).
	 * 
	 * @param dto
	 */
	void updateDto(DTO dto);

	/**
	 * This method should update the entity (f.e. setName(dto.getName())). id
	 * and optlock are updated automatically by using the correct interfaces (
	 * {@link de.bonprix.dto.HasId HasId}, {@link de.bonprix.dto.HasOptlock
	 * HasOptlock}). If you need to call other services/repositories in this
	 * method to check for the correct input, please override the super method
	 * in you abstract service and after the super method, do your own stuff on
	 * the entity.
	 * 
	 * @param dto
	 */
	void updateEntity(DTO dto);

}
