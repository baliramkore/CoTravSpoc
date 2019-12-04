package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBookingApiResponse;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.TaxiDetailApiResponse;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.ViewTaxiBooking;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.TaxiBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaxiBookingRepository {

    private String TAG="BusBookingRepository";
    private TaxiBookingAPI todaysBusBookingAPI,pastBusBookingAPI,upcomingBusBookingAPI,cancelledBusBookingAPI,detailAPI;
    private MutableLiveData<List<TaxiBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<ViewTaxiBooking> bookingDetails;


    public TaxiBookingRepository(Application application){
        this.application = application;

        todaysBusBookingAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);
        upcomingBusBookingAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);
        pastBusBookingAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);
        cancelledBusBookingAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);
        detailAPI = ConfigRetrofit.configRetrofit(TaxiBookingAPI.class);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();


        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<ViewTaxiBooking>();

    }
    public LiveData<List<TaxiBooking>> getTodaysTaxiBooking(String authorization, String usertype, String spoc_id){
        todaysBusBookingAPI.getTaxiBookings(authorization,usertype,spoc_id,"1").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        todaysLiveData.setValue(response.body().getBookings());

                    }
                    else {
                        todaysConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    todaysConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return todaysLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }




    public LiveData<List<TaxiBooking>> getUpcomingTaxiBooking(String authorization, String usertype, String spoc_id){
        upcomingBusBookingAPI.getTaxiBookings(authorization,usertype,spoc_id,"11").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        upcomingLiveData.setValue(response.body().getBookings());

                    }
                    else {
                        upcomingConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    upcomingConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return upcomingLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<TaxiBooking>> getPastTaxiBooking(String authorization, String usertype, String spoc_id){
        pastBusBookingAPI.getTaxiBookings(authorization,usertype,spoc_id,"12").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        pastLiveData.setValue(response.body().getBookings());

                    }
                    else {
                        pastConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    pastConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return pastLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<TaxiBooking>> getCancelledTaxiBooking(String authorization, String usertype, String spoc_id){
        cancelledBusBookingAPI.getTaxiBookings(authorization,usertype,spoc_id,"6").enqueue(new Callback<TaxiBookingApiResponse>() {
            @Override
            public void onResponse(Call<TaxiBookingApiResponse> call, Response<TaxiBookingApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0) {
                        cancelledLiveData.setValue(response.body().getBookings());

                    }
                    else {
                        cancelledConnectionError.setValue("No Bookings Available");
                    }
                }else {
                    cancelledConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return cancelledLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public MutableLiveData<ViewTaxiBooking> getBookingDetails(String authorization, String usertype, String bookingId,String spoc_id){


        detailAPI.getTaxiBookingDetails(authorization,usertype,spoc_id,bookingId).enqueue(new Callback<TaxiDetailApiResponse>() {
            @Override
            public void onResponse(Call<TaxiDetailApiResponse> call, Response<TaxiDetailApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1") && response.body()!=null){

                        bookingDetails.setValue(response.body().getBookings().get(0));

                    }
                    else {
                        detailsConnectionError.setValue(" Error");
                    }
                }
                else {
                    detailsConnectionError.setValue("Connection Error");
                }
            }

            @Override
            public void onFailure(Call<TaxiDetailApiResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
