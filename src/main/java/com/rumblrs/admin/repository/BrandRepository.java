package com.rumblrs.admin.repository;

import java.util.List;

import com.rumblrs.admin.domain.Brand;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Brand entity.
 */
public interface BrandRepository extends MongoRepository<Brand,String> {

	List<Brand> findByNameLike(String name);
}
