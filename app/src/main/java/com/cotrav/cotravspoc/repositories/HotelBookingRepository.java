package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.show_hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotravspoc.models.show_hotel_model.show_hotel.HotelBookingApiResponse;
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.ViewHotelBooking;
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.HotelDetailApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.HotelBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelBookingRepository {

    private String TAG="HotelBookingRepository";
    private HotelBookingAPI todaysHotelBookingAPI,pastHotelBookingAPI,upcomingHotelBookingAPI,cancelledHotelBookingAPI,detailAPI;
    private MutableLiveData<List<HotelBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<ViewHotelBooking> bookingDetails;


    public HotelBookingRepository(Application application){
        this.application = application;

        todaysHotelBookingAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);
        upcomingHotelBookingAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);
        pastHotelBookingAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);
        cancelledHotelBookingAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);
        detailAPI = ConfigRetrofit.configRetrofit(HotelBookingAPI.class);

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
    public LiveData<List<HotelBooking>> getTodaysHotelBooking(String authorization, String usertype, String spoc_id){
        todaysHotelBookingAPI.getHotelBookings(authorization,usertype,spoc_id,"1").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
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
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return todaysLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError()
    {

        return todaysConnectionError;

    }


    public LiveData<List<HotelBooking>> getUpcomingHotelBooking(String authorization, String usertype,String spoc_id){
        upcomingHotelBookingAPI.getHotelBookings(authorization,usertype,spoc_id,"11").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getBookings()!=null && response.body().getBookings().size()>0)
                    {

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
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return upcomingLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<HotelBooking>> getPastHotelBooking(String authorization, String usertype,String spoc_id){
        pastHotelBookingAPI.getHotelBookings(authorization,usertype,spoc_id,"12").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
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
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return pastLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<HotelBooking>> getCancelledHotelBooking(String authorization, String usertype,String spoc_id){
        cancelledHotelBookingAPI.getHotelBookings(authorization,usertype,spoc_id,"6").enqueue(new Callback<HotelBookingApiResponse>() {
            @Override
            public void onResponse(Call<HotelBookingApiResponse> call, Response<HotelBookingApiResponse> response) {
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
            public void onFailure(Call<HotelBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return cancelledLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public MutableLiveData<ViewHotelBooking> getBookingDetails(String authorization, String usertype, String bookingId){


        detailAPI.getHotelBookingDetails(authorization,usertype,bookingId).enqueue(new Callback<HotelDetailApiResponse>() {
            @Override
            public void onResponse(Call<HotelDetailApiResponse> call, Response<HotelDetailApiResponse> response) {
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
            public void onFailure(Call<HotelDetailApiResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
