/**
 * 
 */
package com.rumblrs.admin.domain;

/**
 * A request wrapper purely defined to allow {@link Bike} and
 * {@link BikeDetail} to be saved in the same request
 * 
 * @author Varun Achar
 *
 */
public class BikeRequestWrapper {

	private Bike bike;
	private BikeDetail bikeDetail;
	
	public BikeRequestWrapper() {
		
	}

	public BikeRequestWrapper(Bike bike, BikeDetail bikeDetail) {
		this.bike = bike;
		this.bikeDetail = bikeDetail;
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

	public void setBikeDetails(BikeDetail bikeDetails) {
		this.bikeDetail = bikeDetails;
	}

}
