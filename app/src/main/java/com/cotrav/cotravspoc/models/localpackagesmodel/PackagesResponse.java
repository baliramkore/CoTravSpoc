package com.cotrav.cotravspoc.models.localpackagesmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesResponse
{
    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("Package")
    @Expose
    private List<Packages> _package = null;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<Packages> getPackage() {
        return _package;
    }

    public void setPackage(List<Packages> _package) {
        this._package = _package;
    }
}
