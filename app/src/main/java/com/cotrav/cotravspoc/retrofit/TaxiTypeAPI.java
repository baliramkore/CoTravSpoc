package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.taxitypemodel.TaxiTypeApiResponse;
import com.cotrav.cotravspoc.models.traintypemodel.TrainTypeApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface TaxiTypeAPI
{

        @FormUrlEncoded
        @POST(APIurls.TAXITYPE)
        Call<TaxiTypeApiResponse> getTaxiTypes(@Header("Authorization") String Authorization,
                                               @Header("usertype") String usertype,
                                               @Field("corporate_id")String corporate_id);



}
