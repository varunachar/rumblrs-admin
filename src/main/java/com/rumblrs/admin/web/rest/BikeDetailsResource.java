package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.BikeDetails;
import com.rumblrs.admin.repository.BikeDetailsRepository;
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
 * REST controller for managing BikeDetails.
 */
@RestController
@RequestMapping("/api")
public class BikeDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BikeDetailsResource.class);

    @Inject
    private BikeDetailsRepository bikeDetailsRepository;

    /**
     * POST  /bikeDetailss -> Create a new bikeDetails.
     */
    @RequestMapping(value = "/bikeDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody BikeDetails bikeDetails) throws URISyntaxException {
        log.debug("REST request to save BikeDetails : {}", bikeDetails);
        if (bikeDetails.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bikeDetails cannot already have an ID").build();
        }
        bikeDetailsRepository.save(bikeDetails);
        return ResponseEntity.created(new URI("/api/bikeDetailss/" + bikeDetails.getId())).build();
    }

    /**
     * PUT  /bikeDetailss -> Updates an existing bikeDetails.
     */
    @RequestMapping(value = "/bikeDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody BikeDetails bikeDetails) throws URISyntaxException {
        log.debug("REST request to update BikeDetails : {}", bikeDetails);
        if (bikeDetails.getId() == null) {
            return create(bikeDetails);
        }
        bikeDetailsRepository.save(bikeDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bikeDetailss -> get all the bikeDetailss.
     */
    @RequestMapping(value = "/bikeDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BikeDetails> getAll() {
        log.debug("REST request to get all BikeDetailss");
        return bikeDetailsRepository.findAll();
    }

    /**
     * GET  /bikeDetailss/:id -> get the "id" bikeDetails.
     */
    @RequestMapping(value = "/bikeDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BikeDetails> get(@PathVariable String id) {
        log.debug("REST request to get BikeDetails : {}", id);
        return Optional.ofNullable(bikeDetailsRepository.findOne(id))
            .map(bikeDetails -> new ResponseEntity<>(
                bikeDetails,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bikeDetailss/:id -> delete the "id" bikeDetails.
     */
    @RequestMapping(value = "/bikeDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete BikeDetails : {}", id);
        bikeDetailsRepository.delete(id);
    }
}
