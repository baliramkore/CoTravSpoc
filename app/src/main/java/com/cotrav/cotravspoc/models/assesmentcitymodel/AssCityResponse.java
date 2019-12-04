package com.cotrav.cotravspoc.models.assesmentcitymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssCityResponse
{

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("Cities")
    @Expose
    public List<AssCity> cities = null;

}
