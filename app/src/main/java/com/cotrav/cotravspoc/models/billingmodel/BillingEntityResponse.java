package com.cotrav.cotravspoc.models.billingmodel;

import com.cotrav.cotravspoc.models.billingmodel.BillingEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillingEntityResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Entitys")
    @Expose
    private List<BillingEntity> entitys = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<BillingEntity> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<BillingEntity> entitys) {
        this.entitys = entitys;
    }
}
