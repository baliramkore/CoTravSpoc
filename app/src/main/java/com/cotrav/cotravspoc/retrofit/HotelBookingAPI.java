package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.show_hotel_model.show_hotel.HotelBookingApiResponse;
import com.cotrav.cotravspoc.models.show_hotel_model.view_hotel.HotelDetailApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HotelBookingAPI {
    @FormUrlEncoded
    @POST(APIurls.HOTELBOOKINGS)
    Call<HotelBookingApiResponse> getHotelBookings(@Header("Authorization") String Authorization,
                                                   @Header("usertype") String Usertype,
                                                   @Field("spoc_id") String spoc_id,
                                                   @Field("booking_type") String booking_type);

    @FormUrlEncoded
    @POST(APIurls.HOTELBOOKINGSDETAILS)
    Call<HotelDetailApiResponse> getHotelBookingDetails(@Header("Authorization") String Authorization,
                                                        @Header("usertype") String Usertype,
                                                        @Field("booking_id") String booking_id);
}
