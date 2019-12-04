package com.cotrav.cotravspoc.models.companytypemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Corporates")
    @Expose
    private List<Corporate> corporates = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Corporate> getCorporates() {
        return corporates;
    }

    public void setCorporates(List<Corporate> corporates) {
        this.corporates = corporates;
    }
}
