package com.cotrav.cotravspoc.models.trainstationmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainStationApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Stations")
    @Expose
    private List<TrainStation> stations = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TrainStation> getStations() {
        return stations;
    }

    public void setStations(List<TrainStation> stations) {
        this.stations = stations;
    }
}
