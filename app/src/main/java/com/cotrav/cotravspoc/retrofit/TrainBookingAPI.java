package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.show_bus_model.view_bus.ViewBusBookingDetailsResponse;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBookingApiResponse;
import com.cotrav.cotravspoc.models.show_train_model.view_train.ViewTrainBookingDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TrainBookingAPI
{

    @FormUrlEncoded
    @POST(APIurls.TRAINBOOKINGS)
    Call<TrainBookingApiResponse> getTrainBookings(@Header("Authorization") String Authorization,
                                                   @Header("USERTYPE") String Usertype,
                                                   @Field("spoc_id") String spoc_id,
                                                   @Field("booking_type") String booking_type);

    @FormUrlEncoded
    @POST(APIurls.TRAINBOOKINGSDETAILS)
    Call<ViewTrainBookingDetailsResponse> getTrainBookingDetails(@Header("Authorization") String Authorization,
                                                                 @Header("USERTYPE") String Usertype,
                                                                 @Field("booking_id") String booking_id);
}
