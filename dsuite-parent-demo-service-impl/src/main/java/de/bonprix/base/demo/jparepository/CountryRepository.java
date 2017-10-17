package de.bonprix.base.demo.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bonprix.base.demo.model.CountryEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
