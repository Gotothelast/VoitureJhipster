package fr.it_akademy_voiturejhipster.repository;

import fr.it_akademy_voiturejhipster.domain.Option;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Option entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {}
