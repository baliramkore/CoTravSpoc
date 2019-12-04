package com.cotrav.cotravspoc.local_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;

import java.util.List;


@Dao
public interface DaoAccess
{


    @Query("SELECT * from taxi_bookings")
    LiveData<List<TaxiBooking>> getUpcommingTaxiBookings();

    @Insert
    //void insertTaxiBooking(List<TaxiBooking> taxiBooking);
    //void insertTaxiBooking(List<TaxiBooking> taxiBookingList);

    void insertTaxiBooking(List<com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking> upcomingBookings);
}
