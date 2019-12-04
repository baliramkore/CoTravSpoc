package com.cotrav.cotravspoc.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotravspoc.models.show_flight_model.show_flight.FlightBooking;
import com.cotrav.cotravspoc.models.show_flight_model.show_flight.FlightBookingApiResponse;
import com.cotrav.cotravspoc.models.show_flight_model.view_flight.FlightBookingDetails;
import com.cotrav.cotravspoc.models.show_flight_model.view_flight.ViewFlightBookingDetailsResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.FlightBookingAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightBookingRepository {

    private String TAG="FlightBookingRepository";
    private FlightBookingAPI todaysFlightBookingAPI,pastFlightBookingAPI,upcomingFlightBookingAPI,cancelledFlightBookingAPI,detailAPI;
    private MutableLiveData<List<FlightBooking>> todaysLiveData,pastLiveData,upcomingLiveData,cancelledLiveData;
    private MutableLiveData<String> todaysConnectionError,pastConnectionError,upcomingConnectionError,cancelledConnectionError,detailsConnectionError;
    Application application;
    private MutableLiveData<FlightBookingDetails> bookingDetails;


    public FlightBookingRepository(Application application){
        this.application = application;

        todaysFlightBookingAPI = ConfigRetrofit.configRetrofit(FlightBookingAPI.class);
        upcomingFlightBookingAPI = ConfigRetrofit.configRetrofit(FlightBookingAPI.class);
        pastFlightBookingAPI = ConfigRetrofit.configRetrofit(FlightBookingAPI.class);
        cancelledFlightBookingAPI = ConfigRetrofit.configRetrofit(FlightBookingAPI.class);
        detailAPI = ConfigRetrofit.configRetrofit(FlightBookingAPI.class);

        todaysLiveData = new MutableLiveData<>();
        upcomingLiveData = new MutableLiveData<>();
        cancelledLiveData = new MutableLiveData<>();
        pastLiveData = new MutableLiveData<>();


        todaysConnectionError = new MutableLiveData<>();
        upcomingConnectionError = new MutableLiveData<>();
        pastConnectionError = new MutableLiveData<>();
        cancelledConnectionError = new MutableLiveData<>();
        detailsConnectionError = new MutableLiveData<>();
        bookingDetails=new MutableLiveData<FlightBookingDetails>();

    }
    public LiveData<List<FlightBooking>> getTodaysFlightBooking(String authorization, String usertype, String spoc_id){
        todaysFlightBookingAPI.getFlightBookings(authorization,usertype,spoc_id,"1").enqueue(new Callback<FlightBookingApiResponse>() {
            @Override
            public void onResponse(Call<FlightBookingApiResponse> call, Response<FlightBookingApiResponse> response) {
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
            public void onFailure(Call<FlightBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                todaysConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return todaysLiveData;
    }


    public MutableLiveData<String> getTodaysConnectionError() {
        return todaysConnectionError;
    }


    public LiveData<List<FlightBooking>> getUpcomingFlightBooking(String authorization, String usertype,String spoc_id){
        upcomingFlightBookingAPI.getFlightBookings(authorization,usertype,spoc_id,"11").enqueue(new Callback<FlightBookingApiResponse>() {
            @Override
            public void onResponse(Call<FlightBookingApiResponse> call, Response<FlightBookingApiResponse> response) {
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
            public void onFailure(Call<FlightBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                upcomingConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return upcomingLiveData;
    }


    public MutableLiveData<String> getUpcomingConnectionError() {
        return upcomingConnectionError;
    }



    public LiveData<List<FlightBooking>> getPastFlightBooking(String authorization, String usertype,String spoc_id){
        pastFlightBookingAPI.getFlightBookings(authorization,usertype,spoc_id,"12").enqueue(new Callback<FlightBookingApiResponse>() {
            @Override
            public void onResponse(Call<FlightBookingApiResponse> call, Response<FlightBookingApiResponse> response) {
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
            public void onFailure(Call<FlightBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                pastConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return pastLiveData;
    }


    public MutableLiveData<String> getPastConnectionError() {
        return pastConnectionError;
    }



    public LiveData<List<FlightBooking>> getCancelledFlightBooking(String authorization, String usertype,String spoc_id){
        cancelledFlightBookingAPI.getFlightBookings(authorization,usertype,spoc_id,"6").enqueue(new Callback<FlightBookingApiResponse>() {
            @Override
            public void onResponse(Call<FlightBookingApiResponse> call, Response<FlightBookingApiResponse> response) {
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
            public void onFailure(Call<FlightBookingApiResponse> call, Throwable t) {
                Log.e(TAG,"Connection Error");
                cancelledConnectionError.setValue("Connection Error "+t.getMessage());
            }
        });
        return cancelledLiveData;
    }


    public MutableLiveData<String> getCancelledConnectionError() {
        return cancelledConnectionError;
    }






    public MutableLiveData<FlightBookingDetails> getBookingDetails(String authorization, String usertype, String bookingId){


        detailAPI.getFlightBookingDetails(authorization,usertype,bookingId).enqueue(new Callback<ViewFlightBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewFlightBookingDetailsResponse> call, Response<ViewFlightBookingDetailsResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1") && response.body()!=null){
                        bookingDetails.setValue(response.body().getBookings().get(0));

                        //bookingDetails.setValue(response.body().getBookings().get(0));
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
            public void onFailure(Call<ViewFlightBookingDetailsResponse> call, Throwable t) {
                detailsConnectionError.setValue("Connection Error: "+t.getMessage());
            }
        });

        return bookingDetails;
    }

    public MutableLiveData<String> getDetailsConnectionError() {
        return detailsConnectionError;
    }

}
