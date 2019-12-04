package com.cotrav.cotravspoc.models.assesmentcitymodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssCity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("corporate_id")
    @Expose
    private Integer corporateId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(Integer corporateId) {
        this.corporateId = corporateId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
