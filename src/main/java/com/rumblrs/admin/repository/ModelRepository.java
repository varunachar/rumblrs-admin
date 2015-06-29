package com.rumblrs.admin.repository;

import com.rumblrs.admin.domain.Model;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Model entity.
 */
public interface ModelRepository extends MongoRepository<Model,String> {

}
