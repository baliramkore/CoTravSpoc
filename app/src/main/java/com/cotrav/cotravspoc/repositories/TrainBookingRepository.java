package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBooking;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBookingApiResponse;
import com.cotrav.cotravspoc.models.show_train_model.view_train.ViewTrainBooking;
import com.cotrav.cotravspoc.models.show_train_model.view_train.ViewTrainBookingDetailsResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.TrainBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainBookingRepository {

    private String TAG="TrainBookingRepository";
    private TrainBookingAPI todaysTrainBookingAPI,pastTrainBookingAPI,upcomingTrainBookingAPI,cancelledTrainBookingAPI,detailAPI;
    private MutableLiveData<List<TrainBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;

    private MutableLiveData<ViewTrainBooking> bookingDetails;

    public TrainBookingRepository(Application application){
        this.application = application;

        todaysTrainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        upcomingTrainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        pastTrainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        cancelledTrainBookingAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);
        detailAPI = ConfigRetrofit.configRetrofit(TrainBookingAPI.class);

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
    public LiveData<List<TrainBooking>> getTodaysTrainBooking(String authorization, String usertype, String spoc_id){
        todaysTrainBookingAPI.getTrainBookings(authorization,usertype,spoc_id,"1").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
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
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return todaysLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<TrainBooking>> getUpcomingTrainBooking(String authorization, String usertype,String spoc_id){
        upcomingTrainBookingAPI.getTrainBookings(authorization,usertype,spoc_id,"11").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
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
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return upcomingLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<TrainBooking>> getPastTrainBooking(String authorization, String usertype,String spoc_id){
        pastTrainBookingAPI.getTrainBookings(authorization,usertype,spoc_id,"12").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
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
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return pastLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<TrainBooking>> getCancelledTrainBooking(String authorization, String usertype,String spoc_id){
        cancelledTrainBookingAPI.getTrainBookings(authorization,usertype,spoc_id,"6").enqueue(new Callback<TrainBookingApiResponse>() {
            @Override
            public void onResponse(Call<TrainBookingApiResponse> call, Response<TrainBookingApiResponse> response) {
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
            public void onFailure(Call<TrainBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return cancelledLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }



    public MutableLiveData<ViewTrainBooking> getBookingDetails(String authorization, String usertype, String bookingId){


        detailAPI.getTrainBookingDetails(authorization,usertype,bookingId).enqueue(new Callback<ViewTrainBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewTrainBookingDetailsResponse> call, Response<ViewTrainBookingDetailsResponse> response) {
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
            public void onFailure(Call<ViewTrainBookingDetailsResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
