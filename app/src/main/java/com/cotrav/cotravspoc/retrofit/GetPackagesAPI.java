package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.fragments.FragmentLocalTaxi;
import com.cotrav.cotravspoc.models.localpackagesmodel.PackagesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetPackagesAPI
{

    @FormUrlEncoded
    @POST(APIurls.getratesbycity)
    Call<PackagesResponse> getTaxiPackages(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId,
            @Field("city_id")String cityId,
            @Field("taxi_type")String taxiType,
            @Field("tour_type")String tourType);
}