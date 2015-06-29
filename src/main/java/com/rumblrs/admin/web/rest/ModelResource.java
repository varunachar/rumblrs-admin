package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.Model;
import com.rumblrs.admin.repository.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Model.
 */
@RestController
@RequestMapping("/api")
public class ModelResource {

    private final Logger log = LoggerFactory.getLogger(ModelResource.class);

    @Inject
    private ModelRepository modelRepository;

    /**
     * POST  /models -> Create a new model.
     */
    @RequestMapping(value = "/models",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Model model) throws URISyntaxException {
        log.debug("REST request to save Model : {}", model);
        if (model.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new model cannot already have an ID").build();
        }
        modelRepository.save(model);
        return ResponseEntity.created(new URI("/api/models/" + model.getId())).build();
    }

    /**
     * PUT  /models -> Updates an existing model.
     */
    @RequestMapping(value = "/models",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Model model) throws URISyntaxException {
        log.debug("REST request to update Model : {}", model);
        if (model.getId() == null) {
            return create(model);
        }
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /models -> get all the models.
     */
    @RequestMapping(value = "/models",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Model> getAll() {
        log.debug("REST request to get all Models");
        return modelRepository.findAll();
    }

    /**
     * GET  /models/:id -> get the "id" model.
     */
    @RequestMapping(value = "/models/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Model> get(@PathVariable String id) {
        log.debug("REST request to get Model : {}", id);
        return Optional.ofNullable(modelRepository.findOne(id))
            .map(model -> new ResponseEntity<>(
                model,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /models/:id -> delete the "id" model.
     */
    @RequestMapping(value = "/models/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Model : {}", id);
        modelRepository.delete(id);
    }
}
