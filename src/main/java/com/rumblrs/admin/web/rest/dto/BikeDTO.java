/**
 * 
 */
package com.rumblrs.admin.web.rest.dto;

import javax.validation.constraints.NotNull;

import com.rumblrs.admin.domain.Bike;
import com.rumblrs.admin.domain.BikeDetail;

/**
 * A request wrapper purely defined to allow {@link Bike} and
 * {@link BikeDetail} & Owner information to be passed in the same request
 * 
 * @author Varun Achar
 *
 */
public class BikeDTO {

	@NotNull
	private Bike bike;
	
	@NotNull
	private BikeDetail bikeDetail;
	
	@NotNull
	private UserDTO owner;
	

	public BikeDTO() {
		
	}
	
	public BikeDTO(Bike bike, BikeDetail bikeDetail, UserDTO owner) {
		this.bike = bike;
		this.bikeDetail = bikeDetail;
		this.owner = owner;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	public BikeDetail getBikeDetail() {
		return bikeDetail;
	}

	public void setBikeDetail(BikeDetail bikeDetails) {
		this.bikeDetail = bikeDetails;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO user) {
		this.owner = user;
	}

}
