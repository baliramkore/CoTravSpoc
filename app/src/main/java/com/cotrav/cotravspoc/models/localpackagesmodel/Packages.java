package com.cotrav.cotravspoc.models.localpackagesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packages
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("city_id")
    @Expose
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
