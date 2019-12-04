package com.cotrav.cotravspoc.local_database.local_model.taxi;

import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxiApiResponse
{
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Bookings")
    @Expose
    private List<TaxiBooking> bookings = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<TaxiBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<TaxiBooking> bookings) {
        this.bookings = bookings;
    }
}
