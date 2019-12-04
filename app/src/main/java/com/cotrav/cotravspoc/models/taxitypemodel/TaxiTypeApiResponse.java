package com.cotrav.cotravspoc.models.taxitypemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiTypeApiResponse
{
    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("taxi_types")
    @Expose
    private List<TaxiType> taxiTypes = null;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<TaxiType> getTaxiTypes() {
        return taxiTypes;
    }

    public void setTaxiTypes(List<TaxiType> taxiTypes) {
        this.taxiTypes = taxiTypes;
    }

}
