package com.cotrav.cotravspoc.repositories;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.activities.login.LoginActivity;
import com.cotrav.cotravspoc.models.login_models.LoginApiResponse;
import com.cotrav.cotravspoc.models.login_models.User;
import com.cotrav.cotravspoc.models.logout_model.LogoutApiResponse;
import com.cotrav.cotravspoc.models.view_spoc_model.ViewSpoc;
import com.cotrav.cotravspoc.models.view_spoc_model.ViewSpocApiResponse;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.LoginAPI;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class LoginRepository {

    private final String TAG="LoginRepositoty";
    private LoginAPI loginAPI;
    private SharedPreferences userPreference,spocPref;
    private MutableLiveData<String> access_token;
    private MutableLiveData<String> access_OTP;

    private MutableLiveData<List<User>> userMutableLiveData;
    private MutableLiveData<List<ViewSpoc>> spocMutableLiveData;
    private boolean is_login;
    Application application;
    Context context;
    private MutableLiveData<String> error;
    private MutableLiveData<String> verification_code;
    private MutableLiveData<String> loginSuccessful;
    private MutableLiveData<String> verificationSuccessful;



    public LoginRepository(Application application) {
       this.application=application;
        userPreference=application.getSharedPreferences("login_info", MODE_PRIVATE);
        spocPref = application.getSharedPreferences("spoc_info",MODE_PRIVATE);
        loginAPI= ConfigRetrofit.configRetrofit(LoginAPI.class);
        is_login=userPreference.getBoolean("is_login",false);
        access_token=new MutableLiveData<>();
        access_OTP=new MutableLiveData<>();
        userMutableLiveData=new MutableLiveData<>();
        spocMutableLiveData = new MutableLiveData<>();
        verification_code=new MutableLiveData<>();
        loginSuccessful=new MutableLiveData<>();
        verificationSuccessful=new MutableLiveData<>();

        error=new MutableLiveData<>();
        Log.d(TAG,Boolean.toString(is_login));
        Log.d(TAG,userPreference.getString("login_info","n"));
    }

    public boolean isLogin(){
        Log.d(TAG,Boolean.toString(userPreference.getBoolean("is_login",false)));
        return userPreference.getBoolean("is_login",false);
    }
    public MutableLiveData<List<User>> getEmployeeInfo(){
        return userMutableLiveData;
    }

    public MutableLiveData<String> getAccessToken(){
        return  access_token;
    }

    public MutableLiveData<String> getAccessOTP(){
        return  access_OTP;
    }

    public MutableLiveData<String> getLoginSuccessful() {
        return loginSuccessful;
    }
    public MutableLiveData<String> getError() {
        return error;
    }


    public void performLogin(String username,String password)
    {

        loginAPI.performLogin(username,password,"4").enqueue(new Callback<LoginApiResponse>() {
            @Override
            public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getUser()!=null){

                        access_token.setValue(response.body().getAccessToken());
                        userMutableLiveData.setValue(response.body().getUser());
                        loginSuccessful.setValue("Otp sent Successfully");
                        verification_code.setValue(response.body().getoTP());
                        access_OTP.setValue(response.body().getoTP());
                        SharedPreferences.Editor editor=userPreference.edit();
                        editor.putString("user_name",response.body().getUser().get(0).getUserName());
                        editor.putString("emp_id",response.body().getUser().get(0).getId());
                        editor.putString("email",response.body().getUser().get(0).getEmail());
                        editor.putString("access_token",access_token.getValue());
                        editor.putString("user_contact", response.body().getUser().get(0).getUserContact());
                        editor.putString("spoc_id", response.body().getUser().get(0).getId());
                        //editor.putBoolean("is_login",true);
                        editor.putString("login_info", GsonStringConvertor.gsonToString(userMutableLiveData.getValue()));
                        editor.commit();

                      /*  //access_token.setValue(response.body().getAccessToken());
                        access_OTP.setValue(response.body().getoTP());
                        userMutableLiveData.setValue(response.body().getUser());
                        loginSuccessful.setValue("Otp sent Successfully");
                        //loginSuccessful.setValue("Successful");
                        SharedPreferences.Editor editor=userPreference.edit();
                        //editor.putString("OTP",response.body().getoTP());
                        //editor.putString("Authorization","Token "+response.body().getAccessToken());
                        editor.putString("usertype","4");
                        editor.putString("user_name",response.body().getUser().get(0).getUserName());
                        editor.putString("user_id",response.body().getUser().get(0).getId().toString());
                        editor.putString("spoc_id",response.body().getUser().get(0).getId().toString());
                        editor.putString("corporate_id", response.body().getUser().get(0).getCorporateId().toString());
                        editor.putString("email",response.body().getUser().get(0).getEmail());
                        editor.putString("user_contact",response.body().getUser().get(0).getUserContact().toString());
                        editor.putString("access_token",access_token.getValue());
                        editor.putString("group_id", response.body().getUser().get(0).getGroupId().toString());
                        editor.putString("subgroup_id", response.body().getUser().get(0).getSubgroupId().toString());
                        //editor.putBoolean("is_login",true);
                        editor.putString("is_local", response.body().getUser().get(0).getIsLocal().toString());
                        editor.putString("is_radio", response.body().getUser().get(0).getIsRadio().toString());
                        editor.putString("is_outstation", response.body().getUser().get(0).getIsOutstation().toString());
                        editor.commit();*/
                        // Toast.makeText(LoginActivity.this, response.body().response.accessToken+"..."+response.body().success, Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.body().getSuccess());
                    }
                    if (response.body().getSuccess().equals("0")){
                        error.setValue(response.body().getMessage());
                        Log.d(TAG,".."+response.body().getMessage());
                    }
                    /*else {
                       // error.setValue(response.body().getMessage());
                        Log.d(TAG,".."+response.body().getMessage());
                    }*/
                }else {
                    error.setValue("Connection Unsuccessful");
                    Log.e(TAG,"Connection Unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<LoginApiResponse> call, Throwable t) {

                Log.e(TAG,"Connection failed");
                error.setValue("Connection Failed");
            }
        });
    }

    public void getSpocDetails(String authorization,String spoc_id){
        loginAPI.getSpocDetails(authorization,"4",spoc_id).enqueue(new Callback<ViewSpocApiResponse>() {
            @Override
            public void onResponse(Call<ViewSpocApiResponse> call, Response<ViewSpocApiResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().getSuccess().equals("1")&& response.body().getSpoc()!=null){

                        spocMutableLiveData.setValue(response.body().getSpoc());
                        loginSuccessful.setValue("Successful");
                        SharedPreferences.Editor editor=spocPref.edit();
                        editor.putString("group_id",response.body().getSpoc().get(0).getGroupId());
                        editor.putString("subgroup_id",response.body().getSpoc().get(0).getSubgroupId());
                        editor.putString("has_assessment_codes", String.valueOf(response.body().getSpoc().get(0).getHasAssesmentCode()));
                        editor.putString("spoc_info", GsonStringConvertor.gsonToString(spocMutableLiveData.getValue()));

                        editor.commit();
                        // Toast.makeText(LoginActivity.this, response.body().response.accessToken+"..."+response.body().success, Toast.LENGTH_LONG).show();
                        Log.d(TAG, response.body().getSpoc().toString());
                        error.setValue("1");
                    }
                    if (response.body().getSuccess().equals("0")){
                        SharedPreferences.Editor editor=userPreference.edit();
                        editor.putBoolean("is_login",false);
                        editor.putString("access_token"," ");
                        editor.commit();
                        error.setValue("0");
                        Log.d(TAG,".."+"ERROR");
                    }
                }else {
                    error.setValue("Connection Unsuccessful");
                    Log.e(TAG,"Connection Unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<ViewSpocApiResponse> call, Throwable t) {

            }
        });

    }
    public void performLogout(String authorization, String spoc_id, final Activity context){
        loginAPI.performLogout(authorization,"4").enqueue(
                new Callback<LogoutApiResponse>() {
                    @Override
                    public void onResponse(Call<LogoutApiResponse> call, Response<LogoutApiResponse> response) {


                        if (response.isSuccessful()){
                            if (response.body().getSuccess().equals("1")){

                                SharedPreferences loginpref=application.getSharedPreferences("login_info",application.MODE_PRIVATE);
                                SharedPreferences spocpref = application.getSharedPreferences("spoc_info",MODE_PRIVATE);

                                SharedPreferences.Editor editor=loginpref.edit();
                                editor.clear().commit();

                                editor=loginpref.edit();
                                editor.clear().commit();
                                editor=spocpref.edit();
                                editor.clear().commit();

                                Intent intent=new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                context.finish();

                                // Toast.makeText(LoginActivity.this, response.body().response.accessToken+"..."+response.body().success, Toast.LENGTH_LONG).show();
                                Log.d(TAG, response.body().getSuccess());
                            }
                            if (response.body().getSuccess().equals("0")){
                                error.setValue(response.body().getMessage());
                                Log.d(TAG,".."+response.body().getMessage());
                            }

                        }else {
                            error.setValue("Connection Unsuccessful");
                            Log.e(TAG,"Connection Unsuccessful");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutApiResponse> call, Throwable t) {

                    }
                });

    }
    public void validateVerificationCode(String vCode){
        Log.d("Vcode","s " +verification_code.getValue());
        if (vCode.equals(verification_code.getValue())){
            loginSuccessful.setValue("Successful");
           // error.setValue("1");
            SharedPreferences.Editor editor=userPreference.edit();
            editor.putString("access_token",access_token.getValue());
            editor.putBoolean("is_login",true);
            editor.putString("usertype","4");
            editor.putString("Authorization","Token "+userMutableLiveData.getValue().get(0).getAccessToken());
            editor.putString("user_name",userMutableLiveData.getValue().get(0).getUserName());
            editor.putString("user_id",userMutableLiveData.getValue().get(0).getId().toString());
            editor.putString("spoc_id",userMutableLiveData.getValue().get(0).getId().toString());
            editor.putString("corporate_id", userMutableLiveData.getValue().get(0).getCorporateId().toString());
            editor.putString("email",userMutableLiveData.getValue().get(0).getEmail());
            editor.putString("user_contact",userMutableLiveData.getValue().get(0).getUserContact().toString());
            editor.putString("access_token",access_token.getValue());
            editor.putString("group_id", userMutableLiveData.getValue().get(0).getGroupId().toString());
            editor.putString("subgroup_id", userMutableLiveData.getValue().get(0).getSubgroupId().toString());
            editor.putString("is_local", userMutableLiveData.getValue().get(0).getIsLocal().toString());
            editor.putString("is_radio", userMutableLiveData.getValue().get(0).getIsRadio().toString());
            editor.putString("is_outstation",userMutableLiveData.getValue().get(0).getIsOutstation().toString());
            editor.putString("employee_info",GsonStringConvertor.gsonToString(userMutableLiveData.getValue()));
            editor.commit();
            verificationSuccessful.setValue("verified");
        }else {
            error.setValue("Verification Unsuccessful \n Please Check Email"+" "+verification_code.getValue());
        }
    }

}