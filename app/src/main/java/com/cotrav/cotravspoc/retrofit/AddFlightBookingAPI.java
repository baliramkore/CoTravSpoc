package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.addbookings.addflight.AddFlightBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddFlightBookingAPI {

    @FormUrlEncoded
    @POST(APIurls.AddFlightBooking)
    Call<AddFlightBookingActivity.Responce> addFlightBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("usage_type")String usageType,
            @Field("trip_type")String tripType,
            @Field("seat_type")String seatType,
            @Field("from_city")String fromCity,
            @Field("to_city")String toCity,
            @Field("booking_datetime")String bookingDateTime,
            @Field("departure_datetime")String departureDateTime,
            @Field("preferred_flight")String preferedFlight,
            @Field("assessment_code")String assismentCode,
            @Field("assessment_city_id")String assismentCity,
            @Field("no_of_seats")String no_of_seats,
            @Field("group_id")String groupId,
            @Field("subgroup_id")String subgroupId,
            @Field("spoc_id")String spocId,
            @Field("corporate_id")String corporateId,
            @Field("billing_entity_id")String billingEntityId,
            @Field("reason_booking")String bookingReason,
            @Field("user_id")String userId,
            @Field("user_type")String user_Type,
            @Field("employees") ArrayList<String> employees);
}
