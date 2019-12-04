package com.cotrav.cotravspoc.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cotrav.cotravspoc.models.login_models.User;
import com.cotrav.cotravspoc.repositories.LoginRepository;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final String TAG="UserViewModel";

    private LiveData<List<User>> userLiveData;
    private LiveData<String> accessToken;
    private LiveData<String> accessOTP;
    private LiveData<String> error;
    private LiveData<String> loginSuccessful;
    private LoginRepository loginRepositoty;


    public UserViewModel(Application application) {
        super(application);
        loginRepositoty=new LoginRepository(this.getApplication());
        userLiveData=loginRepositoty.getEmployeeInfo();
        accessToken =loginRepositoty.getAccessToken();
        accessOTP =loginRepositoty.getAccessOTP();

        error=loginRepositoty.getError();
        loginSuccessful=loginRepositoty.getLoginSuccessful();
    }
    public LiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getAccessToken() {
        return accessToken;
    }

    public LiveData<String> getAccessOTP() {
        return accessOTP;
    }

    public void verifyCode(String code){
        loginRepositoty.validateVerificationCode(code);
    }

    public Boolean isLogin() {
        return loginRepositoty.isLogin();
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<String> getLoginSuccessful() {
        return loginSuccessful;
    }


    public void performLogin(String username,String password){
        loginRepositoty.performLogin(username,password);
    }

    public void getSpocDetail(String authorization,String spoc_id){
        loginRepositoty.getSpocDetails(authorization,spoc_id);
    }

    public void performLogout(String authorization, String spoc_id, Activity context){
        loginRepositoty.performLogout(authorization,spoc_id,context);
    }

    public void viewSpoc(String authorization,String spoc_id){
        loginRepositoty.getSpocDetails(authorization,spoc_id);
    }


}
