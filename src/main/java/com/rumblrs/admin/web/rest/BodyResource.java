package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.Body;
import com.rumblrs.admin.repository.BodyRepository;
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
 * REST controller for managing Body.
 */
@RestController
@RequestMapping("/api")
public class BodyResource {

    private final Logger log = LoggerFactory.getLogger(BodyResource.class);

    @Inject
    private BodyRepository bodyRepository;

    /**
     * POST  /bodys -> Create a new body.
     */
    @RequestMapping(value = "/bodys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Body body) throws URISyntaxException {
        log.debug("REST request to save Body : {}", body);
        if (body.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new body cannot already have an ID").build();
        }
        bodyRepository.save(body);
        return ResponseEntity.created(new URI("/api/bodys/" + body.getId())).build();
    }

    /**
     * PUT  /bodys -> Updates an existing body.
     */
    @RequestMapping(value = "/bodys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Body body) throws URISyntaxException {
        log.debug("REST request to update Body : {}", body);
        if (body.getId() == null) {
            return create(body);
        }
        bodyRepository.save(body);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bodys -> get all the bodys.
     */
    @RequestMapping(value = "/bodys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Body> getAll() {
        log.debug("REST request to get all Bodys");
        return bodyRepository.findAll();
    }

    /**
     * GET  /bodys/:id -> get the "id" body.
     */
    @RequestMapping(value = "/bodys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Body> get(@PathVariable String id) {
        log.debug("REST request to get Body : {}", id);
        return Optional.ofNullable(bodyRepository.findOne(id))
            .map(body -> new ResponseEntity<>(
                body,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bodys/:id -> delete the "id" body.
     */
    @RequestMapping(value = "/bodys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Body : {}", id);
        bodyRepository.delete(id);
    }
}
