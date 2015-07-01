/**
 * 
 */
package com.rumblrs.admin.web.rest.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.rumblrs.admin.domain.Brand;
import com.rumblrs.admin.domain.Model;
import com.rumblrs.admin.repository.BrandRepository;
import com.rumblrs.admin.repository.ModelRepository;

/**
 * @author Varun Achar
 *
 */
@Service
public class BrandService {

	@Inject
	private BrandRepository brandRepository;

	@Inject
	private ModelRepository modelRepository;

	public void save(Brand brand) {
		brandRepository.save(brand);
	}

	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	public Brand findOne(String id) {
		return brandRepository.findOne(id);
	}

	public List<Brand> search(String name) {
		return brandRepository.findByNameLikeIgnoreCase(name);
	}

	/**
	 * Checks to see if any models exist with this brand before attempting to
	 * delete
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		List<Model> models = modelRepository.findByBrandId(id);
		if (CollectionUtils.isEmpty(models)) {
			brandRepository.delete(id);
			return true;
		}
		return false;
	}

}
