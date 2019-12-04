package com.cotrav.cotravspoc.models.assesmentcodemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sandeep on 17/7/17.
 */

public class AssessmentResponse {
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("AssCodes")
    @Expose
    public List<AssCode> assCodes = null;
}
