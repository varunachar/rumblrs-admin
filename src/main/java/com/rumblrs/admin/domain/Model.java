package com.rumblrs.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Model.
 */
@Document(collection = "MODEL")
public class Model implements Serializable {

	@Id
	private String id;

	@NotNull
	@Field("name")
	private String name;

	@NotNull
	@Field("engine_capacity")
	private Integer engineCapacity;

	@NotNull
	@Field("brand_id")
	private String brandId;

	@NotNull
	@Field("brand_name")
	private String brandName;

	/**
	 * Added to extract brandName from the submitted data
	 */
	private Brand brand;
	
	private Body body;

	@Field("body_id")
	private String bodyId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Brand getBrand() {
		if (brand == null) {
			Brand brand = new Brand();
			brand.setId(brandId);
			brand.setName(brandName);
			this.brand = brand;
		}
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
		this.brandId = brand.getId();
		this.brandName = brand.getName();
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
		this.bodyId = body.getId();
	}

	public String getBodyId() {
		return bodyId;
	}

	public void setBodyId(String bodyId) {
		this.bodyId = bodyId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Model model = (Model) o;

		if (!Objects.equals(id, model.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Model{" + "id=" + id + ", name='" + name + "'"
				+ ", engineCapacity='" + engineCapacity + "'" + ", brandId='"
				+ brandId + "'" + '}';
	}
}
