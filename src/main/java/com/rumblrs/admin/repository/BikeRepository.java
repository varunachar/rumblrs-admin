package com.rumblrs.admin.repository;

import com.rumblrs.admin.domain.Bike;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Bike entity.
 */
public interface BikeRepository extends MongoRepository<Bike,String> {

}
