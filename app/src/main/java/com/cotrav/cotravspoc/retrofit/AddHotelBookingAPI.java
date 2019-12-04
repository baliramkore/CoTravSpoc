package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.activities.addbookings.addhotel.AddHotelBookingActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddHotelBookingAPI
{

    @FormUrlEncoded
    @POST(APIurls.AddHotelBooking)
    Call<AddHotelBookingActivity.Responce> addHotelBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("from_city_id") String fromCityId,
            @Field("from_area_id") String fromAreaId,
            @Field("preferred_area") String preferedArea,
            @Field("checkin_datetime") String checkinDateTime,
            @Field("checkout_datetime") String checkoutDateTime,
            @Field("bucket_priority_1") String pri1Id,
            @Field("bucket_priority_2") String pri2Id,
            @Field("room_type_id") String roomTypeId,
            @Field("preferred_hotel") String prefHotel,
            @Field("booking_datetime") String bookingDateTime,
            @Field("assessment_code") String assismentCode,
            @Field("assessment_city_id") String assismentCity,
            @Field("no_of_seats") String numPassengers,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("spoc_id") String spocId,
            @Field("corporate_id") String corporateId,
            @Field("billing_entity_id") String billingEntity,
            @Field("reason_booking") String bookingReason,
            @Field("user_id") String userId,
            @Field("user_type") String user_type,
            @Field("employees") ArrayList<String> employees);

}
