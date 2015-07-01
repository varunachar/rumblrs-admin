package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.Body;
import com.rumblrs.admin.repository.BodyRepository;
import com.rumblrs.admin.web.rest.service.BodyService;

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
    private BodyService bodyService;

    /**
     * POST  /bodies -> Create a new body.
     */
    @RequestMapping(value = "/bodies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Body body) throws URISyntaxException {
        log.debug("REST request to save Body : {}", body);
        if (body.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new body cannot already have an ID").build();
        }
        bodyService.save(body);
        return ResponseEntity.created(new URI("/api/bodies/" + body.getId())).build();
    }

    /**
     * PUT  /bodies -> Updates an existing body.
     */
    @RequestMapping(value = "/bodies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Body body) throws URISyntaxException {
        log.debug("REST request to update Body : {}", body);
        if (body.getId() == null) {
            return create(body);
        }
        bodyService.save(body);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bodies -> get all the bodies.
     */
    @RequestMapping(value = "/bodies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Body> getAll() {
        log.debug("REST request to get all Bodies");
        return bodyService.findAll();
    }

    /**
     * GET  /bodies/:id -> get the "id" body.
     */
    @RequestMapping(value = "/bodies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Body> get(@PathVariable String id) {
        log.debug("REST request to get Body : {}", id);
        return Optional.ofNullable(bodyService.findOne(id))
            .map(body -> new ResponseEntity<>(
                body,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bodies/:id -> delete the "id" body.
     * @return 
     */
    @RequestMapping(value = "/bodies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        log.debug("REST request to delete Body : {}", id);
        boolean deleted = bodyService.delete(id);
        if(deleted) {
        	return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }
    
    /**
     * DELETE /bodies/search?type=:type -> search for body of type
     * @param type
     * @return
     */
    @RequestMapping(value="/bodies/search",
    		method=RequestMethod.GET,
    		produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Body> search(@RequestParam String type){
    	return bodyService.search(type);
    }
}
