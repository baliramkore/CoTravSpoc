package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.localpackagesmodel.PackagesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetBillingEntities
{

    @FormUrlEncoded
    @POST(APIurls.billingEntity)
    Call<PackagesResponse> getBillingEntities(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("corporate_id")String corporateId);
}
