package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.show_hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotravspoc.repositories.HotelBookingRepository;

import java.util.List;

public class HotelBookingsViewModel extends AndroidViewModel
{


    private HotelBookingRepository hotelBookingRepository;
    private LiveData<List<HotelBooking>> todaysLiveData;
    private LiveData<List<HotelBooking>> upcomingLiveData;
    private LiveData<List<HotelBooking>> pastLiveData;
    private LiveData<List<HotelBooking>> cancelledLiveData;

    private LiveData<String>  todaysConnectionError;
    private LiveData<String>  upcomingConnectionError;
    private LiveData<String>  pastConnectionError;
    private LiveData<String>  cancelledConnectionError,hoteldetailsConnectionError;

    private MutableLiveData<ViewHotelBooking> hotelBookingDetails;

    public HotelBookingsViewModel(@NonNull Application application) {
        super(application);

        hotelBookingRepository =new HotelBookingRepository(this.getApplication());
        todaysConnectionError=hotelBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=hotelBookingRepository.getUpcomingConnectionError();
        pastConnectionError=hotelBookingRepository.getPastConnectionError();
        cancelledConnectionError=hotelBookingRepository.getCancelledConnectionError();
        hoteldetailsConnectionError=hotelBookingRepository.getDetailsConnectionError();




    }
    public void InitHotelBookingViewModel(String authorization, String usertype, String spoc_id){
        todaysLiveData=hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,spoc_id);
        upcomingLiveData=hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,spoc_id);
        pastLiveData=hotelBookingRepository.getPastHotelBooking(authorization,usertype,spoc_id);
        cancelledLiveData=hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,spoc_id);

    }


    ///Todays live data
    public LiveData<List<HotelBooking>> getTodaysLiveData(String authorization, String usertype, String spoc_id) {
        hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,spoc_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id){
        hotelBookingRepository.getTodaysHotelBooking(authorization,usertype,spoc_id);
    }


    ////Upcoming live data
    public LiveData<List<HotelBooking>> getUpcomingLiveData(String authorization, String usertype, String spoc_id) {
        hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,spoc_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String spoc_id){
        hotelBookingRepository.getUpcomingHotelBooking(authorization,usertype,spoc_id);
    }


    /////past live data
    public LiveData<List<HotelBooking>> getPastLiveData(String authorization, String usertype, String spoc_id) {
        hotelBookingRepository.getPastHotelBooking(authorization,usertype,spoc_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String spoc_id){
        hotelBookingRepository.getPastHotelBooking(authorization,usertype,spoc_id);
    }


    /////Cancelled live data
    public LiveData<List<HotelBooking>> getCancelledLiveData(String authorization, String usertype, String spoc_id) {
        hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,spoc_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String spoc_id){
        hotelBookingRepository.getCancelledHotelBooking(authorization,usertype,spoc_id);
    }

    ///detail live data
    public LiveData<ViewHotelBooking> getHotelBookingDetails(String authorization, String usertype, String bookingId) {
        hotelBookingDetails=hotelBookingRepository.getBookingDetails(authorization,usertype,bookingId);
        return hotelBookingDetails;
    }

    public LiveData<String> getHoteldetailsConnectionError() {
        hoteldetailsConnectionError=hotelBookingRepository.getDetailsConnectionError();
        return hoteldetailsConnectionError;
    }

}
