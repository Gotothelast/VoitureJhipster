package fr.it_akademy_voiturejhipster.repository;

import fr.it_akademy_voiturejhipster.domain.Car;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {}
