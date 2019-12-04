package com.cotrav.cotravspoc.models.assesmentcodemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sandeep on 17/7/17.
 */

public class AssessmentCodeApiResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("AssCodes")
    @Expose
    private List<AssCode> assCodes = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<AssCode> getAssCodes() {
        return assCodes;
    }

    public void setAssCodes(List<AssCode> assCodes) {
        this.assCodes = assCodes;
    }

}
