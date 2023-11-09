package fr.it_akademy_voiturejhipster.repository;

import fr.it_akademy_voiturejhipster.domain.Agence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {}
