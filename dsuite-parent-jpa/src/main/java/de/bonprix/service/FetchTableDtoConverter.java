package de.bonprix.service;

import de.bonprix.dto.HasId;

/**
 * Interface for a jpa entity to help with the converting between dto and jpa
 * with a fetchOptions for converting to a dto.
 * 
 * @author thacht
 *
 * @param <DTO>
 * @param <FETCHOPTIONS>
 */
public interface FetchTableDtoConverter<DTO extends HasId, FETCHOPTIONS> extends BasicTableDtoConverter<DTO> {

	/**
	 * This method should update the dto (f.e. dto.setName(getName()))). id and
	 * optlock are updated automatically by using the correct interfaces (
	 * {@link de.bonprix.dto.HasId HasId}, {@link de.bonprix.dto.HasOptlock
	 * HasOptlock}).
	 * 
	 * @param dto
	 */
	void updateDto(DTO dto, FETCHOPTIONS fetchOptions);

}
