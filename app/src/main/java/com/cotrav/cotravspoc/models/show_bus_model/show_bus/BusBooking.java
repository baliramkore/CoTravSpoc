package com.cotrav.cotravspoc.models.show_bus_model.show_bus;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "busbooking_table")
public class BusBooking  {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("vendor_booking_id")
    @Expose
    private Object vendorBookingId;
    @SerializedName("voucher_no")
    @Expose
    private Object voucherNo;
    @SerializedName("booking_email")
    @Expose
    private Object bookingEmail;
    @SerializedName("drop_location")
    @Expose
    private String dropLocation;
    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;
    @SerializedName("booking_datetime")
    @Expose
    private String bookingDatetime;
    @SerializedName("pickup_from_datetime")
    @Expose
    private String pickupFromDatetime;
    @SerializedName("pickup_to_datetime")
    @Expose
    private String pickupToDatetime;
    @SerializedName("preferred_bus")
    @Expose
    private String preferredBus;
    @SerializedName("boarding_point")
    @Expose
    private Object boardingPoint;
    @SerializedName("boarding_datetime")
    @Expose
    private Object boardingDatetime;
    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;
    @SerializedName("assessment_city_id")
    @Expose
    private String assessmentCityId;
    @SerializedName("bus_type_priority_1")
    @Expose
    private String busTypePriority1;
    @SerializedName("bus_type_priority_2")
    @Expose
    private String busTypePriority2;
    @SerializedName("bus_type_priority_3")
    @Expose
    private String busTypePriority3;
    @SerializedName("no_of_seats")
    @Expose
    private String noOfSeats;
    @SerializedName("last_action_by")
    @Expose
    private String lastActionBy;
    @SerializedName("operator_name")
    @Expose
    private Object operatorName;
    @SerializedName("operator_contact")
    @Expose
    private Object operatorContact;
    @SerializedName("ticket_no")
    @Expose
    private Object ticketNo;
    @SerializedName("pnr_no")
    @Expose
    private Object pnrNo;
    @SerializedName("assign_bus_type_id")
    @Expose
    private Object assignBusTypeId;
    @SerializedName("seat_no")
    @Expose
    private Object seatNo;
    @SerializedName("portal_used")
    @Expose
    private Object portalUsed;
    @SerializedName("status_client")
    @Expose
    private String statusClient;
    @SerializedName("status_cotrav")
    @Expose
    private int statusCotrav;
    @SerializedName("is_invoice")
    @Expose
    private String isInvoice;
    @SerializedName("invoice_id")
    @Expose
    private Object invoiceId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("subgroup_id")
    @Expose
    private String subgroupId;
    @SerializedName("spoc_id")
    @Expose
    private String spocId;
    @SerializedName("corporate_id")
    @Expose
    private String corporateId;
    @SerializedName("billing_entity_id")
    @Expose
    private String billingEntityId;
    @SerializedName("reason_booking")
    @Expose
    private String reasonBooking;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
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
    @SerializedName("cotrav_agent_name")
    @Expose
    private String cotravAgentName;
    @SerializedName("Passangers")
    @Expose
    private List<ShowBusPassanger> passangers = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Object getVendorBookingId() {
        return vendorBookingId;
    }

    public void setVendorBookingId(Object vendorBookingId) {
        this.vendorBookingId = vendorBookingId;
    }

    public Object getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Object voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Object getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(Object bookingEmail) {
        this.bookingEmail = bookingEmail;
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

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public String getPickupFromDatetime() {
        return pickupFromDatetime;
    }

    public void setPickupFromDatetime(String pickupFromDatetime) {
        this.pickupFromDatetime = pickupFromDatetime;
    }

    public String getPickupToDatetime() {
        return pickupToDatetime;
    }

    public void setPickupToDatetime(String pickupToDatetime) {
        this.pickupToDatetime = pickupToDatetime;
    }

    public String getPreferredBus() {
        return preferredBus;
    }

    public void setPreferredBus(String preferredBus) {
        this.preferredBus = preferredBus;
    }

    public Object getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(Object boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public Object getBoardingDatetime() {
        return boardingDatetime;
    }

    public void setBoardingDatetime(Object boardingDatetime) {
        this.boardingDatetime = boardingDatetime;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public String getAssessmentCityId() {
        return assessmentCityId;
    }

    public void setAssessmentCityId(String assessmentCityId) {
        this.assessmentCityId = assessmentCityId;
    }

    public String getBusTypePriority1() {
        return busTypePriority1;
    }

    public void setBusTypePriority1(String busTypePriority1) {
        this.busTypePriority1 = busTypePriority1;
    }

    public String getBusTypePriority2() {
        return busTypePriority2;
    }

    public void setBusTypePriority2(String busTypePriority2) {
        this.busTypePriority2 = busTypePriority2;
    }

    public String getBusTypePriority3() {
        return busTypePriority3;
    }

    public void setBusTypePriority3(String busTypePriority3) {
        this.busTypePriority3 = busTypePriority3;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public Object getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(Object operatorName) {
        this.operatorName = operatorName;
    }

    public Object getOperatorContact() {
        return operatorContact;
    }

    public void setOperatorContact(Object operatorContact) {
        this.operatorContact = operatorContact;
    }

    public Object getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(Object ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Object getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(Object pnrNo) {
        this.pnrNo = pnrNo;
    }

    public Object getAssignBusTypeId() {
        return assignBusTypeId;
    }

    public void setAssignBusTypeId(Object assignBusTypeId) {
        this.assignBusTypeId = assignBusTypeId;
    }

    public Object getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Object seatNo) {
        this.seatNo = seatNo;
    }

    public Object getPortalUsed() {
        return portalUsed;
    }

    public void setPortalUsed(Object portalUsed) {
        this.portalUsed = portalUsed;
    }

    public String getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(String statusClient) {
        this.statusClient = statusClient;
    }

    public int getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(int statusCotrav) {
        this.statusCotrav = statusCotrav;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Object getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Object invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(String subgroupId) {
        this.subgroupId = subgroupId;
    }

    public String getSpocId() {
        return spocId;
    }

    public void setSpocId(String spocId) {
        this.spocId = spocId;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getBillingEntityId() {
        return billingEntityId;
    }

    public void setBillingEntityId(String billingEntityId) {
        this.billingEntityId = billingEntityId;
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

    public String getCotravAgentName() {
        return cotravAgentName;
    }

    public void setCotravAgentName(String cotravAgentName) {
        this.cotravAgentName = cotravAgentName;
    }

    public List<ShowBusPassanger> getPassangers() {
        return passangers;
    }

    public void setPassangers(List<ShowBusPassanger> passangers) {
        this.passangers = passangers;
    }

}