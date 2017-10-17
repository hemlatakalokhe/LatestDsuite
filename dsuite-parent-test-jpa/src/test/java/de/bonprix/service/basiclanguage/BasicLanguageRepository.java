package de.bonprix.service.basiclanguage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicLanguageRepository
		extends JpaRepository<BasicLanguageEntity, Long>, BasicLanguageRepositoryCustom {

}