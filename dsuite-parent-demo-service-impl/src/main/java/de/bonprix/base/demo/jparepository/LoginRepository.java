package de.bonprix.base.demo.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bonprix.base.demo.model.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
}
