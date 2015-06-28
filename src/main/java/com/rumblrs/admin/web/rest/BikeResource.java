package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.Bike;
import com.rumblrs.admin.repository.BikeRepository;
import com.rumblrs.admin.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing Bike.
 */
@RestController
@RequestMapping("/api")
public class BikeResource {

    private final Logger log = LoggerFactory.getLogger(BikeResource.class);

    @Inject
    private BikeRepository bikeRepository;

    /**
     * POST  /bikes -> Create a new bike.
     */
    @RequestMapping(value = "/bikes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Bike bike) throws URISyntaxException {
        log.debug("REST request to save Bike : {}", bike);
        if (bike.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bike cannot already have an ID").build();
        }
        bikeRepository.save(bike);
        return ResponseEntity.created(new URI("/api/bikes/" + bike.getId())).build();
    }

    /**
     * PUT  /bikes -> Updates an existing bike.
     */
    @RequestMapping(value = "/bikes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Bike bike) throws URISyntaxException {
        log.debug("REST request to update Bike : {}", bike);
        if (bike.getId() == null) {
            return create(bike);
        }
        bikeRepository.save(bike);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bikes -> get all the bikes.
     */
    @RequestMapping(value = "/bikes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bike>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Bike> page = bikeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bikes", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bikes/:id -> get the "id" bike.
     */
    @RequestMapping(value = "/bikes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bike> get(@PathVariable String id) {
        log.debug("REST request to get Bike : {}", id);
        return Optional.ofNullable(bikeRepository.findOne(id))
            .map(bike -> new ResponseEntity<>(
                bike,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bikes/:id -> delete the "id" bike.
     */
    @RequestMapping(value = "/bikes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Bike : {}", id);
        bikeRepository.delete(id);
    }
}
