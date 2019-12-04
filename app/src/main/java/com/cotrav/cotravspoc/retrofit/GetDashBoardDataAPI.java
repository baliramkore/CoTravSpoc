package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardApiResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetDashBoardDataAPI
{

    @FormUrlEncoded
    @POST(APIurls.DASHBOARD)
    Call<DashBoardApiResponse> getDashBoardData(
            @Header("Authorization")String authorization,
            @Header("usertype")String userType,
            @Field("spoc_id") String userId);
}
