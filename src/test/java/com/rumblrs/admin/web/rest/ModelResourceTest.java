package com.rumblrs.admin.web.rest;

import com.rumblrs.admin.Application;
import com.rumblrs.admin.domain.Model;
import com.rumblrs.admin.repository.ModelRepository;

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
 * Test class for the ModelResource REST controller.
 *
 * @see ModelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ModelResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Integer DEFAULT_ENGINE_CAPACITY = 0;
    private static final Integer UPDATED_ENGINE_CAPACITY = 1;
    private static final String DEFAULT_BRAND_ID = "SAMPLE_TEXT";
    private static final String UPDATED_BRAND_ID = "UPDATED_TEXT";

    @Inject
    private ModelRepository modelRepository;

    private MockMvc restModelMockMvc;

    private Model model;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModelResource modelResource = new ModelResource();
        ReflectionTestUtils.setField(modelResource, "modelRepository", modelRepository);
        this.restModelMockMvc = MockMvcBuilders.standaloneSetup(modelResource).build();
    }

    @Before
    public void initTest() {
        modelRepository.deleteAll();
        model = new Model();
        model.setName(DEFAULT_NAME);
        model.setEngineCapacity(DEFAULT_ENGINE_CAPACITY);
        model.setBrandId(DEFAULT_BRAND_ID);
    }

    @Test
    public void createModel() throws Exception {
        int databaseSizeBeforeCreate = modelRepository.findAll().size();

        // Create the Model
        restModelMockMvc.perform(post("/api/models")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(model)))
                .andExpect(status().isCreated());

        // Validate the Model in the database
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(databaseSizeBeforeCreate + 1);
        Model testModel = models.get(models.size() - 1);
        assertThat(testModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModel.getEngineCapacity()).isEqualTo(DEFAULT_ENGINE_CAPACITY);
        assertThat(testModel.getBrandId()).isEqualTo(DEFAULT_BRAND_ID);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(modelRepository.findAll()).hasSize(0);
        // set the field null
        model.setName(null);

        // Create the Model, which fails.
        restModelMockMvc.perform(post("/api/models")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(model)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(0);
    }

    @Test
    public void checkEngineCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(modelRepository.findAll()).hasSize(0);
        // set the field null
        model.setEngineCapacity(null);

        // Create the Model, which fails.
        restModelMockMvc.perform(post("/api/models")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(model)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(0);
    }

    @Test
    public void checkBrandIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(modelRepository.findAll()).hasSize(0);
        // set the field null
        model.setBrandId(null);

        // Create the Model, which fails.
        restModelMockMvc.perform(post("/api/models")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(model)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(0);
    }

    @Test
    public void getAllModels() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        // Get all the models
        restModelMockMvc.perform(get("/api/models"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(model.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].engineCapacity").value(hasItem(DEFAULT_ENGINE_CAPACITY)))
                .andExpect(jsonPath("$.[*].brandId").value(hasItem(DEFAULT_BRAND_ID.toString())));
    }

    @Test
    public void getModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        // Get the model
        restModelMockMvc.perform(get("/api/models/{id}", model.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(model.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.engineCapacity").value(DEFAULT_ENGINE_CAPACITY))
            .andExpect(jsonPath("$.brandId").value(DEFAULT_BRAND_ID.toString()));
    }

    @Test
    public void getNonExistingModel() throws Exception {
        // Get the model
        restModelMockMvc.perform(get("/api/models/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

		int databaseSizeBeforeUpdate = modelRepository.findAll().size();

        // Update the model
        model.setName(UPDATED_NAME);
        model.setEngineCapacity(UPDATED_ENGINE_CAPACITY);
        model.setBrandId(UPDATED_BRAND_ID);
        restModelMockMvc.perform(put("/api/models")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(model)))
                .andExpect(status().isOk());

        // Validate the Model in the database
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(databaseSizeBeforeUpdate);
        Model testModel = models.get(models.size() - 1);
        assertThat(testModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModel.getEngineCapacity()).isEqualTo(UPDATED_ENGINE_CAPACITY);
        assertThat(testModel.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
    }

    @Test
    public void deleteModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

		int databaseSizeBeforeDelete = modelRepository.findAll().size();

        // Get the model
        restModelMockMvc.perform(delete("/api/models/{id}", model.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Model> models = modelRepository.findAll();
        assertThat(models).hasSize(databaseSizeBeforeDelete - 1);
    }
}
