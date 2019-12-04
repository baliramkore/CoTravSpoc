package com.cotrav.cotravspoc.models.view_spoc_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewSpocApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Spoc")
    @Expose
    private List<ViewSpoc> spoc = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ViewSpoc> getSpoc() {
        return spoc;
    }

    public void setSpoc(List<ViewSpoc> spoc) {
        this.spoc = spoc;
    }
}
