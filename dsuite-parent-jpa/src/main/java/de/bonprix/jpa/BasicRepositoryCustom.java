package de.bonprix.jpa;

import java.util.List;

public interface BasicRepositoryCustom<ENTITY, FILTER> {

	List<ENTITY> findAll(FILTER filter);

	ENTITY findOne(FILTER filter);

	int count(FILTER filter);

}
