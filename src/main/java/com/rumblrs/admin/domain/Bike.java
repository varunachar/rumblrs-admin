package com.rumblrs.admin.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Seller's Bike details. Brand, Model info are stored here for performance optimizations.
 */
@Document(collection = "BIKE")
public class Bike implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("brand")
    private String brand;

    /**
     * The model name
     */
    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("engine_capacity")
    private Integer engineCapacity;
    
    @NotNull
    @Field("model_id")
    private String modelId;
    
    @NotNull
    @Field("brand_id")
    private String brandId;
    
    @Transient
    private Model model;
    
    @NotNull
    @Field("year_of_manufacture")
    private Integer yearOfManufacture;

    @NotNull
    @Field("kms")
    private Integer kms;

    @NotNull
    @Field("owners")
    private Integer owners;

    @NotNull
    @Field("price")
    private Integer price;
    
    @NotNull
    @Field("purchasePrice")
    private Integer purchasePrice;

    @NotNull
    @Field("score")
    private BigDecimal score = new BigDecimal(0);

    @Field("thumbnail")
    private String thumbnail;
    
    @Field("pictures")
    private String[] pictures;

	@Field("reserved")
    private Boolean reserved;

    @Field("sold")
    private Boolean sold;

    @NotNull
    @Field("detail_id")
    private String detailId;
    
    /**
     * Added for performance improvement
     */
    @Field("location")
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return model name
	 */
	public String getName() {
        return name;
    }

	/**
	 * Set model name
	 * @param name
	 */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		this.brand = model.getBrandName();
		this.brandId = model.getBrandId();
		this.modelId = model.getId();
		this.engineCapacity = model.getEngineCapacity();
	}

	public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Integer getKms() {
        return kms;
    }

    public void setKms(Integer kms) {
        this.kms = kms;
    }

    public Integer getOwners() {
        return owners;
    }

    public void setOwners(Integer owners) {
        this.owners = owners;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public String[] getPictures() {
		return pictures;
	}

	public void setPictures(String[] pictures) {
		this.pictures = pictures;
	}

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bike bike = (Bike) o;

        if ( ! Objects.equals(id, bike.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", brand='" + brand + "'" +
                ", name='" + name + "'" +
                ", engineCapacity='" + engineCapacity + "'" +
                ", yearOfManufacture='" + yearOfManufacture + "'" +
                ", kms='" + kms + "'" +
                ", owners='" + owners + "'" +
                ", price='" + price + "'" +
                ", score='" + score + "'" +
                ", thumbnail='" + thumbnail + "'" +
                ", reserved='" + reserved + "'" +
                ", sold='" + sold + "'" +
                ", detailId='" + detailId + "'" +
                '}';
    }
}
