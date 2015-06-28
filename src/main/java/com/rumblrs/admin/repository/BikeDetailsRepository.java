package com.rumblrs.admin.repository;

import com.rumblrs.admin.domain.BikeDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the BikeDetails entity.
 */
public interface BikeDetailsRepository extends MongoRepository<BikeDetails,String> {

}
