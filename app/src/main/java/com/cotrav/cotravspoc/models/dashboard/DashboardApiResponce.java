package com.cotrav.cotravspoc.models.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardApiResponce {


        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Dashboard")
        @Expose
        private List<Dashboard> dashboard = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Dashboard> getDashboard() {
            return dashboard;
        }

        public void setDashboard(List<Dashboard> dashboard) {
            this.dashboard = dashboard;
        }

    }