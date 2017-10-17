package de.bonprix.base.demo.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bonprix.base.demo.model.StyleEntity;

@Repository
public interface StyleRepository extends JpaRepository<StyleEntity, Long> {

}
