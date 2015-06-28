package com.rumblrs.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Bike.
 */
@Document(collection = "BIKE")
public class Bike implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("brand")
    private String brand;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("engine_capacity")
    private Integer engineCapacity;

    @NotNull
    @Field("year_of_manufacture")
    private Integer yearOfManufacture;

    @NotNull
    @Field("location")
    private String location;

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
    @Field("score")
    private BigDecimal score;

    @NotNull
    @Field("type")
    private Integer type;

    @Field("thumbnail")
    private String thumbnail;

    @Field("reserved")
    private Boolean reserved;

    @Field("sold")
    private Boolean sold;

    @NotNull
    @Field("bike_id")
    private String bikeId;

    @NotNull
    @Field("detail_id")
    private String detailId;

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

    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
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
                ", location='" + location + "'" +
                ", kms='" + kms + "'" +
                ", owners='" + owners + "'" +
                ", price='" + price + "'" +
                ", score='" + score + "'" +
                ", type='" + type + "'" +
                ", thumbnail='" + thumbnail + "'" +
                ", reserved='" + reserved + "'" +
                ", sold='" + sold + "'" +
                ", bikeId='" + bikeId + "'" +
                ", detailId='" + detailId + "'" +
                '}';
    }
}
