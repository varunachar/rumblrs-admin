package com.rumblrs.admin.repository;

import com.rumblrs.admin.domain.Body;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Body entity.
 */
public interface BodyRepository extends MongoRepository<Body,String> {

}
