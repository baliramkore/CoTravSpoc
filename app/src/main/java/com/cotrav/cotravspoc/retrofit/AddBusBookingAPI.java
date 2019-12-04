package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.employee_details.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddBusBookingAPI {

    @FormUrlEncoded
    @POST(APIurls.AddBusBooking)
    Call<AddBusBookingActivity.Responce> addBusBooking(
                                            @Header("Authorization")String authorization,
                                            @Header("usertype")String userType,
                                            @Field("user_id") String userId,
                                            @Field("corporate_id") String corporateId,
                                            @Field("spoc_id") String spocId,
                                            @Field("group_id") String groupId,
                                            @Field("subgroup_id") String subgroupId,
                                            @Field("from") String fromCity,
                                            @Field("to") String toCity,
                                            @Field("booking_datetime") String bookingDateTime,
                                            @Field("journey_datetime") String journeyDateTime,
                                            @Field("journey_datetime_to")String dropDateTime,
                                            @Field("entity_id") String entityId,
                                            @Field("preferred_bus") String preferredBus,
                                            @Field("assessment_code") String assessmentCode,
                                            @Field("assessment_city_id") String assessmentCityId,
                                            @Field("reason_booking") String reasonBooking,
                                            @Field("no_of_seats") String seatCount,
                                            @Field("employees") ArrayList<String> employees,
                                            @Field("bus_type") String busType);
}