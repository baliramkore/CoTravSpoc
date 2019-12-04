package com.cotrav.cotravspoc.retrofit;
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBookingApiResponse;
import com.cotrav.cotravspoc.models.show_taxi_model.view_taxi.TaxiDetailApiResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TaxiBookingAPI
{
    @FormUrlEncoded
    @POST(APIurls.TAXIBOOKINGS)
    Call<TaxiBookingApiResponse> getTaxiBookings(@Header("Authorization") String Authorization,
                                                 @Header("usertype") String Usertype,
                                                 @Field("spoc_id") String spoc_id,
                                                 @Field("booking_type")String booking_type);
    @FormUrlEncoded
    @POST(APIurls.TAXIBOOKINGSDETAILS)
    Call<TaxiDetailApiResponse> getTaxiBookingDetails(@Header("Authorization") String Authorization,
                                                      @Header("USERTYPE") String Usertype,
                                                      @Field("spoc_id")String spocId,
                                                      @Field("booking_id") String booking_id)
            ;
}
