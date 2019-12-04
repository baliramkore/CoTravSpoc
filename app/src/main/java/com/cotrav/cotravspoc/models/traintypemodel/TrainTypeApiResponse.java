package com.cotrav.cotravspoc.models.traintypemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainTypeApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Types")
    @Expose
    private List<TrainType> types = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<TrainType> getTypes() {
        return types;
    }

    public void setTypes(List<TrainType> types) {
        this.types = types;
    }
}
