package com.rumblrs.admin.web.rest;

import com.rumblrs.admin.Application;
import com.rumblrs.admin.domain.Bike;
import com.rumblrs.admin.repository.BikeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BikeResource REST controller.
 *
 * @see BikeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BikeResourceTest {

    private static final String DEFAULT_BRAND = "SAMPLE_TEXT";
    private static final String UPDATED_BRAND = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_ENGINE_CAPACITY = 0;
    private static final Integer UPDATED_ENGINE_CAPACITY = 1;

    private static final Integer DEFAULT_YEAR_OF_MANUFACTURE = 0;
    private static final Integer UPDATED_YEAR_OF_MANUFACTURE = 1;
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";

    private static final Integer DEFAULT_KMS = 0;
    private static final Integer UPDATED_KMS = 1;

    private static final Integer DEFAULT_OWNERS = 0;
    private static final Integer UPDATED_OWNERS = 1;

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    private static final BigDecimal DEFAULT_SCORE = new BigDecimal(0);
    private static final BigDecimal UPDATED_SCORE = new BigDecimal(1);

    private static final Integer DEFAULT_TYPE = 0;
    private static final Integer UPDATED_TYPE = 1;
    private static final String DEFAULT_THUMBNAIL = "SAMPLE_TEXT";
    private static final String UPDATED_THUMBNAIL = "UPDATED_TEXT";

    private static final Boolean DEFAULT_RESERVED = false;
    private static final Boolean UPDATED_RESERVED = true;

    private static final Boolean DEFAULT_SOLD = false;
    private static final Boolean UPDATED_SOLD = true;
    private static final String DEFAULT_BIKE_ID = "SAMPLE_TEXT";
    private static final String UPDATED_BIKE_ID = "UPDATED_TEXT";
    private static final String DEFAULT_DETAIL_ID = "SAMPLE_TEXT";
    private static final String UPDATED_DETAIL_ID = "UPDATED_TEXT";

    @Inject
    private BikeRepository bikeRepository;

    private MockMvc restBikeMockMvc;

    private Bike bike;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BikeResource bikeResource = new BikeResource();
        ReflectionTestUtils.setField(bikeResource, "bikeRepository", bikeRepository);
        this.restBikeMockMvc = MockMvcBuilders.standaloneSetup(bikeResource).build();
    }

    @Before
    public void initTest() {
        bikeRepository.deleteAll();
        bike = new Bike();
        bike.setBrand(DEFAULT_BRAND);
        bike.setName(DEFAULT_NAME);
        bike.setEngineCapacity(DEFAULT_ENGINE_CAPACITY);
        bike.setYearOfManufacture(DEFAULT_YEAR_OF_MANUFACTURE);
        bike.setLocation(DEFAULT_LOCATION);
        bike.setKms(DEFAULT_KMS);
        bike.setOwners(DEFAULT_OWNERS);
        bike.setPrice(DEFAULT_PRICE);
        bike.setScore(DEFAULT_SCORE);
        bike.setType(DEFAULT_TYPE);
        bike.setThumbnail(DEFAULT_THUMBNAIL);
        bike.setReserved(DEFAULT_RESERVED);
        bike.setSold(DEFAULT_SOLD);
        bike.setBikeId(DEFAULT_BIKE_ID);
        bike.setDetailId(DEFAULT_DETAIL_ID);
    }

    @Test
    public void createBike() throws Exception {
        int databaseSizeBeforeCreate = bikeRepository.findAll().size();

        // Create the Bike
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isCreated());

        // Validate the Bike in the database
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(databaseSizeBeforeCreate + 1);
        Bike testBike = bikes.get(bikes.size() - 1);
        assertThat(testBike.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testBike.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBike.getEngineCapacity()).isEqualTo(DEFAULT_ENGINE_CAPACITY);
        assertThat(testBike.getYearOfManufacture()).isEqualTo(DEFAULT_YEAR_OF_MANUFACTURE);
        assertThat(testBike.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testBike.getKms()).isEqualTo(DEFAULT_KMS);
        assertThat(testBike.getOwners()).isEqualTo(DEFAULT_OWNERS);
        assertThat(testBike.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBike.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testBike.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBike.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testBike.getReserved()).isEqualTo(DEFAULT_RESERVED);
        assertThat(testBike.getSold()).isEqualTo(DEFAULT_SOLD);
        assertThat(testBike.getBikeId()).isEqualTo(DEFAULT_BIKE_ID);
        assertThat(testBike.getDetailId()).isEqualTo(DEFAULT_DETAIL_ID);
    }

    @Test
    public void checkBrandIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setBrand(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setName(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkEngineCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setEngineCapacity(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkYearOfManufactureIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setYearOfManufacture(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkLocationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setLocation(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkKmsIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setKms(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkOwnersIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setOwners(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkPriceIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setPrice(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkScoreIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setScore(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setType(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkBikeIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setBikeId(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void checkDetailIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeRepository.findAll()).hasSize(0);
        // set the field null
        bike.setDetailId(null);

        // Create the Bike, which fails.
        restBikeMockMvc.perform(post("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(0);
    }

    @Test
    public void getAllBikes() throws Exception {
        // Initialize the database
        bikeRepository.save(bike);

        // Get all the bikes
        restBikeMockMvc.perform(get("/api/bikes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bike.getId())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].engineCapacity").value(hasItem(DEFAULT_ENGINE_CAPACITY)))
                .andExpect(jsonPath("$.[*].yearOfManufacture").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURE)))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].kms").value(hasItem(DEFAULT_KMS)))
                .andExpect(jsonPath("$.[*].owners").value(hasItem(DEFAULT_OWNERS)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
                .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL.toString())))
                .andExpect(jsonPath("$.[*].reserved").value(hasItem(DEFAULT_RESERVED.booleanValue())))
                .andExpect(jsonPath("$.[*].sold").value(hasItem(DEFAULT_SOLD.booleanValue())))
                .andExpect(jsonPath("$.[*].bikeId").value(hasItem(DEFAULT_BIKE_ID.toString())))
                .andExpect(jsonPath("$.[*].detailId").value(hasItem(DEFAULT_DETAIL_ID.toString())));
    }

    @Test
    public void getBike() throws Exception {
        // Initialize the database
        bikeRepository.save(bike);

        // Get the bike
        restBikeMockMvc.perform(get("/api/bikes/{id}", bike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bike.getId()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.engineCapacity").value(DEFAULT_ENGINE_CAPACITY))
            .andExpect(jsonPath("$.yearOfManufacture").value(DEFAULT_YEAR_OF_MANUFACTURE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.kms").value(DEFAULT_KMS))
            .andExpect(jsonPath("$.owners").value(DEFAULT_OWNERS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL.toString()))
            .andExpect(jsonPath("$.reserved").value(DEFAULT_RESERVED.booleanValue()))
            .andExpect(jsonPath("$.sold").value(DEFAULT_SOLD.booleanValue()))
            .andExpect(jsonPath("$.bikeId").value(DEFAULT_BIKE_ID.toString()))
            .andExpect(jsonPath("$.detailId").value(DEFAULT_DETAIL_ID.toString()));
    }

    @Test
    public void getNonExistingBike() throws Exception {
        // Get the bike
        restBikeMockMvc.perform(get("/api/bikes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBike() throws Exception {
        // Initialize the database
        bikeRepository.save(bike);

		int databaseSizeBeforeUpdate = bikeRepository.findAll().size();

        // Update the bike
        bike.setBrand(UPDATED_BRAND);
        bike.setName(UPDATED_NAME);
        bike.setEngineCapacity(UPDATED_ENGINE_CAPACITY);
        bike.setYearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE);
        bike.setLocation(UPDATED_LOCATION);
        bike.setKms(UPDATED_KMS);
        bike.setOwners(UPDATED_OWNERS);
        bike.setPrice(UPDATED_PRICE);
        bike.setScore(UPDATED_SCORE);
        bike.setType(UPDATED_TYPE);
        bike.setThumbnail(UPDATED_THUMBNAIL);
        bike.setReserved(UPDATED_RESERVED);
        bike.setSold(UPDATED_SOLD);
        bike.setBikeId(UPDATED_BIKE_ID);
        bike.setDetailId(UPDATED_DETAIL_ID);
        restBikeMockMvc.perform(put("/api/bikes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bike)))
                .andExpect(status().isOk());

        // Validate the Bike in the database
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(databaseSizeBeforeUpdate);
        Bike testBike = bikes.get(bikes.size() - 1);
        assertThat(testBike.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBike.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBike.getEngineCapacity()).isEqualTo(UPDATED_ENGINE_CAPACITY);
        assertThat(testBike.getYearOfManufacture()).isEqualTo(UPDATED_YEAR_OF_MANUFACTURE);
        assertThat(testBike.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testBike.getKms()).isEqualTo(UPDATED_KMS);
        assertThat(testBike.getOwners()).isEqualTo(UPDATED_OWNERS);
        assertThat(testBike.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBike.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testBike.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBike.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testBike.getReserved()).isEqualTo(UPDATED_RESERVED);
        assertThat(testBike.getSold()).isEqualTo(UPDATED_SOLD);
        assertThat(testBike.getBikeId()).isEqualTo(UPDATED_BIKE_ID);
        assertThat(testBike.getDetailId()).isEqualTo(UPDATED_DETAIL_ID);
    }

    @Test
    public void deleteBike() throws Exception {
        // Initialize the database
        bikeRepository.save(bike);

		int databaseSizeBeforeDelete = bikeRepository.findAll().size();

        // Get the bike
        restBikeMockMvc.perform(delete("/api/bikes/{id}", bike.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bike> bikes = bikeRepository.findAll();
        assertThat(bikes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
