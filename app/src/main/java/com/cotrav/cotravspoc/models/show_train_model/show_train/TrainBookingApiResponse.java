package com.cotrav.cotravspoc.models.show_train_model.show_train;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainBookingApiResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Bookings")
    @Expose
    private List<TrainBooking> bookings = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TrainBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<TrainBooking> bookings) {
        this.bookings = bookings;
    }

}