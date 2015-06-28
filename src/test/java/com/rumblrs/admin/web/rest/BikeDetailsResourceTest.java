package com.rumblrs.admin.web.rest;

import com.rumblrs.admin.Application;
import com.rumblrs.admin.domain.BikeDetails;
import com.rumblrs.admin.repository.BikeDetailsRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BikeDetailsResource REST controller.
 *
 * @see BikeDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BikeDetailsResourceTest {

    private static final String DEFAULT_OWNER = "SAMPLE_TEXT";
    private static final String UPDATED_OWNER = "UPDATED_TEXT";
    private static final String DEFAULT_FEATURES = "SAMPLE_TEXT";
    private static final String UPDATED_FEATURES = "UPDATED_TEXT";
    private static final String DEFAULT_DOCUMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_DOCUMENTS = "UPDATED_TEXT";
    private static final String DEFAULT_REPORT = "SAMPLE_TEXT";
    private static final String UPDATED_REPORT = "UPDATED_TEXT";
    private static final String DEFAULT_PERFORMANCE = "SAMPLE_TEXT";
    private static final String UPDATED_PERFORMANCE = "UPDATED_TEXT";

    private static final Integer DEFAULT_DETAIL_ID = 0;
    private static final Integer UPDATED_DETAIL_ID = 1;

    @Inject
    private BikeDetailsRepository bikeDetailsRepository;

    private MockMvc restBikeDetailsMockMvc;

    private BikeDetails bikeDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BikeDetailsResource bikeDetailsResource = new BikeDetailsResource();
        ReflectionTestUtils.setField(bikeDetailsResource, "bikeDetailsRepository", bikeDetailsRepository);
        this.restBikeDetailsMockMvc = MockMvcBuilders.standaloneSetup(bikeDetailsResource).build();
    }

    @Before
    public void initTest() {
        bikeDetailsRepository.deleteAll();
        bikeDetails = new BikeDetails();
        bikeDetails.setOwner(DEFAULT_OWNER);
        bikeDetails.setFeatures(DEFAULT_FEATURES);
        bikeDetails.setDocuments(DEFAULT_DOCUMENTS);
        bikeDetails.setReport(DEFAULT_REPORT);
        bikeDetails.setPerformance(DEFAULT_PERFORMANCE);
        bikeDetails.setDetailId(DEFAULT_DETAIL_ID);
    }

    @Test
    public void createBikeDetails() throws Exception {
        int databaseSizeBeforeCreate = bikeDetailsRepository.findAll().size();

        // Create the BikeDetails
        restBikeDetailsMockMvc.perform(post("/api/bikeDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bikeDetails)))
                .andExpect(status().isCreated());

        // Validate the BikeDetails in the database
        List<BikeDetails> bikeDetailss = bikeDetailsRepository.findAll();
        assertThat(bikeDetailss).hasSize(databaseSizeBeforeCreate + 1);
        BikeDetails testBikeDetails = bikeDetailss.get(bikeDetailss.size() - 1);
        assertThat(testBikeDetails.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testBikeDetails.getFeatures()).isEqualTo(DEFAULT_FEATURES);
        assertThat(testBikeDetails.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
        assertThat(testBikeDetails.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testBikeDetails.getPerformance()).isEqualTo(DEFAULT_PERFORMANCE);
        assertThat(testBikeDetails.getDetailId()).isEqualTo(DEFAULT_DETAIL_ID);
    }

    @Test
    public void checkDetailIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bikeDetailsRepository.findAll()).hasSize(0);
        // set the field null
        bikeDetails.setDetailId(null);

        // Create the BikeDetails, which fails.
        restBikeDetailsMockMvc.perform(post("/api/bikeDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bikeDetails)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<BikeDetails> bikeDetailss = bikeDetailsRepository.findAll();
        assertThat(bikeDetailss).hasSize(0);
    }

    @Test
    public void getAllBikeDetailss() throws Exception {
        // Initialize the database
        bikeDetailsRepository.save(bikeDetails);

        // Get all the bikeDetailss
        restBikeDetailsMockMvc.perform(get("/api/bikeDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bikeDetails.getId())))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
                .andExpect(jsonPath("$.[*].features").value(hasItem(DEFAULT_FEATURES.toString())))
                .andExpect(jsonPath("$.[*].documents").value(hasItem(DEFAULT_DOCUMENTS.toString())))
                .andExpect(jsonPath("$.[*].report").value(hasItem(DEFAULT_REPORT.toString())))
                .andExpect(jsonPath("$.[*].performance").value(hasItem(DEFAULT_PERFORMANCE.toString())))
                .andExpect(jsonPath("$.[*].detailId").value(hasItem(DEFAULT_DETAIL_ID)));
    }

    @Test
    public void getBikeDetails() throws Exception {
        // Initialize the database
        bikeDetailsRepository.save(bikeDetails);

        // Get the bikeDetails
        restBikeDetailsMockMvc.perform(get("/api/bikeDetailss/{id}", bikeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bikeDetails.getId()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.features").value(DEFAULT_FEATURES.toString()))
            .andExpect(jsonPath("$.documents").value(DEFAULT_DOCUMENTS.toString()))
            .andExpect(jsonPath("$.report").value(DEFAULT_REPORT.toString()))
            .andExpect(jsonPath("$.performance").value(DEFAULT_PERFORMANCE.toString()))
            .andExpect(jsonPath("$.detailId").value(DEFAULT_DETAIL_ID));
    }

    @Test
    public void getNonExistingBikeDetails() throws Exception {
        // Get the bikeDetails
        restBikeDetailsMockMvc.perform(get("/api/bikeDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBikeDetails() throws Exception {
        // Initialize the database
        bikeDetailsRepository.save(bikeDetails);

		int databaseSizeBeforeUpdate = bikeDetailsRepository.findAll().size();

        // Update the bikeDetails
        bikeDetails.setOwner(UPDATED_OWNER);
        bikeDetails.setFeatures(UPDATED_FEATURES);
        bikeDetails.setDocuments(UPDATED_DOCUMENTS);
        bikeDetails.setReport(UPDATED_REPORT);
        bikeDetails.setPerformance(UPDATED_PERFORMANCE);
        bikeDetails.setDetailId(UPDATED_DETAIL_ID);
        restBikeDetailsMockMvc.perform(put("/api/bikeDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bikeDetails)))
                .andExpect(status().isOk());

        // Validate the BikeDetails in the database
        List<BikeDetails> bikeDetailss = bikeDetailsRepository.findAll();
        assertThat(bikeDetailss).hasSize(databaseSizeBeforeUpdate);
        BikeDetails testBikeDetails = bikeDetailss.get(bikeDetailss.size() - 1);
        assertThat(testBikeDetails.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testBikeDetails.getFeatures()).isEqualTo(UPDATED_FEATURES);
        assertThat(testBikeDetails.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testBikeDetails.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testBikeDetails.getPerformance()).isEqualTo(UPDATED_PERFORMANCE);
        assertThat(testBikeDetails.getDetailId()).isEqualTo(UPDATED_DETAIL_ID);
    }

    @Test
    public void deleteBikeDetails() throws Exception {
        // Initialize the database
        bikeDetailsRepository.save(bikeDetails);

		int databaseSizeBeforeDelete = bikeDetailsRepository.findAll().size();

        // Get the bikeDetails
        restBikeDetailsMockMvc.perform(delete("/api/bikeDetailss/{id}", bikeDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BikeDetails> bikeDetailss = bikeDetailsRepository.findAll();
        assertThat(bikeDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
