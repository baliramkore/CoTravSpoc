package com.cotrav.cotravspoc.models.allcitiesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCityResponse {

    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("Cities")
    @Expose
    private List<City> cities = null;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
