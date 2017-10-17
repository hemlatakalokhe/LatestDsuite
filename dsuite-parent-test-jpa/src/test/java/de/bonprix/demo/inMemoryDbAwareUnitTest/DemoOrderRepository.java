package de.bonprix.demo.inMemoryDbAwareUnitTest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vbaghdas
 */

@Repository
public interface DemoOrderRepository extends JpaRepository<DemoOrder, Long> {

}
