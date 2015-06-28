package com.rumblrs.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rumblrs.admin.domain.Bike;

/**
 * Spring Data MongoDB repository for the Bike entity.
 */
public interface BikeRepository extends MongoRepository<Bike,String> {

}
