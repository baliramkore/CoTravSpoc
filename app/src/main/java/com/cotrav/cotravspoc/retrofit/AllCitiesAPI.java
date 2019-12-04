package com.cotrav.cotravspoc.retrofit;
import com.cotrav.cotravspoc.models.allcitiesmodel.AllCityResponse;
import com.cotrav.cotravspoc.models.assesmentcitymodel.AssCityApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Baliram on 07/7/19.
 */

public interface AllCitiesAPI {

    @POST(APIurls.CITIES)
    Call<AllCityResponse> getAllCities(@Header("Authorization") String Authorization,
                                       @Header("usertype") String usertype);

    @FormUrlEncoded
    @POST(APIurls.ASSCITIES)
    Call<AssCityApiResponse> getAllAssCities(@Header("Authorization") String Authorization,
                                             @Header("usertype") String usertype,
                                             @Field("corporate_id")String corporateId);


}
