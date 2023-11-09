package fr.it_akademy_voiturejhipster.service;

import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy_voiturejhipster.domain.Mechanic}.
 */
public interface MechanicService {
    /**
     * Save a mechanic.
     *
     * @param mechanicDTO the entity to save.
     * @return the persisted entity.
     */
    MechanicDTO save(MechanicDTO mechanicDTO);

    /**
     * Updates a mechanic.
     *
     * @param mechanicDTO the entity to update.
     * @return the persisted entity.
     */
    MechanicDTO update(MechanicDTO mechanicDTO);

    /**
     * Partially updates a mechanic.
     *
     * @param mechanicDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MechanicDTO> partialUpdate(MechanicDTO mechanicDTO);

    /**
     * Get all the mechanics.
     *
     * @return the list of entities.
     */
    List<MechanicDTO> findAll();

    /**
     * Get all the MechanicDTO where Car is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MechanicDTO> findAllWhereCarIsNull();

    /**
     * Get the "id" mechanic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MechanicDTO> findOne(Long id);

    /**
     * Delete the "id" mechanic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
