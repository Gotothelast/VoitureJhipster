package fr.it_akademy_voiturejhipster.service;

import fr.it_akademy_voiturejhipster.service.dto.OptionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy_voiturejhipster.domain.Option}.
 */
public interface OptionService {
    /**
     * Save a option.
     *
     * @param optionDTO the entity to save.
     * @return the persisted entity.
     */
    OptionDTO save(OptionDTO optionDTO);

    /**
     * Updates a option.
     *
     * @param optionDTO the entity to update.
     * @return the persisted entity.
     */
    OptionDTO update(OptionDTO optionDTO);

    /**
     * Partially updates a option.
     *
     * @param optionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OptionDTO> partialUpdate(OptionDTO optionDTO);

    /**
     * Get all the options.
     *
     * @return the list of entities.
     */
    List<OptionDTO> findAll();

    /**
     * Get the "id" option.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionDTO> findOne(Long id);

    /**
     * Delete the "id" option.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
