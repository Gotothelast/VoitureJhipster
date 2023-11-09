package fr.it_akademy_voiturejhipster.repository;

import fr.it_akademy_voiturejhipster.domain.Mechanic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mechanic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {}
