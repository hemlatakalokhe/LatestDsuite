package de.bonprix.service.basic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicRepository
		extends JpaRepository<BasicEntity, Long>, BasicRepositoryCustom {

}