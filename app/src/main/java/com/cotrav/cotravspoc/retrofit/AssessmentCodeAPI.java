package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.assesmentcodemodel.AssessmentCodeApiResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Baliram on 07/7/19.
 */

public interface AssessmentCodeAPI {

    @FormUrlEncoded
    @POST(APIurls.ASSCODES)
    Call<AssessmentCodeApiResponse> getAssessmentCodes(@Header("Authorization") String Authorization,
                                                       @Header("usertype") String usertype,
                                                       @Field("corporate_id")String corporate_id);
}
