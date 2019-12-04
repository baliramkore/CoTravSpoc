package com.cotrav.cotravspoc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotravspoc.repositories.TaxiBookingRepository;
import java.util.List;

public class TaxiBookingsViewModel extends AndroidViewModel
{
    private TaxiBookingRepository taxiBookingRepository;
    private LiveData<List<TaxiBooking>> todaysLiveData;
    private LiveData<List<TaxiBooking>> upcomingLiveData;
    private LiveData<List<TaxiBooking>> pastLiveData;
    private LiveData<List<TaxiBooking>> cancelledLiveData;
    private LiveData<String>  todaysConnectionError;
    private LiveData<String>  upcomingConnectionError;
    private LiveData<String>  pastConnectionError;
    private LiveData<String>  cancelledConnectionError;
    private LiveData<ViewTaxiBooking> taxiBookingDetails;
    private LiveData<String>  taxidetailsConnectionError;


    public TaxiBookingsViewModel(@NonNull Application application) {
        super(application);

        taxiBookingRepository =new TaxiBookingRepository(this.getApplication());
        todaysConnectionError=taxiBookingRepository.getTodaysConnectionError();
        upcomingConnectionError=taxiBookingRepository.getUpcomingConnectionError();
        pastConnectionError=taxiBookingRepository.getPastConnectionError();
        cancelledConnectionError=taxiBookingRepository.getCancelledConnectionError();
        taxidetailsConnectionError=taxiBookingRepository.getDetailsConnectionError();




    }
    public void InitPlaneBookingViewModel(String authorization, String usertype, String spoc_id){
        todaysLiveData=taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,spoc_id);
        upcomingLiveData=taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,spoc_id);
        pastLiveData=taxiBookingRepository.getPastTaxiBooking(authorization,usertype,spoc_id);
        cancelledLiveData=taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,spoc_id);

    }


    ///Todays live data
    public LiveData<List<TaxiBooking>> getTodaysLiveData(String authorization, String usertype, String spoc_id) {
        taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,spoc_id);
        return todaysLiveData;
    }

    public LiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }

    public void refreshTodaysBooking(String authorization, String usertype,String spoc_id){
        taxiBookingRepository.getTodaysTaxiBooking(authorization,usertype,spoc_id);
    }


    ////Upcoming live data
    public LiveData<List<TaxiBooking>> getUpcomingLiveData(String authorization, String usertype,String spoc_id) {
        taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,spoc_id);
        return upcomingLiveData;
    }

    public LiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }

    public void refreshUpcomingBooking(String authorization, String usertype,String spoc_id){
        taxiBookingRepository.getUpcomingTaxiBooking(authorization,usertype,spoc_id);
    }


    /////past live data
    public LiveData<List<TaxiBooking>> getPastLiveData(String authorization, String usertype,String spoc_id) {
        taxiBookingRepository.getPastTaxiBooking(authorization,usertype,spoc_id);
        return pastLiveData;
    }

    public LiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }

    public void refreshPastBooking(String authorization, String usertype,String spoc_id){
        taxiBookingRepository.getPastTaxiBooking(authorization,usertype,spoc_id);
    }


    /////Cancelled live data
    public LiveData<List<TaxiBooking>> getCancelledLiveData(String authorization, String usertype,String spoc_id) {
        taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,spoc_id);
        return cancelledLiveData;
    }

    public LiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }

    public void refreshCancelledBooking(String authorization, String usertype,String spoc_id){
        taxiBookingRepository.getCancelledTaxiBooking(authorization,usertype,spoc_id);
    }

    ///detail live data
    public LiveData<ViewTaxiBooking> getTaxiBookingDetails(String authorization, String usertype, String bookingId,String spoc_id) {
        taxiBookingDetails=taxiBookingRepository.getBookingDetails(authorization,usertype,bookingId,spoc_id);
        return taxiBookingDetails;
    }

    public LiveData<String> getTaxiDetailsConnectionError() {
        taxidetailsConnectionError=taxiBookingRepository.getDetailsConnectionError();
        return taxidetailsConnectionError;
    }


}
