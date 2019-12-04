package com.cotrav.cotravspoc.models.login_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginApiResponse {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("User")
        @Expose
        private List<User> user = null;
        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("OTP")
        @Expose
        private String oTP;

    public String getoTP() {
        return oTP;
    }

    public void setoTP(String oTP) {
        this.oTP = oTP;
    }

    public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public List<User> getUser() {
            return user;
        }

        public void setUser(List<User> user) {
            this.user = user;
        }

        public String getMessage() {
        return message;
    }
        public void setMessage(String message) {
        this.message = message;
    }



}