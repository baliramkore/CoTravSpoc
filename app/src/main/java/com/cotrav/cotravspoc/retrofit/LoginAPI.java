package com.cotrav.cotravspoc.retrofit;

import com.cotrav.cotravspoc.models.dashboard.DashboardApiResponce;
import com.cotrav.cotravspoc.models.login_models.LoginApiResponse;
import com.cotrav.cotravspoc.models.logout_model.LogoutApiResponse;
import com.cotrav.cotravspoc.models.view_spoc_model.ViewSpocApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginAPI {


    @FormUrlEncoded
    @POST(APIurls.LOGIN)
    Call<LoginApiResponse> performLogin(@Field("user_name") String user_name,
                                        @Field("user_password") String user_password,
                                        @Field("user_type") String user_type);

    @FormUrlEncoded
    @POST(APIurls.VIEWSPOC)
    Call<ViewSpocApiResponse> getSpocDetails(@Header("Authorization") String Authorization,
                                       @Header("usertype") String usertype,
                                       @Field("spoc_id") String spocId);


    @POST(APIurls.LOGOUT)
    Call<LogoutApiResponse> performLogout(@Header("Authorization") String Authorization,
                                       @Header("usertype") String usertype);

    @FormUrlEncoded
    @POST(APIurls.DASHBOARD)
    Call<DashboardApiResponce> viewDashboard(@Header("Authorization") String Authorization,
                                             @Header("usertype") String usertype,
                                             @Field("employee_id") String employee_id);

}
