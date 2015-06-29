package com.rumblrs.admin.web.rest;

import com.rumblrs.admin.Application;
import com.rumblrs.admin.domain.Body;
import com.rumblrs.admin.repository.BodyRepository;

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
 * Test class for the BodyResource REST controller.
 *
 * @see BodyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BodyResourceTest {

    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";

    @Inject
    private BodyRepository bodyRepository;

    private MockMvc restBodyMockMvc;

    private Body body;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BodyResource bodyResource = new BodyResource();
        ReflectionTestUtils.setField(bodyResource, "bodyRepository", bodyRepository);
        this.restBodyMockMvc = MockMvcBuilders.standaloneSetup(bodyResource).build();
    }

    @Before
    public void initTest() {
        bodyRepository.deleteAll();
        body = new Body();
        body.setType(DEFAULT_TYPE);
    }

    @Test
    public void createBody() throws Exception {
        int databaseSizeBeforeCreate = bodyRepository.findAll().size();

        // Create the Body
        restBodyMockMvc.perform(post("/api/bodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(body)))
                .andExpect(status().isCreated());

        // Validate the Body in the database
        List<Body> bodys = bodyRepository.findAll();
        assertThat(bodys).hasSize(databaseSizeBeforeCreate + 1);
        Body testBody = bodys.get(bodys.size() - 1);
        assertThat(testBody.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bodyRepository.findAll()).hasSize(0);
        // set the field null
        body.setType(null);

        // Create the Body, which fails.
        restBodyMockMvc.perform(post("/api/bodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(body)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Body> bodys = bodyRepository.findAll();
        assertThat(bodys).hasSize(0);
    }

    @Test
    public void getAllBodys() throws Exception {
        // Initialize the database
        bodyRepository.save(body);

        // Get all the bodys
        restBodyMockMvc.perform(get("/api/bodys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(body.getId())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    public void getBody() throws Exception {
        // Initialize the database
        bodyRepository.save(body);

        // Get the body
        restBodyMockMvc.perform(get("/api/bodys/{id}", body.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(body.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingBody() throws Exception {
        // Get the body
        restBodyMockMvc.perform(get("/api/bodys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBody() throws Exception {
        // Initialize the database
        bodyRepository.save(body);

		int databaseSizeBeforeUpdate = bodyRepository.findAll().size();

        // Update the body
        body.setType(UPDATED_TYPE);
        restBodyMockMvc.perform(put("/api/bodys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(body)))
                .andExpect(status().isOk());

        // Validate the Body in the database
        List<Body> bodys = bodyRepository.findAll();
        assertThat(bodys).hasSize(databaseSizeBeforeUpdate);
        Body testBody = bodys.get(bodys.size() - 1);
        assertThat(testBody.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void deleteBody() throws Exception {
        // Initialize the database
        bodyRepository.save(body);

		int databaseSizeBeforeDelete = bodyRepository.findAll().size();

        // Get the body
        restBodyMockMvc.perform(delete("/api/bodys/{id}", body.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Body> bodys = bodyRepository.findAll();
        assertThat(bodys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
