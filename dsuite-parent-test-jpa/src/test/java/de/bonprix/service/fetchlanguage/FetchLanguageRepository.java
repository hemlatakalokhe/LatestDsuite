package de.bonprix.service.fetchlanguage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FetchLanguageRepository
		extends JpaRepository<FetchLanguageEntity, Long>, FetchLanguageRepositoryCustom {

}