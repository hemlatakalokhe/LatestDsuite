package de.bonprix.jpa;

import java.util.List;

import de.bonprix.dto.FetchAllOptions;

public interface FetchRepositoryCustom<ENTITY, FILTER, FETCHOPTIONS extends FetchAllOptions>
		extends BasicRepositoryCustom<ENTITY, FILTER> {

	List<ENTITY> findAll(FILTER filter, FETCHOPTIONS fetchOptions);

	ENTITY findOne(FILTER filter, FETCHOPTIONS fetchOptions);

}
