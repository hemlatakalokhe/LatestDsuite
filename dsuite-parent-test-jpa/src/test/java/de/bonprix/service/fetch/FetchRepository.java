package de.bonprix.service.fetch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FetchRepository
		extends JpaRepository<FetchEntity, Long>, FetchRepositoryCustom {

}