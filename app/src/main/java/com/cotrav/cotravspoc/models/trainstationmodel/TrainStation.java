package com.cotrav.cotravspoc.models.trainstationmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainStation
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("station_name")
    @Expose
    private String stationName;
    @SerializedName("station_code")
    @Expose
    private String stationCode;
    @SerializedName("trains_pass_by")
    @Expose
    private Integer trainsPassBy;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Integer getTrainsPassBy() {
        return trainsPassBy;
    }

    public void setTrainsPassBy(Integer trainsPassBy) {
        this.trainsPassBy = trainsPassBy;
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
