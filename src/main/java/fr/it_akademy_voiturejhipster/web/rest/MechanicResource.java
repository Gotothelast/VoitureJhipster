package fr.it_akademy_voiturejhipster.web.rest;

import fr.it_akademy_voiturejhipster.repository.MechanicRepository;
import fr.it_akademy_voiturejhipster.service.MechanicService;
import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import fr.it_akademy_voiturejhipster.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy_voiturejhipster.domain.Mechanic}.
 */
@RestController
@RequestMapping("/api/mechanics")
public class MechanicResource {

    private final Logger log = LoggerFactory.getLogger(MechanicResource.class);

    private static final String ENTITY_NAME = "mechanic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MechanicService mechanicService;

    private final MechanicRepository mechanicRepository;

    public MechanicResource(MechanicService mechanicService, MechanicRepository mechanicRepository) {
        this.mechanicService = mechanicService;
        this.mechanicRepository = mechanicRepository;
    }

    /**
     * {@code POST  /mechanics} : Create a new mechanic.
     *
     * @param mechanicDTO the mechanicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mechanicDTO, or with status {@code 400 (Bad Request)} if the mechanic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MechanicDTO> createMechanic(@RequestBody MechanicDTO mechanicDTO) throws URISyntaxException {
        log.debug("REST request to save Mechanic : {}", mechanicDTO);
        if (mechanicDTO.getId() != null) {
            throw new BadRequestAlertException("A new mechanic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MechanicDTO result = mechanicService.save(mechanicDTO);
        return ResponseEntity
            .created(new URI("/api/mechanics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mechanics/:id} : Updates an existing mechanic.
     *
     * @param id the id of the mechanicDTO to save.
     * @param mechanicDTO the mechanicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mechanicDTO,
     * or with status {@code 400 (Bad Request)} if the mechanicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mechanicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MechanicDTO> updateMechanic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MechanicDTO mechanicDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Mechanic : {}, {}", id, mechanicDTO);
        if (mechanicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mechanicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mechanicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MechanicDTO result = mechanicService.update(mechanicDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mechanicDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mechanics/:id} : Partial updates given fields of an existing mechanic, field will ignore if it is null
     *
     * @param id the id of the mechanicDTO to save.
     * @param mechanicDTO the mechanicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mechanicDTO,
     * or with status {@code 400 (Bad Request)} if the mechanicDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mechanicDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mechanicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MechanicDTO> partialUpdateMechanic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MechanicDTO mechanicDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mechanic partially : {}, {}", id, mechanicDTO);
        if (mechanicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mechanicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mechanicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MechanicDTO> result = mechanicService.partialUpdate(mechanicDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mechanicDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mechanics} : get all the mechanics.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mechanics in body.
     */
    @GetMapping("")
    public List<MechanicDTO> getAllMechanics(@RequestParam(required = false) String filter) {
        if ("car-is-null".equals(filter)) {
            log.debug("REST request to get all Mechanics where car is null");
            return mechanicService.findAllWhereCarIsNull();
        }
        log.debug("REST request to get all Mechanics");
        return mechanicService.findAll();
    }

    /**
     * {@code GET  /mechanics/:id} : get the "id" mechanic.
     *
     * @param id the id of the mechanicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mechanicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MechanicDTO> getMechanic(@PathVariable Long id) {
        log.debug("REST request to get Mechanic : {}", id);
        Optional<MechanicDTO> mechanicDTO = mechanicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mechanicDTO);
    }

    /**
     * {@code DELETE  /mechanics/:id} : delete the "id" mechanic.
     *
     * @param id the id of the mechanicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanic(@PathVariable Long id) {
        log.debug("REST request to delete Mechanic : {}", id);
        mechanicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
