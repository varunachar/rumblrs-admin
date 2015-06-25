package com.rumblrs.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BikeDetails.
 */
@Document(collection = "BIKEDETAILS")
public class BikeDetails implements Serializable {

    @Id
    private String id;

    @Field("owner")
    private String owner;

    @Field("features")
    private String features;

    @Field("documents")
    private String documents;

    @Field("report")
    private String report;

    @Field("performance")
    private String performance;

    @NotNull
    @Field("detail_id")
    private Integer detailId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
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

        BikeDetails bikeDetails = (BikeDetails) o;

        if ( ! Objects.equals(id, bikeDetails.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BikeDetails{" +
                "id=" + id +
                ", owner='" + owner + "'" +
                ", features='" + features + "'" +
                ", documents='" + documents + "'" +
                ", report='" + report + "'" +
                ", performance='" + performance + "'" +
                ", detailId='" + detailId + "'" +
                '}';
    }
}