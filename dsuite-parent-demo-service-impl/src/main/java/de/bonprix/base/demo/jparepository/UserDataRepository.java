package de.bonprix.base.demo.jparepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bonprix.base.demo.model.UserDataEntity;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, Long>{

}
