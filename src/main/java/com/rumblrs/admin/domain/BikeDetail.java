package com.rumblrs.admin.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsonorg.JSONObjectSerializer;

/**
 * A BikeDetails.
 */
@Document(collection = "BIKEDETAILS")
public class BikeDetail implements Serializable {

    @Id
    private String id;

    /**
     * User id
     */
    @Field("owner")
    private String owner;

    @Field("features")
    private String features;

    @Field("documents")
    private Documents documents;

    @JsonSerialize(using=JSONObjectSerializer.class)
    @Field("report")
    private JSONObject report;

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

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public JSONObject getReport() {
        return report;
    }

    public void setReport(Map<String, Object> report) {
    	this.report = new JSONObject(report);
    }
    
    /*public void setReport(JSONObject report) {
        this.report = report;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BikeDetail bikeDetails = (BikeDetail) o;

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
                ", report='" + report.toString() + "'" +
                '}';
    }
}
