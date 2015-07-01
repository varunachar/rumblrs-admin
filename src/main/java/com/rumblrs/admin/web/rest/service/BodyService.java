package com.rumblrs.admin.web.rest.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.rumblrs.admin.domain.Body;
import com.rumblrs.admin.domain.Model;
import com.rumblrs.admin.repository.BodyRepository;
import com.rumblrs.admin.repository.ModelRepository;

@Service
public class BodyService {

	@Inject
	private BodyRepository bodyRepository;

	@Inject
	private ModelRepository modelRepository;

	public void save(Body body) {
		bodyRepository.save(body);
	}

	public List<Body> findAll() {
		return bodyRepository.findAll();
	}

	public Body findOne(String id) {
		return bodyRepository.findOne(id);
	}

	/**
	 * Checks to see if any {@link Model} exist with this body before attempting
	 * a delete
	 * 
	 * @param id
	 * @return <code>true</code> if delete is successful, <code>false</code>
	 *         otherwise
	 */
	public boolean delete(String id) {
		List<Model> models = modelRepository.findByBodyId(id);
		if (CollectionUtils.isEmpty(models)) {
			bodyRepository.delete(id);
			return true;
		}
		return false;
	}

	public List<Body> search(String type) {
		return bodyRepository.findByTypeLikeIgnoreCase(type);
	}
}
