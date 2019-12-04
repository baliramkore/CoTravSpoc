package com.cotrav.cotravspoc.models.show_bus_model.view_bus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewBusBookingDetailsResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Bookings")
    @Expose
    private List<BusBooking> bookings = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<BusBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<BusBooking> bookings) {
        this.bookings = bookings;
    }
    }