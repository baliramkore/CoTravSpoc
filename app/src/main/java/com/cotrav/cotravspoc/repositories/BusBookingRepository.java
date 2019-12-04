package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBookingApiResponse;
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.BusBooking;
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.ViewBusBookingDetailsResponse;
import com.cotrav.cotravspoc.retrofit.BusBookingAPI;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusBookingRepository {

    private String TAG="BusBookingRepository";
    private BusBookingAPI todaysBusBookingAPI,pastBusBookingAPI,upcomingBusBookingAPI,cancelledBusBookingAPI,detailAPI;
    private MutableLiveData<List<com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<BusBooking> bookingDetails;


    public BusBookingRepository(Application application){
        this.application = application;

        todaysBusBookingAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);
        upcomingBusBookingAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);
        pastBusBookingAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);
        cancelledBusBookingAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);
        detailAPI = ConfigRetrofit.configRetrofit(BusBookingAPI.class);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();


        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<>();

    }
    public LiveData<List<com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking>> getTodaysBusBooking(String authorization, String usertype, String spoc_id){
        todaysBusBookingAPI.getBusBookings(authorization,usertype,spoc_id,"1").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
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
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return todaysLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking>> getUpcomingBusBooking(String authorization, String usertype, String spoc_id){
        upcomingBusBookingAPI.getBusBookings(authorization,usertype,spoc_id,"11").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
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
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return upcomingLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking>> getPastBusBooking(String authorization, String usertype, String spoc_id){
        pastBusBookingAPI.getBusBookings(authorization,usertype,spoc_id,"12").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
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
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return pastLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking>> getCancelledBusBooking(String authorization, String usertype, String spoc_id){
        cancelledBusBookingAPI.getBusBookings(authorization,usertype,spoc_id,"6").enqueue(new Callback<BusBookingApiResponse>() {
            @Override
            public void onResponse(Call<BusBookingApiResponse> call, Response<BusBookingApiResponse> response) {
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
            public void onFailure(Call<BusBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return cancelledLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public MutableLiveData<BusBooking> getBookingDetails(String authorization, String usertype, String bookingId){


        detailAPI.getBusBookingDetails(authorization,usertype,bookingId).enqueue(new Callback<ViewBusBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewBusBookingDetailsResponse> call, Response<ViewBusBookingDetailsResponse> response) {

                    if (response.body().getSuccess().equals("1") && response.body()!=null){

                        bookingDetails.setValue(response.body().getBookings().get(0));

                    }
                    else {
                        detailsConnectionError.setValue(" Error");
                    }
            }

            @Override
            public void onFailure(Call<ViewBusBookingDetailsResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
