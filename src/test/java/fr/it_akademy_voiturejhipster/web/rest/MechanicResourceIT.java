package fr.it_akademy_voiturejhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy_voiturejhipster.IntegrationTest;
import fr.it_akademy_voiturejhipster.domain.Mechanic;
import fr.it_akademy_voiturejhipster.repository.MechanicRepository;
import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import fr.it_akademy_voiturejhipster.service.mapper.MechanicMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MechanicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MechanicResourceIT {

    private static final String DEFAULT_MOTOR = "AAAAAAAAAA";
    private static final String UPDATED_MOTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_POWER = 1;
    private static final Integer UPDATED_POWER = 2;

    private static final Integer DEFAULT_KM = 1;
    private static final Integer UPDATED_KM = 2;

    private static final String ENTITY_API_URL = "/api/mechanics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MechanicRepository mechanicRepository;

    @Autowired
    private MechanicMapper mechanicMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMechanicMockMvc;

    private Mechanic mechanic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mechanic createEntity(EntityManager em) {
        Mechanic mechanic = new Mechanic().motor(DEFAULT_MOTOR).power(DEFAULT_POWER).km(DEFAULT_KM);
        return mechanic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mechanic createUpdatedEntity(EntityManager em) {
        Mechanic mechanic = new Mechanic().motor(UPDATED_MOTOR).power(UPDATED_POWER).km(UPDATED_KM);
        return mechanic;
    }

    @BeforeEach
    public void initTest() {
        mechanic = createEntity(em);
    }

    @Test
    @Transactional
    void createMechanic() throws Exception {
        int databaseSizeBeforeCreate = mechanicRepository.findAll().size();
        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);
        restMechanicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isCreated());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeCreate + 1);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getMotor()).isEqualTo(DEFAULT_MOTOR);
        assertThat(testMechanic.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testMechanic.getKm()).isEqualTo(DEFAULT_KM);
    }

    @Test
    @Transactional
    void createMechanicWithExistingId() throws Exception {
        // Create the Mechanic with an existing ID
        mechanic.setId(1L);
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        int databaseSizeBeforeCreate = mechanicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMechanicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMechanics() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        // Get all the mechanicList
        restMechanicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mechanic.getId().intValue())))
            .andExpect(jsonPath("$.[*].motor").value(hasItem(DEFAULT_MOTOR)))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER)))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM)));
    }

    @Test
    @Transactional
    void getMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        // Get the mechanic
        restMechanicMockMvc
            .perform(get(ENTITY_API_URL_ID, mechanic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mechanic.getId().intValue()))
            .andExpect(jsonPath("$.motor").value(DEFAULT_MOTOR))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM));
    }

    @Test
    @Transactional
    void getNonExistingMechanic() throws Exception {
        // Get the mechanic
        restMechanicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();

        // Update the mechanic
        Mechanic updatedMechanic = mechanicRepository.findById(mechanic.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMechanic are not directly saved in db
        em.detach(updatedMechanic);
        updatedMechanic.motor(UPDATED_MOTOR).power(UPDATED_POWER).km(UPDATED_KM);
        MechanicDTO mechanicDTO = mechanicMapper.toDto(updatedMechanic);

        restMechanicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mechanicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isOk());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getMotor()).isEqualTo(UPDATED_MOTOR);
        assertThat(testMechanic.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testMechanic.getKm()).isEqualTo(UPDATED_KM);
    }

    @Test
    @Transactional
    void putNonExistingMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mechanicDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mechanicDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMechanicWithPatch() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();

        // Update the mechanic using partial update
        Mechanic partialUpdatedMechanic = new Mechanic();
        partialUpdatedMechanic.setId(mechanic.getId());

        partialUpdatedMechanic.motor(UPDATED_MOTOR).power(UPDATED_POWER).km(UPDATED_KM);

        restMechanicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMechanic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMechanic))
            )
            .andExpect(status().isOk());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getMotor()).isEqualTo(UPDATED_MOTOR);
        assertThat(testMechanic.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testMechanic.getKm()).isEqualTo(UPDATED_KM);
    }

    @Test
    @Transactional
    void fullUpdateMechanicWithPatch() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();

        // Update the mechanic using partial update
        Mechanic partialUpdatedMechanic = new Mechanic();
        partialUpdatedMechanic.setId(mechanic.getId());

        partialUpdatedMechanic.motor(UPDATED_MOTOR).power(UPDATED_POWER).km(UPDATED_KM);

        restMechanicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMechanic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMechanic))
            )
            .andExpect(status().isOk());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
        Mechanic testMechanic = mechanicList.get(mechanicList.size() - 1);
        assertThat(testMechanic.getMotor()).isEqualTo(UPDATED_MOTOR);
        assertThat(testMechanic.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testMechanic.getKm()).isEqualTo(UPDATED_KM);
    }

    @Test
    @Transactional
    void patchNonExistingMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mechanicDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMechanic() throws Exception {
        int databaseSizeBeforeUpdate = mechanicRepository.findAll().size();
        mechanic.setId(longCount.incrementAndGet());

        // Create the Mechanic
        MechanicDTO mechanicDTO = mechanicMapper.toDto(mechanic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMechanicMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mechanicDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mechanic in the database
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMechanic() throws Exception {
        // Initialize the database
        mechanicRepository.saveAndFlush(mechanic);

        int databaseSizeBeforeDelete = mechanicRepository.findAll().size();

        // Delete the mechanic
        restMechanicMockMvc
            .perform(delete(ENTITY_API_URL_ID, mechanic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mechanic> mechanicList = mechanicRepository.findAll();
        assertThat(mechanicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
