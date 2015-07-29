/**
 * 
 */
package com.rumblrs.admin.web.rest.service;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rumblrs.admin.domain.Bike;
import com.rumblrs.admin.domain.BikeDetail;
import com.rumblrs.admin.domain.User;
import com.rumblrs.admin.repository.BikeDetailRepository;
import com.rumblrs.admin.repository.BikeRepository;
import com.rumblrs.admin.repository.ModelRepository;
import com.rumblrs.admin.repository.UserRepository;
import com.rumblrs.admin.service.DuplicateUserException;
import com.rumblrs.admin.service.UserService;
import com.rumblrs.admin.web.rest.dto.BikeDTO;
import com.rumblrs.admin.web.rest.dto.UserDTO;
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

	@Inject
	private UserService userService;

	@Inject
	private UserRepository userRepository;
	
	@Inject
	private ModelRepository modelRepository;

	@Transactional
	public Bike save(Bike bike, BikeDetail bikeDetails, UserDTO user) {
		User seller;
		try {
			seller = userService.createCustomer(user);
		} catch (DuplicateUserException e) {
			seller = userService.verifyUserExists(null, user.getMobileNo(), user.getEmail()).get();
		}
		bikeDetails.setOwner(seller.getLogin());
		bike.setLocation(seller.getLandmark());
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
		return bikeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
	}

	public BikeDTO findOne(String id) {
		Bike bike = bikeRepository.findOne(id);
		bike.setModel(modelRepository.findOne(bike.getModelId()));
		BikeDetail bikeDetail = bikeDetailRepository.findOne(bike.getDetailId());
		Optional<User> user = userRepository.findOneByLogin(bikeDetail.getOwner());
		UserDTO userDTO = user.map((owner) -> {
			UserDTO convertedUser = new UserDTO(owner.getLogin(), null, owner.getFirstName(), owner.getLastName(),
				owner.getEmail(), owner.getMobileNo(), owner.getLangKey(), null);
			convertedUser.setAddress(owner.getAddress());
			convertedUser.setLandmark(owner.getLandmark());
			return convertedUser;
			}).get();
		BikeDTO bikeDTO = new BikeDTO(bike, bikeDetail, userDTO);
		return bikeDTO;
	}

	@Transactional
	public void delete(String id) {
		bikeDetailRepository.delete(bikeRepository.findOne(id).getDetailId());
		bikeRepository.delete(id);
	}
}
