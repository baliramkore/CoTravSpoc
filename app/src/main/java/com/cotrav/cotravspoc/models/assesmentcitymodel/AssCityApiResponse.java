package com.cotrav.cotravspoc.models.assesmentcitymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssCityApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("AssCity")
    @Expose
    private List<AssCity> assCity = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<AssCity> getAssCity() {
        return assCity;
    }

    public void setAssCity(List<AssCity> assCity) {
        this.assCity = assCity;
    }
}
