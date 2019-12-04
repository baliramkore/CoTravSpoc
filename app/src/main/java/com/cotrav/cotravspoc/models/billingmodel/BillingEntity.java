package com.cotrav.cotravspoc.models.billingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingEntity
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("corporate_id")
    @Expose
    private int corporateId;
    @SerializedName("entity_name")
    @Expose
    private String entityName;
    @SerializedName("billing_city_id")
    @Expose
    private int billingCityId;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_email")
    @Expose
    private String contactPersonEmail;
    @SerializedName("contact_person_no")
    @Expose
    private String contactPersonNo;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line_2")
    @Expose
    private String addressLine2;
    @SerializedName("address_line_3")
    @Expose
    private String addressLine3;
    @SerializedName("gst_id")
    @Expose
    private String gstId;
    @SerializedName("pan_no")
    @Expose
    private Object panNo;
    @SerializedName("is_deleted")
    @Expose
    private int isDeleted;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("corporate_name")
    @Expose
    private String corporateName;
    @SerializedName("billing_city")
    @Expose
    private Object billingCity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getBillingCityId() {
        return billingCityId;
    }

    public void setBillingCityId(int billingCityId) {
        this.billingCityId = billingCityId;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonNo() {
        return contactPersonNo;
    }

    public void setContactPersonNo(String contactPersonNo) {
        this.contactPersonNo = contactPersonNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public Object getPanNo() {
        return panNo;
    }

    public void setPanNo(Object panNo) {
        this.panNo = panNo;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
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

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public Object getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(Object billingCity) {
        this.billingCity = billingCity;
    }
}