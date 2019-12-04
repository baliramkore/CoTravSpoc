package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.fragments.FragmentLocalTaxi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AddTaxiBookingAPI
{


    @FormUrlEncoded
    @POST(APIurls.AddTaxiBooking)
    Call<FragmentLocalTaxi.AddTaxiResponce> addLocalTaxiBooking(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("user_id") String userId,
            @Field("corporate_id") String corporateId,
            @Field("entity_id")String entity_id,
            @Field("spoc_id") String spocId,
            @Field("group_id") String groupId,
            @Field("subgroup_id") String subgroupId,
            @Field("tour_type") String tour_type,
            @Field("pickup_city") String pickup_city,
            @Field("pickup_location") String pickup_location,
            @Field("drop_location") String drop_location,
            @Field("pickup_datetime") String pickup_datetime,
            @Field("taxi_type") String taxi_type,
            @Field("package_id") String package_id,
            @Field("assessment_code") String assessment_code,
            @Field("assessment_city_id") String assessment_city_id,
            @Field("reason_booking") String reason_booking,
            @Field("no_of_seats") String no_of_seats,
            @Field("employees") String[] employees);

    @FormUrlEncoded
    @POST(APIurls.AddTaxiBooking)
    Call<FragmentLocalTaxi.AddTaxiResponce> addRadioTaxiBooking(
            @Header("Authorization")String Authorization,
            @Header("usertype")String usertype,
            @Field("user_id") String user_id,
            @Field("corporate_id") String corporate_id,
            @Field("entity_id")String entity_id,
            @Field("spoc_id") String spoc_id,
            @Field("group_id") String group_id,
            @Field("subgroup_id") String subgroup_id,
            @Field("tour_type")String tour_type,
            @Field("pickup_city")String pickup_city,
            @Field("pickup_location")String pickup_location,
            @Field("drop_location")String drop_location,
            @Field("pickup_datetime")String pickup_datetime,
            @Field("taxi_type")String taxi_type,
            @Field("assessment_code")String assessment_code,
            @Field("assessment_city_id")String assessment_city_id,
            @Field("reason_booking")String reason_booking,
            @Field("no_of_seats")String no_of_seats,
            @Field("employees")String[] employees);


    @FormUrlEncoded
    @POST(APIurls.AddTaxiBooking)
    Call<FragmentLocalTaxi.AddTaxiResponce> addOutStationTaxiBooking(
            @Header("Authorization")String Authorization,
            @Header("usertype")String usertype,
            @Field("user_id") String user_id,
            @Field("corporate_id") String corporate_id,
            @Field("entity_id")String entity_id,
            @Field("spoc_id") String spoc_id,
            @Field("group_id") String group_id,
            @Field("subgroup_id") String subgroup_id,
            @Field("tour_type")String tour_type,
            @Field("pickup_city")String pickup_city,
            @Field("pickup_location")String pickup_location,
            @Field("drop_location")String drop_location,
            @Field("pickup_datetime")String pickup_datetime,
            @Field("taxi_type")String taxi_type,
            @Field("package_id") String package_id,
            @Field("assessment_code")String assessment_code,
            @Field("assessment_city_id")String assessment_city_id,
            @Field("reason_booking")String reason_booking,
            @Field("no_of_seats")String no_of_seats,
            @Field("no_of_days")String no_of_days,
            @Field("employees")String[] employees);



}
