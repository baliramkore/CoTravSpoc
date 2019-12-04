package com.cotrav.cotravspoc.retrofit;



import com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBookingApiResponse;
import com.cotrav.cotravspoc.models.show_bus_model.view_bus.ViewBusBookingDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BusBookingAPI {
    @FormUrlEncoded
    @POST(APIurls.BUSBOOKINGS)
    Call<BusBookingApiResponse> getBusBookings(@Header("Authorization") String Authorization,
                                               @Header("USERTYPE") String Usertype,
                                               @Field("spoc_id") String spoc_id,
                                               @Field("booking_type") String booking_type);

    @FormUrlEncoded
    @POST(APIurls.BUSBOOKINGSDETAILS)
    Call<ViewBusBookingDetailsResponse> getBusBookingDetails(@Header("Authorization") String Authorization,
                                                             @Header("USERTYPE") String Usertype,
                                                             @Field("booking_id") String booking_id);
}
