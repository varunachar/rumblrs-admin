/**
 * 
 */
package com.rumblrs.admin.web.rest.service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rumblrs.admin.domain.Bike;
import com.rumblrs.admin.domain.BikeDetail;
import com.rumblrs.admin.domain.BikeRequestWrapper;
import com.rumblrs.admin.repository.BikeDetailRepository;
import com.rumblrs.admin.repository.BikeRepository;
import com.rumblrs.admin.web.rest.util.PaginationUtil;

/**
 * @author Varun Achar
 *
 */
@Service
public class BikeService {

	@Inject
	private BikeRepository bikeRepository;

	@Inject
	private BikeDetailRepository bikeDetailRepository;

	@Transactional
	public Bike save(Bike bike, BikeDetail bikeDetails) {
		bikeDetails = bikeDetailRepository.save(bikeDetails);
		bike.setDetailId(bikeDetails.getId());
		bike = bikeRepository.save(bike);
		return bike;
	}

	@Transactional
	public void update(Bike bike, BikeDetail bikeDetails) {
		bikeDetailRepository.save(bikeDetails);
		bikeRepository.save(bike);
	}

	public Page<Bike> findAll(Integer offset, Integer limit) {
		return bikeRepository.findAll(PaginationUtil
				.generatePageRequest(offset, limit));
	}

	public BikeRequestWrapper findOne(String id) {
		Bike bike = bikeRepository.findOne(id);
		BikeDetail bikeDetail = bikeDetailRepository.findOne(bike.getDetailId());
		return new BikeRequestWrapper(bike, bikeDetail);
	}

	@Transactional
	public void delete(String id) {
		bikeDetailRepository.delete(bikeRepository.findOne(id).getDetailId());
		bikeRepository.delete(id);
	}
}
