package fr.it_akademy_voiturejhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy_voiturejhipster.IntegrationTest;
import fr.it_akademy_voiturejhipster.domain.Car;
import fr.it_akademy_voiturejhipster.repository.CarRepository;
import fr.it_akademy_voiturejhipster.service.dto.CarDTO;
import fr.it_akademy_voiturejhipster.service.mapper.CarMapper;
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
 * Integration tests for the {@link CarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarResourceIT {

    private static final String DEFAULT_CAR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAR_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_CAR_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_CAR_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_CAR_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAR_YEAR = 1;
    private static final Integer UPDATED_CAR_YEAR = 2;

    private static final Integer DEFAULT_CAR_PRICE = 1;
    private static final Integer UPDATED_CAR_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/cars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarMockMvc;

    private Car car;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createEntity(EntityManager em) {
        Car car = new Car()
            .carName(DEFAULT_CAR_NAME)
            .carModel(DEFAULT_CAR_MODEL)
            .carReference(DEFAULT_CAR_REFERENCE)
            .carYear(DEFAULT_CAR_YEAR)
            .carPrice(DEFAULT_CAR_PRICE);
        return car;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createUpdatedEntity(EntityManager em) {
        Car car = new Car()
            .carName(UPDATED_CAR_NAME)
            .carModel(UPDATED_CAR_MODEL)
            .carReference(UPDATED_CAR_REFERENCE)
            .carYear(UPDATED_CAR_YEAR)
            .carPrice(UPDATED_CAR_PRICE);
        return car;
    }

    @BeforeEach
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();
        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);
        restCarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getCarName()).isEqualTo(DEFAULT_CAR_NAME);
        assertThat(testCar.getCarModel()).isEqualTo(DEFAULT_CAR_MODEL);
        assertThat(testCar.getCarReference()).isEqualTo(DEFAULT_CAR_REFERENCE);
        assertThat(testCar.getCarYear()).isEqualTo(DEFAULT_CAR_YEAR);
        assertThat(testCar.getCarPrice()).isEqualTo(DEFAULT_CAR_PRICE);
    }

    @Test
    @Transactional
    void createCarWithExistingId() throws Exception {
        // Create the Car with an existing ID
        car.setId(1L);
        CarDTO carDTO = carMapper.toDto(car);

        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCars() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].carName").value(hasItem(DEFAULT_CAR_NAME)))
            .andExpect(jsonPath("$.[*].carModel").value(hasItem(DEFAULT_CAR_MODEL)))
            .andExpect(jsonPath("$.[*].carReference").value(hasItem(DEFAULT_CAR_REFERENCE)))
            .andExpect(jsonPath("$.[*].carYear").value(hasItem(DEFAULT_CAR_YEAR)))
            .andExpect(jsonPath("$.[*].carPrice").value(hasItem(DEFAULT_CAR_PRICE)));
    }

    @Test
    @Transactional
    void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc
            .perform(get(ENTITY_API_URL_ID, car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId().intValue()))
            .andExpect(jsonPath("$.carName").value(DEFAULT_CAR_NAME))
            .andExpect(jsonPath("$.carModel").value(DEFAULT_CAR_MODEL))
            .andExpect(jsonPath("$.carReference").value(DEFAULT_CAR_REFERENCE))
            .andExpect(jsonPath("$.carYear").value(DEFAULT_CAR_YEAR))
            .andExpect(jsonPath("$.carPrice").value(DEFAULT_CAR_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findById(car.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCar are not directly saved in db
        em.detach(updatedCar);
        updatedCar
            .carName(UPDATED_CAR_NAME)
            .carModel(UPDATED_CAR_MODEL)
            .carReference(UPDATED_CAR_REFERENCE)
            .carYear(UPDATED_CAR_YEAR)
            .carPrice(UPDATED_CAR_PRICE);
        CarDTO carDTO = carMapper.toDto(updatedCar);

        restCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carDTO))
            )
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getCarName()).isEqualTo(UPDATED_CAR_NAME);
        assertThat(testCar.getCarModel()).isEqualTo(UPDATED_CAR_MODEL);
        assertThat(testCar.getCarReference()).isEqualTo(UPDATED_CAR_REFERENCE);
        assertThat(testCar.getCarYear()).isEqualTo(UPDATED_CAR_YEAR);
        assertThat(testCar.getCarPrice()).isEqualTo(UPDATED_CAR_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarWithPatch() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car using partial update
        Car partialUpdatedCar = new Car();
        partialUpdatedCar.setId(car.getId());

        partialUpdatedCar.carReference(UPDATED_CAR_REFERENCE).carYear(UPDATED_CAR_YEAR).carPrice(UPDATED_CAR_PRICE);

        restCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCar))
            )
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getCarName()).isEqualTo(DEFAULT_CAR_NAME);
        assertThat(testCar.getCarModel()).isEqualTo(DEFAULT_CAR_MODEL);
        assertThat(testCar.getCarReference()).isEqualTo(UPDATED_CAR_REFERENCE);
        assertThat(testCar.getCarYear()).isEqualTo(UPDATED_CAR_YEAR);
        assertThat(testCar.getCarPrice()).isEqualTo(UPDATED_CAR_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateCarWithPatch() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car using partial update
        Car partialUpdatedCar = new Car();
        partialUpdatedCar.setId(car.getId());

        partialUpdatedCar
            .carName(UPDATED_CAR_NAME)
            .carModel(UPDATED_CAR_MODEL)
            .carReference(UPDATED_CAR_REFERENCE)
            .carYear(UPDATED_CAR_YEAR)
            .carPrice(UPDATED_CAR_PRICE);

        restCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCar))
            )
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getCarName()).isEqualTo(UPDATED_CAR_NAME);
        assertThat(testCar.getCarModel()).isEqualTo(UPDATED_CAR_MODEL);
        assertThat(testCar.getCarReference()).isEqualTo(UPDATED_CAR_REFERENCE);
        assertThat(testCar.getCarYear()).isEqualTo(UPDATED_CAR_YEAR);
        assertThat(testCar.getCarPrice()).isEqualTo(UPDATED_CAR_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();
        car.setId(longCount.incrementAndGet());

        // Create the Car
        CarDTO carDTO = carMapper.toDto(car);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Delete the car
        restCarMockMvc.perform(delete(ENTITY_API_URL_ID, car.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
