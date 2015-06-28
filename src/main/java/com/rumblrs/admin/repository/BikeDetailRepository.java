package com.rumblrs.admin.repository;

import com.rumblrs.admin.domain.BikeDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the BikeDetails entity.
 */
public interface BikeDetailRepository extends MongoRepository<BikeDetail,String> {

}
