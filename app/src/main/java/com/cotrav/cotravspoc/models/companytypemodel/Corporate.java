package com.cotrav.cotravspoc.models.companytypemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Corporate
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("corporate_name")
    @Expose
    private String corporateName;
    @SerializedName("corporate_code")
    @Expose
    private String corporateCode;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_no")
    @Expose
    private String contactPersonNo;
    @SerializedName("contact_person_email")
    @Expose
    private String contactPersonEmail;
    @SerializedName("has_auth_level")
    @Expose
    private Integer hasAuthLevel;
    @SerializedName("no_of_auth_level")
    @Expose
    private Integer noOfAuthLevel;
    @SerializedName("has_assessment_codes")
    @Expose
    private Integer hasAssessmentCodes;
    @SerializedName("is_radio")
    @Expose
    private Integer isRadio;
    @SerializedName("is_local")
    @Expose
    private Integer isLocal;
    @SerializedName("is_outstation")
    @Expose
    private Integer isOutstation;
    @SerializedName("is_bus")
    @Expose
    private Integer isBus;
    @SerializedName("is_train")
    @Expose
    private Integer isTrain;
    @SerializedName("is_hotel")
    @Expose
    private Integer isHotel;
    @SerializedName("is_meal")
    @Expose
    private Integer isMeal;
    @SerializedName("is_flight")
    @Expose
    private Integer isFlight;
    @SerializedName("is_water_bottles")
    @Expose
    private Integer isWaterBottles;
    @SerializedName("is_reverse_logistics")
    @Expose
    private Integer isReverseLogistics;
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

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporateCode() {
        return corporateCode;
    }

    public void setCorporateCode(String corporateCode) {
        this.corporateCode = corporateCode;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonNo() {
        return contactPersonNo;
    }

    public void setContactPersonNo(String contactPersonNo) {
        this.contactPersonNo = contactPersonNo;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public Integer getHasAuthLevel() {
        return hasAuthLevel;
    }

    public void setHasAuthLevel(Integer hasAuthLevel) {
        this.hasAuthLevel = hasAuthLevel;
    }

    public Integer getNoOfAuthLevel() {
        return noOfAuthLevel;
    }

    public void setNoOfAuthLevel(Integer noOfAuthLevel) {
        this.noOfAuthLevel = noOfAuthLevel;
    }

    public Integer getHasAssessmentCodes() {
        return hasAssessmentCodes;
    }

    public void setHasAssessmentCodes(Integer hasAssessmentCodes) {
        this.hasAssessmentCodes = hasAssessmentCodes;
    }

    public Integer getIsRadio() {
        return isRadio;
    }

    public void setIsRadio(Integer isRadio) {
        this.isRadio = isRadio;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
        this.isLocal = isLocal;
    }

    public Integer getIsOutstation() {
        return isOutstation;
    }

    public void setIsOutstation(Integer isOutstation) {
        this.isOutstation = isOutstation;
    }

    public Integer getIsBus() {
        return isBus;
    }

    public void setIsBus(Integer isBus) {
        this.isBus = isBus;
    }

    public Integer getIsTrain() {
        return isTrain;
    }

    public void setIsTrain(Integer isTrain) {
        this.isTrain = isTrain;
    }

    public Integer getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(Integer isHotel) {
        this.isHotel = isHotel;
    }

    public Integer getIsMeal() {
        return isMeal;
    }

    public void setIsMeal(Integer isMeal) {
        this.isMeal = isMeal;
    }

    public Integer getIsFlight() {
        return isFlight;
    }

    public void setIsFlight(Integer isFlight) {
        this.isFlight = isFlight;
    }

    public Integer getIsWaterBottles() {
        return isWaterBottles;
    }

    public void setIsWaterBottles(Integer isWaterBottles) {
        this.isWaterBottles = isWaterBottles;
    }

    public Integer getIsReverseLogistics() {
        return isReverseLogistics;
    }

    public void setIsReverseLogistics(Integer isReverseLogistics) {
        this.isReverseLogistics = isReverseLogistics;
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
