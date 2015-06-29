package com.rumblrs.admin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rumblrs.admin.domain.Brand;
import com.rumblrs.admin.repository.BrandRepository;
import com.rumblrs.admin.web.rest.service.BrandService;

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
 * REST controller for managing Brand.
 */
@RestController
@RequestMapping("/api")
public class BrandResource {

    private final Logger log = LoggerFactory.getLogger(BrandResource.class);

    @Inject
    private BrandService brandService;

    /**
     * POST  /brands -> Create a new brand.
     */
    @RequestMapping(value = "/brands",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to save Brand : {}", brand);
        if (brand.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new brand cannot already have an ID").build();
        }
        brandService.save(brand);
        return ResponseEntity.created(new URI("/api/brands/" + brand.getId())).build();
    }

    /**
     * PUT  /brands -> Updates an existing brand.
     */
    @RequestMapping(value = "/brands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to update Brand : {}", brand);
        if (brand.getId() == null) {
            return create(brand);
        }
        brandService.save(brand);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /brands -> get all the brands.
     */
    @RequestMapping(value = "/brands",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Brand> getAll() {
        log.debug("REST request to get all Brands");
        return brandService.findAll();
    }

    /**
     * GET  /brands/:id -> get the "id" brand.
     */
    @RequestMapping(value = "/brands/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Brand> get(@PathVariable String id) {
        log.debug("REST request to get Brand : {}", id);
        return Optional.ofNullable(brandService.findOne(id))
            .map(brand -> new ResponseEntity<>(
                brand,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/brands/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Brand> search(@RequestParam String name) {
        log.debug("REST request to search Brands starting with : {}", name);
        return brandService.search(name);
    }

    /**
     * DELETE  /brands/:id -> delete the "id" brand.
     * @return 
     */
    @RequestMapping(value = "/brands/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        log.debug("REST request to delete Brand : {}", id);
        boolean delete = brandService.delete(id);
        if(!delete){
        	return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        else {
        	return new ResponseEntity<Boolean>(HttpStatus.OK);
        }
    }
}
