package com.cotrav.cotravspoc.models.dashboardmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardApiResponse
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Dashboard")
    @Expose
    private List<DashBoardData> dashboard = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DashBoardData> getDashboard() {
        return dashboard;
    }

    public void setDashboard(List<DashBoardData> dashboard) {
        this.dashboard = dashboard;
    }

}
