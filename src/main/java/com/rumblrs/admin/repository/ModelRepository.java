package com.rumblrs.admin.repository;

import java.util.List;

import com.rumblrs.admin.domain.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Model entity.
 */
public interface ModelRepository extends MongoRepository<Model,String> {

	List<Model> findByBrandId(String id);
	
	List<Model> findByBodyId(String id);

	List<Model> findByNameLikeIgnoreCase(String name);

}
