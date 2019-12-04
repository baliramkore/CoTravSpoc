package com.cotrav.cotravspoc.models.show_flight_model.show_flight;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightBooking {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("usage_type")
    @Expose
    private String usageType;
    @SerializedName("journey_type")
    @Expose
    private String journeyType;
    @SerializedName("flight_class")
    @Expose
    private String flightClass;
    @SerializedName("from_location")
    @Expose
    private String fromLocation;
    @SerializedName("to_location")
    @Expose
    private String toLocation;
    @SerializedName("booking_datetime")
    @Expose
    private String bookingDatetime;
    @SerializedName("departure_datetime")
    @Expose
    private String departureDatetime;
    @SerializedName("preferred_flight")
    @Expose
    private String preferredFlight;
    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;
    @SerializedName("no_of_seats")
    @Expose
    private Integer noOfSeats;
    @SerializedName("last_action_by")
    @Expose
    private Integer lastActionBy;
    @SerializedName("flight_type")
    @Expose
    private Object flightType;
    @SerializedName("seat_type")
    @Expose
    private Object seatType;
    @SerializedName("trip_type")
    @Expose
    private Object tripType;
    @SerializedName("fare_type")
    @Expose
    private Object fareType;
    @SerializedName("meal_is_include")
    @Expose
    private Integer mealIsInclude;
    @SerializedName("no_of_stops")
    @Expose
    private Object noOfStops;
    @SerializedName("ticket_no")
    @Expose
    private Object ticketNo;
    @SerializedName("status_client")
    @Expose
    private Integer statusClient;
    @SerializedName("status_cotrav")
    @Expose
    private Integer statusCotrav;
    @SerializedName("is_invoice")
    @Expose
    private Integer isInvoice;
    @SerializedName("invoice_id")
    @Expose
    private Object invoiceId;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("subgroup_id")
    @Expose
    private Integer subgroupId;
    @SerializedName("spoc_id")
    @Expose
    private Integer spocId;
    @SerializedName("corporate_id")
    @Expose
    private Integer corporateId;
    @SerializedName("billing_entity_id")
    @Expose
    private Integer billingEntityId;
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
    private Object cotravAgentName;
    @SerializedName("Passangers")
    @Expose
    private List<FlightPassanger> passangers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public String getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(String departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public String getPreferredFlight() {
        return preferredFlight;
    }

    public void setPreferredFlight(String preferredFlight) {
        this.preferredFlight = preferredFlight;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(Integer lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public Object getFlightType() {
        return flightType;
    }

    public void setFlightType(Object flightType) {
        this.flightType = flightType;
    }

    public Object getSeatType() {
        return seatType;
    }

    public void setSeatType(Object seatType) {
        this.seatType = seatType;
    }

    public Object getTripType() {
        return tripType;
    }

    public void setTripType(Object tripType) {
        this.tripType = tripType;
    }

    public Object getFareType() {
        return fareType;
    }

    public void setFareType(Object fareType) {
        this.fareType = fareType;
    }

    public Integer getMealIsInclude() {
        return mealIsInclude;
    }

    public void setMealIsInclude(Integer mealIsInclude) {
        this.mealIsInclude = mealIsInclude;
    }

    public Object getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(Object noOfStops) {
        this.noOfStops = noOfStops;
    }

    public Object getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(Object ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(Integer statusClient) {
        this.statusClient = statusClient;
    }

    public Integer getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(Integer statusCotrav) {
        this.statusCotrav = statusCotrav;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Object getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Object invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(Integer subgroupId) {
        this.subgroupId = subgroupId;
    }

    public Integer getSpocId() {
        return spocId;
    }

    public void setSpocId(Integer spocId) {
        this.spocId = spocId;
    }

    public Integer getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(Integer corporateId) {
        this.corporateId = corporateId;
    }

    public Integer getBillingEntityId() {
        return billingEntityId;
    }

    public void setBillingEntityId(Integer billingEntityId) {
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

    public Object getCotravAgentName() {
        return cotravAgentName;
    }

    public void setCotravAgentName(Object cotravAgentName) {
        this.cotravAgentName = cotravAgentName;
    }

    public List<FlightPassanger> getPassangers() {
        return passangers;
    }

    public void setPassangers(List<FlightPassanger> passangers) {
        this.passangers = passangers;
    }
}
