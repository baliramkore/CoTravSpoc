package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.companytypemodel.CompanyApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CompanyTypeAPI

{
    @FormUrlEncoded
    @POST(APIurls.COMPANYTYPE)
    Call<CompanyApiResponse> getCompanyTypes(@Header("Authorization") String Authorization,
                                           @Header("usertype") String usertype,
                                           @Field("corporate_id")String corporate_id);
}
