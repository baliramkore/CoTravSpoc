package com.cotrav.cotravspoc.local_database.local_model.taxi;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TaxiBooking implements Serializable
{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("vendor_booking_id")
    @Expose
    private String vendorBookingId;
    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;
    @SerializedName("booking_email")
    @Expose
    private String bookingEmail;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("taxi_id")
    @Expose
    private String taxiId;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("tour_type")
    @Expose
    private String tourType;
    @SerializedName("city_id")
    @Expose
    private int cityId;
    @SerializedName("drop_location")
    @Expose
    private String dropLocation;
    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;
    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("pickup_datetime")

    @Expose
    private String pickupDatetime;
    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;
    @SerializedName("assessment_city_id")
    @Expose
    private int assessmentCityId;
    @SerializedName("no_of_seats")
    @Expose
    private int noOfSeats;
    @SerializedName("status_client")
    @Expose
    private int statusClient;
    @SerializedName("status_cotrav")
    @Expose
    private int statusCotrav;
    @SerializedName("last_action_by")
    @Expose
    private int lastActionBy;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("rate_id")
    @Expose
    private String rateId;
    @SerializedName("is_invoice")
    @Expose
    private int isInvoice;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("group_id")
    @Expose
    private int groupId;
    @SerializedName("subgroup_id")
    @Expose
    private int subgroupId;
    @SerializedName("spoc_id")
    @Expose
    private int spocId;
    @SerializedName("corporate_id")
    @Expose
    private int corporateId;
    @SerializedName("billing_entity_id")
    @Expose
    private int billingEntityId;
    @SerializedName("ass_code")
    @Expose
    private String assCode;
    @SerializedName("reason_booking")
    @Expose
    private String reasonBooking;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("taxi_type_name")
    @Expose
    private String taxiTypeName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("client_status")
    @Expose
    private String clientStatus;
    @SerializedName("cotrav_status")
    @Expose
    private String cotravStatus;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_contact")
    @Expose
    private String userContact;
    @SerializedName("operator_name")
    @Expose
    private String operatorName;
    @SerializedName("operator_contact")
    @Expose
    private String operatorContact;
    @SerializedName("cotrav_agent_name")
    @Expose
    private String cotravAgentName;



/*
    @TypeConverters(TaxiPassengerConverter.class)
    private TaxiPassenger[] taxiPassenger;

    public TaxiPassenger[] getTaxiPassenger()
    {
        return taxiPassenger;
    }

    public void setTaxiPassenger(TaxiPassenger[] taxiPassenger)
    {
        this.taxiPassenger = taxiPassenger;
    }
*/

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getVendorBookingId() {
        return vendorBookingId;
    }

    public void setVendorBookingId(String vendorBookingId) {
        this.vendorBookingId = vendorBookingId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPickupDatetime() {
        return pickupDatetime;
    }

    public void setPickupDatetime(String pickupDatetime) {
        this.pickupDatetime = pickupDatetime;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public int getAssessmentCityId() {
        return assessmentCityId;
    }

    public void setAssessmentCityId(int assessmentCityId) {
        this.assessmentCityId = assessmentCityId;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(int statusClient) {
        this.statusClient = statusClient;
    }

    public int getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(int statusCotrav) {
        this.statusCotrav = statusCotrav;
    }

    public int getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(int lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public int getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(int isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(int subgroupId) {
        this.subgroupId = subgroupId;
    }

    public int getSpocId() {
        return spocId;
    }

    public void setSpocId(int spocId) {
        this.spocId = spocId;
    }

    public int getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(int corporateId) {
        this.corporateId = corporateId;
    }

    public int getBillingEntityId() {
        return billingEntityId;
    }

    public void setBillingEntityId(int billingEntityId) {
        this.billingEntityId = billingEntityId;
    }

    public String getAssCode() {
        return assCode;
    }

    public void setAssCode(String assCode) {
        this.assCode = assCode;
    }

    public String getReasonBooking() {
        return reasonBooking;
    }

    public void setReasonBooking(String reasonBooking) {
        this.reasonBooking = reasonBooking;
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

    public String getTaxiTypeName() {
        return taxiTypeName;
    }

    public void setTaxiTypeName(String taxiTypeName) {
        this.taxiTypeName = taxiTypeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getCotravStatus() {
        return cotravStatus;
    }

    public void setCotravStatus(String cotravStatus) {
        this.cotravStatus = cotravStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorContact() {
        return operatorContact;
    }

    public void setOperatorContact(String operatorContact) {
        this.operatorContact = operatorContact;
    }

    public String getCotravAgentName() {
        return cotravAgentName;
    }

    public void setCotravAgentName(String cotravAgentName) {
        this.cotravAgentName = cotravAgentName;
    }

}
